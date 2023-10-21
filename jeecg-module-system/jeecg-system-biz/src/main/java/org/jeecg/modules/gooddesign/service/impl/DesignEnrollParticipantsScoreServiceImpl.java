package org.jeecg.modules.gooddesign.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.jeecg.modules.gooddesign.entity.DesignActivity;
import org.jeecg.modules.gooddesign.entity.DesignEnrollParticipantsScoreVO;
import org.jeecg.modules.gooddesign.mapper.DesignEnrollJudgesScoreMapper;
import org.jeecg.modules.gooddesign.service.IDesignActivityService;
import org.jeecg.modules.gooddesign.service.IDesignEnrollParticipantsScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DesignEnrollParticipantsScoreServiceImpl extends ServiceImpl<DesignEnrollJudgesScoreMapper, DesignEnrollParticipantsScoreVO> implements IDesignEnrollParticipantsScoreService {
    @Autowired
    IDesignActivityService designActivityService;

    @Override
    public Page<DesignEnrollParticipantsScoreVO> pageByNameAndScoreStatus(Page<DesignEnrollParticipantsScoreVO> page, String realName, List<Integer> screeStatus, String userId, String designNo) {
        List<DesignEnrollParticipantsScoreVO> result = this.baseMapper.page(page, realName, screeStatus, userId, designNo);
        if (CollectionUtils.isNotEmpty(result)) {
            Integer activityId = result.get(0).getActivityId();
            DesignActivity activity = designActivityService.getById(activityId);
            result.stream().forEach(vo -> {
                vo.setActivityName(activity.getActivityName());
            });
        }
        return page.setRecords(result);

    }

    @Override
    public DesignEnrollParticipantsScoreVO doStartScore(String userId) {
        List<Integer> screeStatus = new ArrayList<>();
        screeStatus.add(0);
        screeStatus.add(3);
        Page<DesignEnrollParticipantsScoreVO> page = new Page<DesignEnrollParticipantsScoreVO>(1, 1);
        List<DesignEnrollParticipantsScoreVO> result = this.baseMapper.page(page, null, screeStatus, userId, null);
        if (CollectionUtils.isEmpty(result)) {
            return null;
        }
        DesignEnrollParticipantsScoreVO designEnrollParticipantsScoreVO = result.get(0);
        DesignActivity activity = designActivityService.getById(designEnrollParticipantsScoreVO.getActivityId());
        designEnrollParticipantsScoreVO.setActivityName(activity.getActivityName());
        return designEnrollParticipantsScoreVO;
    }
}
