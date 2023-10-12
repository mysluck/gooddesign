package org.jeecg.modules.gooddesign.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeecg.weibo.exception.BusinessException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.gooddesign.entity.DesignActivity;
import org.jeecg.modules.gooddesign.entity.DesignEnrollJudges;
import org.jeecg.modules.gooddesign.entity.DesignTopJudges;
import org.jeecg.modules.gooddesign.entity.vo.*;
import org.jeecg.modules.gooddesign.mapper.DesignActivityMapper;
import org.jeecg.modules.gooddesign.mapper.DesignTopJudgesMapper;
import org.jeecg.modules.gooddesign.service.IDesignActivityService;
import org.jeecg.modules.gooddesign.service.IDesignTopJudgesParticipantsService;
import org.jeecg.modules.gooddesign.service.IDesignTopJudgesService;
import org.jeecg.modules.gooddesign.service.IDesignTopProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 好设计-发现100-设计师信息
 * @Author: jeecg-boot
 * @Date: 2023-08-20
 * @Version: V1.0
 */
@Service
public class DesignTopJudgesServiceImpl extends ServiceImpl<DesignTopJudgesMapper, DesignTopJudges> implements IDesignTopJudgesService {
    @Autowired
    IDesignTopProductService designTopProductService;
    @Autowired
    IDesignActivityService designActivityService;
    @Autowired
    DesignActivityMapper designActivityMapper;
    @Autowired
    IDesignTopJudgesParticipantsService designTopJudgesParticipantsService;


    @Override
    public void addDetail(DesignTopJudgesDetailVO designTopJudgesAllVO) {

        DesignActivity activity = designActivityService.getActivity();
        if (activity == null)
            throw new BusinessException("当前不存在开启活动，请开起活动！");

//        DesignTopJudgesVO designTopJudges = designTopJudgesAllVO.getDesignTopJudges();
        DesignTopJudges bean = new DesignTopJudges();
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
            designTopProductService.saveProduct(productvo);
        });
    }

    @Override
    public void editDetail(DesignTopJudgesDetailVO designTopJudgesAllVO) {

        DesignActivity activity = designActivityService.getActivity();
        if (activity == null)
            throw new BusinessException("当前不存在开启活动，请开起活动！");

//        DesignTopJudgesVO designTopJudges = designTopJudgesAllVO.getDesignTopJudges();
        DesignTopJudges bean = new DesignTopJudges();
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
//        products.forEach(productvo -> {
//            productvo.setTopJudgesId(judgesId);
//            designTopProductService.editProduct(productvo);
//        });
        designTopProductService.editProducts(products, judgesId);
    }

    @Override
    public List<DesignActivity> queryActivityList() {
        return designActivityMapper.queryActivityList();
    }

    @Override
    public DesignTopJudgesDetailVO queryDetailById(Integer id) {
        DesignTopJudgesDetailVO result = new DesignTopJudgesDetailVO();

        DesignTopJudges designTopJudges = this.getById(id);
        if (designTopJudges == null || designTopJudges.getId() == null) {
            return result;
        }
        BeanUtils.copyProperties(designTopJudges, result);

        List<DesignTopProductVO> designTopProductVOS = designTopProductService.queryByTopJudgesId(id);
        result.setProducts(designTopProductVOS);

        return result;
    }

    @Override
    public DesignTopJudges getByLoginId(String loginId) {
        QueryWrapper<DesignTopJudges> queryWrapper = new QueryWrapper();
        queryWrapper.eq("login_id", loginId);
        DesignActivity activity = designActivityService.getActivity();
        if (activity != null) {
            queryWrapper.eq("activity_id", activity.getId());
            List<DesignTopJudges> list = this.list(queryWrapper);
            if (!list.isEmpty()) {
                return list.get(0);
            }
        }
        return null;
    }


    private String getDesignNo() {
        return "FX" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmsss");
    }


    @Override
    public void updateSortByDesignNo(String designNo, int sort) {
        this.baseMapper.updateSortByDesignNo(designNo, sort);
    }

    @Override
    public void deleteBatchDetail(List<Integer> asList) {
        if (CollectionUtils.isNotEmpty(asList)) {
            designTopProductService.deleteByTopJudgesIds(asList);
            this.removeByIds(asList);
        }
    }
}
