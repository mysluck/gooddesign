package org.jeecg.modules.gooddesign.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.gooddesign.entity.DesignMainMovie;
import org.jeecg.modules.gooddesign.entity.vo.DesignMainMovieVO;
import org.jeecg.modules.gooddesign.mapper.DesignMainMovieMapper;
import org.jeecg.modules.gooddesign.service.IDesignMainMovieService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 现场照片
 * @Author: jeecg-boot
 * @Date: 2023-08-18
 * @Version: V1.0
 */
@Service
public class DesignMainMovieServiceImpl extends ServiceImpl<DesignMainMovieMapper, DesignMainMovie> implements IDesignMainMovieService {

    @Override
    public List<DesignMainMovieVO> queryByMainId(int mainId) {
        QueryWrapper<DesignMainMovie> queryWrapper = new QueryWrapper();
        queryWrapper.eq("main_id", mainId);
        List<DesignMainMovie> list = this.list(queryWrapper);
        List<DesignMainMovieVO> result = list.stream().map(designMainMovie -> {
            DesignMainMovieVO designMainMovieVO = new DesignMainMovieVO();
            BeanUtils.copyProperties(designMainMovie, designMainMovieVO);
            return designMainMovieVO;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public List<DesignMainMovieVO> queryByMainIds(List<Integer> mainIds) {
        QueryWrapper<DesignMainMovie> queryWrapper = new QueryWrapper();
        queryWrapper.in("main_id", mainIds);
        List<DesignMainMovie> list = this.list(queryWrapper);
        List<DesignMainMovieVO> result = list.stream().map(designMainMovie -> {
            DesignMainMovieVO designMainMovieVO = new DesignMainMovieVO();
            BeanUtils.copyProperties(designMainMovie, designMainMovieVO);
            return designMainMovieVO;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public void batchAdd(List<DesignMainMovieVO> designMainMovieVOS) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        List<DesignMainMovie> result = designMainMovieVOS.stream().map(designMainMovieVO -> {
            DesignMainMovie designMainMovie = new DesignMainMovie();
            BeanUtils.copyProperties(designMainMovieVO, designMainMovie);
            designMainMovie.setMainId(designMainMovieVO.getMainId());
            if (sysUser != null) {
                designMainMovie.setCreateBy(sysUser.getUsername());
            }
            designMainMovie.setCreateTime(new Date());
            return designMainMovie;
        }).collect(Collectors.toList());
        this.saveBatch(result);
    }

    @Override
    public void batchEdit(List<DesignMainMovieVO> designMainMovieVOS) {
        if (CollectionUtils.isNotEmpty(designMainMovieVOS)) {
            Integer mainId = designMainMovieVOS.get(0).getMainId();
            QueryWrapper<DesignMainMovie> queryWrapper = new QueryWrapper();
            queryWrapper.eq("main_id", mainId);
            this.remove(queryWrapper);
        }
        batchAdd(designMainMovieVOS);
    }
}
