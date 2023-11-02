package org.jeecg.modules.gooddesign.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeecg.weibo.exception.BusinessException;
import org.apache.commons.collections.CollectionUtils;
import org.jeecg.modules.gooddesign.entity.DesignActivity;
import org.jeecg.modules.gooddesign.entity.vo.DesignEnrollParticipantsCountVO;
import org.jeecg.modules.gooddesign.entity.vo.DesignEnrollParticipantsScoreVO;
import org.jeecg.modules.gooddesign.mapper.DesignEnrollJudgesScoreMapper;
import org.jeecg.modules.gooddesign.service.IDesignActivityService;
import org.jeecg.modules.gooddesign.service.IDesignEnrollParticipantsScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DesignEnrollParticipantsScoreServiceImpl extends ServiceImpl<DesignEnrollJudgesScoreMapper, DesignEnrollParticipantsScoreVO> implements IDesignEnrollParticipantsScoreService {
    @Autowired
    IDesignActivityService designActivityService;

    @Override
    public Page<DesignEnrollParticipantsScoreVO> pageByNameAndScoreStatus(Page<DesignEnrollParticipantsScoreVO> page, String realName, List<Integer> screeStatus, String userId, String designNo, DesignActivity activity) {
        List<DesignEnrollParticipantsScoreVO> result = this.baseMapper.page(page, realName, screeStatus, userId, designNo, activity.getId());
        if (CollectionUtils.isNotEmpty(result)) {
            result.stream().forEach(vo -> {
                vo.setActivityName(activity.getActivityName());
            });
        }
        return page.setRecords(result);

    }

    @Override
    public DesignEnrollParticipantsScoreVO doStartScore(String userId, DesignActivity activity) {
        List<Integer> screeStatus = new ArrayList<>();
        screeStatus.add(0);
        screeStatus.add(3);
        Page<DesignEnrollParticipantsScoreVO> page = new Page<DesignEnrollParticipantsScoreVO>(1, 1);
        if (activity == null || activity.getId() == null) {
            throw new BusinessException("未发现启动的活动，请确认！");
        }
        List<DesignEnrollParticipantsScoreVO> result = this.baseMapper.page(page, null, screeStatus, userId, null, activity.getId());
        if (CollectionUtils.isEmpty(result)) {
            return null;
        }
        DesignEnrollParticipantsScoreVO designEnrollParticipantsScoreVO = result.get(0);
        designEnrollParticipantsScoreVO.setActivityName(activity.getActivityName());
        return designEnrollParticipantsScoreVO;
    }

    @Override
    public DesignEnrollParticipantsCountVO countByNameAndScoreStatus(String realName, List<Integer> screeStatus, String userId, String designNo, DesignActivity activity) {
        List<DesignEnrollParticipantsScoreVO> result = this.baseMapper.count(realName, screeStatus, userId, designNo, activity.getId());
        if (CollectionUtils.isNotEmpty(result)) {
            int scoreCount = result.stream().filter(data -> {
                return data.getScoreStatus() == 2 || data.getScoreStatus() == 1;
            }).collect(Collectors.toList()).size();
            return DesignEnrollParticipantsCountVO.builder().totalCount(result.size()).scoreCount(scoreCount).build();
        }
        return DesignEnrollParticipantsCountVO.builder().build();

    }
}
