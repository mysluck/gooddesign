package org.jeecg.modules.gooddesign.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.gooddesign.entity.DesignMain;
import org.jeecg.modules.gooddesign.entity.vo.DesignMainDetailVO;
import org.jeecg.modules.gooddesign.entity.vo.DesignMainVO;

/**
 * @Description: 设计壮游
 * @Author: jeecg-boot
 * @Date:   2023-08-18
 * @Version: V1.0
 */
public interface IDesignMainService extends IService<DesignMain> {

    DesignMainDetailVO queryDetailById(String id);

    DesignMainDetailVO queryDetailByYearAndCity(int yearId,int cityId);

    Page<DesignMainVO> pageDesignMain(Page<DesignMainVO> page, DesignMain designMain);
}
