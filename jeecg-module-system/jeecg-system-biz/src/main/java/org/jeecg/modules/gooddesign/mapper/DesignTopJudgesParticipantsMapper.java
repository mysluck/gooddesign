package org.jeecg.modules.gooddesign.mapper;

import org.jeecg.modules.gooddesign.entity.DesignEnrollParticipants;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopParticipantsScoreVO;

import java.util.List;

/**
 * @Description: 评委通过表，保存评委评分数据
 * @Author: jeecg-boot
 * @Date: 2023-08-17
 * @Version: V1.0
 */
public interface DesignTopJudgesParticipantsMapper extends BaseMapper<DesignEnrollParticipants> {
    List<DesignTopParticipantsScoreVO> getScore();

}
