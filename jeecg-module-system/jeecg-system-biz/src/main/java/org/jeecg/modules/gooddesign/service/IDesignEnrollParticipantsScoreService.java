package org.jeecg.modules.gooddesign.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.gooddesign.entity.DesignActivity;
import org.jeecg.modules.gooddesign.entity.vo.DesignEnrollParticipantsCountVO;
import org.jeecg.modules.gooddesign.entity.vo.DesignEnrollParticipantsScoreVO;

import java.util.List;

/**
 * @Description: 评委通过表，保存评委评分数据
 * @Author: jeecg-boot
 * @Date: 2023-08-17
 * @Version: V1.0
 */
public interface IDesignEnrollParticipantsScoreService extends IService<DesignEnrollParticipantsScoreVO> {

    /**
     *
     * @param page
     * @param realName
     * @param screeStatus
     * @param userId 评委ID
     * @param designNo
     * @return
     */
    Page<DesignEnrollParticipantsScoreVO> pageByNameAndScoreStatus(Page<DesignEnrollParticipantsScoreVO> page, String realName, List<Integer> screeStatus, String userId, String designNo, DesignActivity designActivity);

    DesignEnrollParticipantsScoreVO doStartScore(String id, DesignActivity activity);

    DesignEnrollParticipantsCountVO countByNameAndScoreStatus(String realName, List<Integer> list, String id, String designNo, DesignActivity activity);
}
