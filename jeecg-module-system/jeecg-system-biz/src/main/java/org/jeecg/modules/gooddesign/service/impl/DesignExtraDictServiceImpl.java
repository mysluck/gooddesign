package org.jeecg.modules.gooddesign.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.compress.utils.Lists;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.gooddesign.entity.DesignExtraDict;
import org.jeecg.modules.gooddesign.entity.DesignMain;
import org.jeecg.modules.gooddesign.entity.vo.DesignExtraDictVO;
import org.jeecg.modules.gooddesign.mapper.DesignExtraDictMapper;
import org.jeecg.modules.gooddesign.service.IDesignExtraDictService;
import org.jeecg.modules.gooddesign.service.IDesignMainService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 壮游字典（年份、城市）
 * @Author: jeecg-boot
 * @Date: 2023-08-18
 * @Version: V1.0
 */
@Service
public class DesignExtraDictServiceImpl extends ServiceImpl<DesignExtraDictMapper, DesignExtraDict> implements IDesignExtraDictService {
    @Autowired
    IDesignMainService designMainService;

    @Override
    public boolean saveExt(int type, String value) {
        DesignExtraDict designExtraDict = new DesignExtraDict();
        designExtraDict.setType(type);
        designExtraDict.setValue(value);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (sysUser != null) {
            designExtraDict.setUpdateBy(sysUser.getUsername());
        }
        designExtraDict.setUpdateTime(new Date());
        return this.save(designExtraDict);
    }

    @Override
    public boolean saveExt(int type, String value, int parentId) {
        DesignExtraDict designExtraDict = new DesignExtraDict();
        designExtraDict.setType(type);
        designExtraDict.setValue(value);
        designExtraDict.setParentId(parentId);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (sysUser != null) {
            designExtraDict.setUpdateBy(sysUser.getUsername());
        }
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
    public List<DesignExtraDict> listByParentId(int parentId) {
        QueryWrapper<DesignExtraDict> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("parent_id", parentId);
        List<DesignExtraDict> list = this.list(queryWrapper);
        return list;
    }

    @Override
    public List<DesignExtraDictVO> tree() {
        QueryWrapper<DesignMain> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("commit_status", 1);
        List<DesignMain> designMains = designMainService.list(queryWrapper);
        if (CollectionUtils.isEmpty(designMains)) {
            return Lists.newArrayList();
        }
        Set<Integer> dictIds = new HashSet<>();
        designMains.forEach(data -> {
            dictIds.add(data.getCityId());
            dictIds.add(data.getYearId());
        });
        QueryWrapper<DesignExtraDict> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper.in("id", dictIds);
        List<DesignExtraDict> designExtraDictList = this.list(dictQueryWrapper);
        List<DesignExtraDictVO> collect = designExtraDictList.stream().map(data -> {
            DesignExtraDictVO vo = new DesignExtraDictVO();
            BeanUtils.copyProperties(data, vo);
            return vo;
        }).collect(Collectors.toList());

        List<DesignExtraDictVO> rootList = parseTree(collect, 0);
        rootList.forEach(data -> {
            data.setChild(parseTree(collect, data.getId()));
        });
        rootList.sort(Comparator.comparing(DesignExtraDictVO::getValue).reversed());
        return rootList;
    }

    List<DesignExtraDictVO> parseTree(List<DesignExtraDictVO> list, int parentId) {
        return list.stream().filter(data -> data.getParentId() == parentId).collect(Collectors.toList());
    }

}
