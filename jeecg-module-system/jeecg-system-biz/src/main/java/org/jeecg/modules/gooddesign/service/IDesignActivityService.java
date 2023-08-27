package org.jeecg.modules.gooddesign.service;

import org.jeecg.modules.gooddesign.entity.DesignActivity;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @Description: 好设计-跨年启停
 * @Author: jeecg-boot
 * @Date: 2023-08-19
 * @Version: V1.0
 */
public interface IDesignActivityService extends IService<DesignActivity> {

    /**
     *
     * @return 进行中 返回true
     */
    boolean checkActivityStatus();
}
