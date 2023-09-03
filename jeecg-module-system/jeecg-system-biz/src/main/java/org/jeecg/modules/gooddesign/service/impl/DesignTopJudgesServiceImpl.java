package org.jeecg.modules.gooddesign.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.gooddesign.entity.DesignTopJudges;
import org.jeecg.modules.gooddesign.entity.vo.*;
import org.jeecg.modules.gooddesign.mapper.DesignTopJudgesMapper;
import org.jeecg.modules.gooddesign.service.IDesignTopJudgesParticipantsService;
import org.jeecg.modules.gooddesign.service.IDesignTopJudgesService;
import org.jeecg.modules.gooddesign.service.IDesignTopProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
            QueryWrapper<DesignTopJudges> queryWrapper = new QueryWrapper();
            queryWrapper.in("id", judgesIds);
            List<DesignTopJudges> list = this.list(queryWrapper);
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
    public void addDetail(DesignTopJudgesAllVO designTopJudgesAllVO) {
        DesignTopJudgesVO designTopJudges = designTopJudgesAllVO.getDesignTopJudges();
        DesignTopJudges bean = new DesignTopJudges();
        BeanUtils.copyProperties(designTopJudges, bean);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        bean.setCreateBy(sysUser.getUsername());
        bean.setCreateTime(new Date());
        bean.setDesignNo(getDesignNo());
        this.save(bean);

        //设计师ID
        Integer judgesId = bean.getId();
        List<DesignTopProductVO> products = designTopJudgesAllVO.getProducts();
        products.forEach(productvo -> {
            productvo.setTopJudgesId(judgesId);
            designTopProductService.saveProduct(productvo);
        });
    }


    private String getDesignNo() {
        return "FX" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmsss");
    }

}
