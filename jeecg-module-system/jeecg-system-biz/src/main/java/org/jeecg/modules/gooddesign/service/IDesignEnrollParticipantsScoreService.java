package org.jeecg.modules.gooddesign.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.gooddesign.entity.DesignEnrollParticipantsScoreVO;

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
     * @return
     */
    Page<DesignEnrollParticipantsScoreVO> pageByNameAndScoreStatus(Page<DesignEnrollParticipantsScoreVO> page, String realName, List<Integer> screeStatus, String userId);

    DesignEnrollParticipantsScoreVO doStartScore(String id);
}
