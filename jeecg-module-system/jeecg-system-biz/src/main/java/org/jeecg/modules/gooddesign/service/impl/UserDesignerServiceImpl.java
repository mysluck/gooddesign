package org.jeecg.modules.gooddesign.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.gooddesign.entity.DesignJudges;
import org.jeecg.modules.gooddesign.mapper.DesignJudgesMapper;
import org.jeecg.modules.gooddesign.service.IUserDesignerService;
import org.springframework.stereotype.Service;

/**
 * @Description: 设计师
 * @Author: jeecg-boot
 * @Date: 2023-08-17
 * @Version: V1.0
 */
@Service
public class UserDesignerServiceImpl extends ServiceImpl<DesignJudgesMapper, DesignJudges> implements IUserDesignerService {


    @Override
    public boolean checkSortNoExist(Integer sort) {
        if (sort == null) {
            return false;
        }
        QueryWrapper<DesignJudges> queryWrapper = new QueryWrapper();
        queryWrapper.eq("sort", sort);
        DesignJudges one = this.getOne(queryWrapper);
        if (one == null || one.getSort() == null) {
            return false;
        }
        return true;
    }
}
