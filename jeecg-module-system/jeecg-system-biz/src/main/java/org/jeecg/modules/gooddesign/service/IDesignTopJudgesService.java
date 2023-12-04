package org.jeecg.modules.gooddesign.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.gooddesign.entity.DesignActivity;
import org.jeecg.modules.gooddesign.entity.DesignTopJudges;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopJudgesDetailVO;

import java.util.List;

/**
 * @Description: 好设计-发现100-设计师信息
 * @Author: jeecg-boot
 * @Date: 2023-08-20
 * @Version: V1.0
 */
public interface IDesignTopJudgesService extends IService<DesignTopJudges> {




    void addDetail(DesignTopJudgesDetailVO designTopJudgesAllVO);

    void editDetail(DesignTopJudgesDetailVO designTopJudgesAllVO);

    List<DesignActivity> queryActivityList();

    DesignTopJudgesDetailVO queryDetailById(Integer id);

    DesignTopJudges getByLoginId(String loginId);

    void updateSortByDesignNo(String designNo, int sort);

    void deleteBatchDetail(List<Integer> asList);

    List<DesignTopJudges>  index(int id);
}
