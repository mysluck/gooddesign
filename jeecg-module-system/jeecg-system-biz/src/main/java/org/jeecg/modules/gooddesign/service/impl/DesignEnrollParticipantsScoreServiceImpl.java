package org.jeecg.modules.gooddesign.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.jeecg.modules.gooddesign.entity.DesignEnrollParticipantsScoreVO;
import org.jeecg.modules.gooddesign.mapper.DesignEnrollJudgesScoreMapper;
import org.jeecg.modules.gooddesign.service.IDesignEnrollParticipantsScoreService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DesignEnrollParticipantsScoreServiceImpl extends ServiceImpl<DesignEnrollJudgesScoreMapper, DesignEnrollParticipantsScoreVO> implements IDesignEnrollParticipantsScoreService {

    @Override
    public Page<DesignEnrollParticipantsScoreVO> pageByNameAndScoreStatus(Page<DesignEnrollParticipantsScoreVO> page, String realName, List<Integer> screeStatus, String userId) {

        return page.setRecords(this.baseMapper.page(page, realName, screeStatus, userId));

    }

    @Override
    public DesignEnrollParticipantsScoreVO doStartScore(String userId) {
        List<Integer> screeStatus = new ArrayList<>();
        screeStatus.add(0);
        screeStatus.add(3);
        Page<DesignEnrollParticipantsScoreVO> page = new Page<DesignEnrollParticipantsScoreVO>(1, 1);
        List<DesignEnrollParticipantsScoreVO> result = this.baseMapper.page(page, null, screeStatus, userId);
        if (CollectionUtils.isEmpty(result)) {
            return null;
        }
        return result.get(0);
    }
}
