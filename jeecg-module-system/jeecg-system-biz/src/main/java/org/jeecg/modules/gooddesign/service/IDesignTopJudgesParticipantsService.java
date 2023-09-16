package org.jeecg.modules.gooddesign.service;

import org.jeecg.modules.gooddesign.entity.DesignEnrollParticipants;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopParticipantsScoreVO;

import java.util.List;

/**
 * @Description: 评委通过表，保存评委评分数据
 * @Author: jeecg-boot
 * @Date: 2023-08-17
 * @Version: V1.0
 */
public interface IDesignTopJudgesParticipantsService extends IService<DesignEnrollParticipants> {

    /**
     * 获取打分数据
     *
     * @return
     */
    List<DesignTopParticipantsScoreVO> getTotalScore();


}
