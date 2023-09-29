package org.jeecg.modules.gooddesign.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.gooddesign.entity.DesignEnrollJudges;
import org.jeecg.modules.gooddesign.entity.DesignEnrollParticipants;
import org.jeecg.modules.gooddesign.entity.DesignEnrollParticipantsScoreVO;
import org.jeecg.modules.system.entity.SysAnnouncement;

import java.util.List;

/**
 * @Description: 好设计-评委打分分页查询 添加打分状态
 * @Author: jeecg-boot
 * @Date: 2023-08-20
 * @Version: V1.0
 */
public interface DesignEnrollJudgesScoreMapper extends BaseMapper<DesignEnrollParticipantsScoreVO> {

    List<DesignEnrollParticipantsScoreVO> page(Page<DesignEnrollParticipantsScoreVO> page, String judgesName, List<Integer> scoreStatus, String userId);

}
