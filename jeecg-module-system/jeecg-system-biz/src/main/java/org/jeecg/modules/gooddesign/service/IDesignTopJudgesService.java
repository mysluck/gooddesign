package org.jeecg.modules.gooddesign.service;

import org.jeecg.modules.gooddesign.entity.DesignTopJudges;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopJudgesAllVO;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopJudgesScoreVO;

import java.util.List;

/**
 * @Description: 好设计-发现100-设计师信息
 * @Author: jeecg-boot
 * @Date: 2023-08-20
 * @Version: V1.0
 */
public interface IDesignTopJudgesService extends IService<DesignTopJudges> {

    /**
     * 查询top100
     *
     * @return
     */
    List<DesignTopJudgesScoreVO> queryByTopJudgesId();


    void addDetail(DesignTopJudgesAllVO designTopJudgesAllVO);
}
