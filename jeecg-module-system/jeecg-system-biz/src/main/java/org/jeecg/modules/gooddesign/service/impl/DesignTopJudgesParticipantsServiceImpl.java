package org.jeecg.modules.gooddesign.service.impl;

import org.jeecg.modules.gooddesign.entity.DesignEnrollParticipants;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopParticipantsScoreVO;
import org.jeecg.modules.gooddesign.mapper.DesignTopJudgesParticipantsMapper;
import org.jeecg.modules.gooddesign.service.IDesignTopJudgesParticipantsService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 评委通过表，保存评委评分数据
 * @Author: jeecg-boot
 * @Date: 2023-08-17
 * @Version: V1.0
 */
@Service
public class DesignTopJudgesParticipantsServiceImpl extends ServiceImpl<DesignTopJudgesParticipantsMapper, DesignEnrollParticipants> implements IDesignTopJudgesParticipantsService {
    @Override
    public List<DesignTopParticipantsScoreVO> getTotalScore() {
        return this.baseMapper.getScore();
    }

}
