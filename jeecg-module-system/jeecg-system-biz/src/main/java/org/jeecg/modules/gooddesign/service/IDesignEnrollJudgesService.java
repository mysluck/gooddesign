package org.jeecg.modules.gooddesign.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.gooddesign.entity.DesignEnrollJudges;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopJudgesDetailVO;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopJudgesScoreVO;
import org.jeecg.modules.gooddesign.entity.vo.JudgesScoreVO;

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


    DesignTopJudgesDetailVO queryDetailById(Integer id);

    /**
     * 根据登陆ID获取所有报名信息
     *
     * @param id
     * @return
     */
    List<DesignTopJudgesDetailVO> queryDetailByLoginId(String loginid);

    List<DesignEnrollJudges> getByLoginId(String loginId);

    void batchEditScreenStatus(List<Integer> enrollIds, int screenStatus);

    void addTop100(Integer id, int status);

    void batchAddTop100(List<Integer> id);

     void removeFromTop100(int id);

    void batchRemoveFromTop100(List<Integer> id);

    List<JudgesScoreVO> queryScoreHistory(int id);

    Page<DesignTopJudgesScoreVO> pageByNameAndTopStatus(Page<DesignTopJudgesScoreVO> page, String realName, Integer topStatus, Integer sortStatus);
}
