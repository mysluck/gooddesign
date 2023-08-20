package org.jeecg.modules.gooddesign.service;

import org.jeecg.modules.gooddesign.entity.DesignMainStakeholder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 好设计-编辑壮游-相关人员映射表
 * @Author: jeecg-boot
 * @Date: 2023-08-19
 * @Version: V1.0
 */
public interface IDesignMainStakeholderService extends IService<DesignMainStakeholder> {

    /**
     * 获取相关人员ID
     *
     * @param mainId
     * @param type
     * @return
     */
    List<Integer> getStakeholderIds(int mainId, int type);

}