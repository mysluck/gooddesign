package org.jeecg.modules.gooddesign.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeecg.weibo.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.gooddesign.entity.DesignActivity;
import org.jeecg.modules.gooddesign.entity.DesignEnrollParticipants;
import org.jeecg.modules.gooddesign.entity.vo.DesignEnrollParticipantsSaveEditVO;
import org.jeecg.modules.gooddesign.entity.vo.DesignJudgesParticipantsVO;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopParticipantsScoreVO;
import org.jeecg.modules.gooddesign.mapper.DesignTopJudgesParticipantsMapper;
import org.jeecg.modules.gooddesign.service.IDesignActivityService;
import org.jeecg.modules.gooddesign.service.IDesignTopJudgesParticipantsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 评委通过表，保存评委评分数据
 * @Author: jeecg-boot
 * @Date: 2023-08-17
 * @Version: V1.0
 */
@Slf4j
@Service
public class DesignTopJudgesParticipantsServiceImpl extends ServiceImpl<DesignTopJudgesParticipantsMapper, DesignEnrollParticipants> implements IDesignTopJudgesParticipantsService {
    @Autowired
    IDesignActivityService designActivityService;

    @Override
    public List<DesignTopParticipantsScoreVO> getTotalScore() {
        return this.baseMapper.getScore();
    }

    @Override
    public void add(DesignJudgesParticipantsVO designJudgesParticipantsVO) {
        DesignEnrollParticipants designJudgesParticipants = new DesignEnrollParticipants();
        BeanUtils.copyProperties(designJudgesParticipantsVO, designJudgesParticipants);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (sysUser != null) {
            designJudgesParticipants.setCreateBy(sysUser.getUsername());
        }
        designJudgesParticipants.setCreateTime(new Date());
        this.save(designJudgesParticipants);
    }

    @Override
    public void batchAdd(List<DesignJudgesParticipantsVO> designJudgesParticipants) {
        designJudgesParticipants.stream().forEach(data -> {
            try {
                add(data);
            } catch (Exception e) {
                log.error("插入失败，当前数据为：{},报错信息为:{}", JSONObject.toJSONString(data), e.getMessage());
            }
        });
    }

    @Override
    public void edit(DesignJudgesParticipantsVO designJudgesParticipantsVO) {
        DesignEnrollParticipants designJudgesParticipants = new DesignEnrollParticipants();
        BeanUtils.copyProperties(designJudgesParticipantsVO, designJudgesParticipants);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (sysUser != null) {
            designJudgesParticipants.setUpdateBy(sysUser.getUsername());
            if (StringUtils.isEmpty(designJudgesParticipants.getJudgeId())) {
                designJudgesParticipants.setJudgeId(sysUser.getId());
            }
        }
        designJudgesParticipants.setUpdateTime(new Date());
        this.updateById(designJudgesParticipants);
    }

    @Override
    public void batchEdit(List<DesignEnrollParticipantsSaveEditVO> designJudgesParticipants) {

        DesignActivity activity = designActivityService.getNowActivity();
        if (activity == null) {
            throw new BusinessException("评委暂时无法打分，请联系管理员！");
        }
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (sysUser == null) {
            throw new BusinessException("为获取评委信息！");
        }

        List<DesignEnrollParticipants> result = designJudgesParticipants.stream().map(data -> {
            DesignEnrollParticipants designEnrollParticipants = new DesignEnrollParticipants();
            designEnrollParticipants.setParticipantId(data.getId());
            designEnrollParticipants.setScoreStatus(data.getScoreStatus());
            designEnrollParticipants.setActivityId(activity.getId());
            designEnrollParticipants.setJudgeId(sysUser.getId());
            return designEnrollParticipants;
        }).collect(Collectors.toList());

        result.stream().forEach(data -> saveOrUpdateData(data));
    }

    private void saveOrUpdateData(DesignEnrollParticipants designJudgesParticipants) {
        QueryWrapper<DesignEnrollParticipants> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("participant_id", designJudgesParticipants.getParticipantId());
        queryWrapper.eq("judge_id", designJudgesParticipants.getJudgeId());
        queryWrapper.eq("activity_id", designJudgesParticipants.getActivityId());
        DesignEnrollParticipants designEnrollParticipants = this.baseMapper.selectOne(queryWrapper);
        if (designEnrollParticipants == null) {
            this.save(designJudgesParticipants);
        } else {
            designEnrollParticipants.setScoreStatus(designJudgesParticipants.getScoreStatus());
            this.updateById(designEnrollParticipants);
        }

    }
}
