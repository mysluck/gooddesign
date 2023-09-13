package org.jeecg.modules.gooddesign.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeecg.weibo.exception.BusinessException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.gooddesign.entity.DesignActivity;
import org.jeecg.modules.gooddesign.entity.DesignEnrollJudges;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopJudgesDetailVO;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopJudgesScoreVO;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopParticipantsScoreVO;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopProductVO;
import org.jeecg.modules.gooddesign.mapper.DesignActivityMapper;
import org.jeecg.modules.gooddesign.mapper.DesignEnrollJudgesMapper;
import org.jeecg.modules.gooddesign.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 好设计-发现100-设计师信息
 * @Author: jeecg-boot
 * @Date: 2023-08-20
 * @Version: V1.0
 */
@Service
public class DesignEnrollJudgesServiceImpl extends ServiceImpl<DesignEnrollJudgesMapper, DesignEnrollJudges> implements IDesignEnrollJudgesService {
    @Autowired
    IDesignEnrollProductService designEnrollProductService;
    @Autowired
    IDesignActivityService designActivityService;
    @Autowired
    DesignActivityMapper designActivityMapper;
    @Autowired
    IDesignTopJudgesParticipantsService designTopJudgesParticipantsService;

    @Override
    public List<DesignTopJudgesScoreVO> queryByTopJudgesId() {
        List<DesignTopJudgesScoreVO> result = new ArrayList<>();
        List<DesignTopParticipantsScoreVO> totalScore = designTopJudgesParticipantsService.getTotalScore();
        if (!totalScore.isEmpty()) {
            List<Integer> judgesIds = new ArrayList<>();
            Map<Integer, Double> scoreMap = new HashMap<>(totalScore.size());
            for (int i = 0; i < totalScore.size(); i++) {
                DesignTopParticipantsScoreVO designTopParticipantsScoreVO = totalScore.get(i);
                judgesIds.add(designTopParticipantsScoreVO.getJudgesId());
                scoreMap.put(designTopParticipantsScoreVO.getJudgesId(), designTopParticipantsScoreVO.getTotalScore());
            }

            if (judgesIds.size() > 100) {
                judgesIds.subList(0, 100);
            }
            QueryWrapper<DesignEnrollJudges> queryWrapper = new QueryWrapper();
            queryWrapper.in("id", judgesIds);
            List<DesignEnrollJudges> list = this.list(queryWrapper);
            result = list.stream().map(designTopJudge -> {
                DesignTopJudgesScoreVO scoreVO = new DesignTopJudgesScoreVO();
                BeanUtils.copyProperties(designTopJudge, scoreVO);
                scoreVO.setScore(scoreMap.get(designTopJudge.getId()));
                return scoreVO;
            }).collect(Collectors.toList());
        }
        return result;
    }

    @Override
    public void addDetail(DesignTopJudgesDetailVO designTopJudgesAllVO) {

        DesignActivity activity = designActivityService.getActivity();
        if (activity == null)
            throw new BusinessException("当前不存在开启活动，请开起活动！");

//        DesignTopJudgesVO designTopJudges = designTopJudgesAllVO.getDesignTopJudges();
        DesignEnrollJudges bean = new DesignEnrollJudges();
        BeanUtils.copyProperties(designTopJudgesAllVO, bean);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (sysUser != null) {
            bean.setCreateBy(sysUser.getUsername());
        }
        bean.setCreateTime(new Date());
        bean.setDesignNo(getDesignNo());
        bean.setActivityId(activity.getId());
        bean.setActivityName(activity.getActivityName());
        this.save(bean);

        //设计师ID
        Integer judgesId = bean.getId();
        List<DesignTopProductVO> products = designTopJudgesAllVO.getProducts();
        products.forEach(productvo -> {
            productvo.setTopJudgesId(judgesId);
            designEnrollProductService.saveProduct(productvo);
        });
    }

    @Override
    public void editDetail(DesignTopJudgesDetailVO designTopJudgesAllVO) {

        DesignActivity activity = designActivityService.getActivity();
        if (activity == null)
            throw new BusinessException("当前不存在开启活动，请开起活动！");

//        DesignTopJudgesVO designTopJudges = designTopJudgesAllVO.getDesignTopJudges();
        DesignEnrollJudges bean = new DesignEnrollJudges();
        BeanUtils.copyProperties(designTopJudgesAllVO, bean);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (sysUser != null) {
            bean.setUpdateBy(sysUser.getUsername());
        }
        bean.setUpdateTime(new Date());
        this.updateById(bean);

        //设计师ID
        Integer judgesId = bean.getId();
        List<DesignTopProductVO> products = designTopJudgesAllVO.getProducts();
        products.forEach(productvo -> {
            productvo.setTopJudgesId(judgesId);
            designEnrollProductService.editProduct(productvo);
        });
    }


    @Override
    public DesignTopJudgesDetailVO queryDetailById(Integer id) {
        DesignTopJudgesDetailVO result = new DesignTopJudgesDetailVO();

        DesignEnrollJudges designTopJudges = this.getById(id);
        if (designTopJudges == null || designTopJudges.getId() == null) {
            return result;
        }
        BeanUtils.copyProperties(designTopJudges, result);

        List<DesignTopProductVO> designTopProductVOS = designEnrollProductService.queryByTopJudgesId(id);
        result.setProducts(designTopProductVOS);

        return result;
    }


    @Override
    public List<DesignTopJudgesDetailVO> queryDetailByLoginId(String loginId) {
        QueryWrapper<DesignEnrollJudges> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("login_id", loginId);
        List<DesignEnrollJudges> enrollJudges = this.list(queryWrapper);
        if (CollectionUtils.isEmpty(enrollJudges)) {
            return new ArrayList<>();
        }
        return enrollJudges.stream().map(enrollJudge -> {
            DesignTopJudgesDetailVO result = new DesignTopJudgesDetailVO();
            BeanUtils.copyProperties(enrollJudge, result);
            List<DesignTopProductVO> designTopProductVOS = designEnrollProductService.queryByTopJudgesId(result.getId());
            result.setProducts(designTopProductVOS);
            return result;
        }).collect(Collectors.toList());
    }

    @Override
    public List<DesignEnrollJudges> getByLoginId(String loginId) {
        List<DesignEnrollJudges> list = new ArrayList<>();
        QueryWrapper<DesignEnrollJudges> queryWrapper = new QueryWrapper();
        queryWrapper.eq("login_id", loginId);
        DesignActivity activity = designActivityService.getActivity();
        if (activity != null) {
            queryWrapper.eq("activity_id", activity.getId());
            list = this.list(queryWrapper);
        }
        return list;
    }


    private String getDesignNo() {
        return "FX" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmsss");
    }


}
