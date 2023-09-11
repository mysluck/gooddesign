package org.jeecg.modules.gooddesign.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.gooddesign.entity.DesignActivity;
import org.jeecg.modules.gooddesign.entity.DesignEnrollJudges;
import org.jeecg.modules.gooddesign.entity.DesignTopJudges;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopJudgesDetailVO;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopJudgesScoreVO;

import java.util.List;

/**
 * @Description: 好设计-报名-设计师信息
 * @Author: jeecg-boot
 * @Date: 2023-08-20
 * @Version: V1.0
 */
public interface IDesignEnrollJudgesService extends IService<DesignEnrollJudges> {


    /**
     * 查询top100
     *
     * @return
     */
    List<DesignTopJudgesScoreVO> queryByTopJudgesId();


    void addDetail(DesignTopJudgesDetailVO designTopJudgesAllVO);

    void editDetail(DesignTopJudgesDetailVO designTopJudgesAllVO);

    List<DesignActivity> queryActivityList();

    DesignTopJudgesDetailVO queryDetailById(Integer id);

    /**
     * 根据登陆ID获取所有报名信息
     * @param id
     * @return
     */
    List<DesignTopJudgesDetailVO> queryDetailByLoginId(String loginid);

    DesignEnrollJudges getByLoginId(String loginId);
}
