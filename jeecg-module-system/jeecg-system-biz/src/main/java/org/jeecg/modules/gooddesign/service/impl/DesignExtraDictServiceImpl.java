package org.jeecg.modules.gooddesign.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.gooddesign.entity.DesignExtraDict;
import org.jeecg.modules.gooddesign.mapper.DesignExtraDictMapper;
import org.jeecg.modules.gooddesign.service.IDesignExtraDictService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Description: 壮游字典（年份、城市）
 * @Author: jeecg-boot
 * @Date: 2023-08-18
 * @Version: V1.0
 */
@Service
public class DesignExtraDictServiceImpl extends ServiceImpl<DesignExtraDictMapper, DesignExtraDict> implements IDesignExtraDictService {

    @Override
    public boolean saveExt(int type, String value) {
        DesignExtraDict designExtraDict = new DesignExtraDict();
        designExtraDict.setType(type);
        designExtraDict.setValue(value);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        designExtraDict.setUpdateBy(sysUser.getUsername());
        designExtraDict.setUpdateTime(new Date());
        return this.save(designExtraDict);
    }

    @Override
    public boolean saveExtAndPid(int type, String value, int parentId) {
        DesignExtraDict designExtraDict = new DesignExtraDict();
        designExtraDict.setType(type);
        designExtraDict.setValue(value);
        designExtraDict.setParentId(parentId);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        designExtraDict.setUpdateBy(sysUser.getUsername());
        designExtraDict.setUpdateTime(new Date());
        return this.save(designExtraDict);
    }

    @Override
    public List<DesignExtraDict> list(int type) {
        QueryWrapper<DesignExtraDict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", type);
        List<DesignExtraDict> list = this.list(queryWrapper);
        return list;
    }

    @Override
    public List<DesignExtraDict> list(int id, int type) {
        QueryWrapper<DesignExtraDict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", id);
        queryWrapper.eq("type", type);
        List<DesignExtraDict> list = this.list(queryWrapper);
        return list;
    }
}
