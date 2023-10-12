package org.jeecg.modules.gooddesign.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections.CollectionUtils;
import org.jeecg.modules.gooddesign.entity.DesignTopProductWork;
import org.jeecg.modules.gooddesign.mapper.DesignTopProductWorkMapper;
import org.jeecg.modules.gooddesign.service.IDesignTopProductWorkService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @Description: 好设计-发现100-项目作品
 * @Author: jeecg-boot
 * @Date: 2023-08-20
 * @Version: V1.0
 */
@Service
public class DesignTopProductWorkServiceImpl extends ServiceImpl<DesignTopProductWorkMapper, DesignTopProductWork> implements IDesignTopProductWorkService {

    @Override
    public void deleteByProductIds(List<Integer> productIdList) {
        if (CollectionUtils.isNotEmpty(productIdList)) {
            QueryWrapper<DesignTopProductWork> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("product_id", productIdList);
            this.baseMapper.delete(queryWrapper);
        }
    }
}
