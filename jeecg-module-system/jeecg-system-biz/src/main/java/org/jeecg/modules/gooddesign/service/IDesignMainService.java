package org.jeecg.modules.gooddesign.service;

import org.jeecg.modules.gooddesign.entity.DesignMain;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.gooddesign.entity.vo.DesignMainDetailVO;

/**
 * @Description: 设计壮游
 * @Author: jeecg-boot
 * @Date:   2023-08-18
 * @Version: V1.0
 */
public interface IDesignMainService extends IService<DesignMain> {

    DesignMainDetailVO queryDetailById(String id);
}
