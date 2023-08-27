package org.jeecg.modules.gooddesign.service.impl;

import org.jeecg.modules.gooddesign.entity.DesignMainStakeholder;
import org.jeecg.modules.gooddesign.mapper.DesignMainStakeholderMapper;
import org.jeecg.modules.gooddesign.service.IDesignMainStakeholderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 好设计-编辑壮游-相关人员映射表
 * @Author: jeecg-boot
 * @Date: 2023-08-19
 * @Version: V1.0
 */
@Service
public class DesignMainStakeholderServiceImpl extends ServiceImpl<DesignMainStakeholderMapper, DesignMainStakeholder> implements IDesignMainStakeholderService {
    @Autowired
    DesignMainStakeholderMapper designMainStakeholderMapper;

    @Override
    public List<Integer> getStakeholderIds(int mainId, int type) {
        return designMainStakeholderMapper.getStakeholderIds(mainId, type);
    }
}
