package org.jeecg.modules.gooddesign.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.gooddesign.entity.DesignMainImage;
import org.jeecg.modules.gooddesign.entity.DesignMainMovie;
import org.jeecg.modules.gooddesign.entity.vo.DesignMainImageVO;
import org.jeecg.modules.gooddesign.entity.vo.DesignMainMovieVO;
import org.jeecg.modules.gooddesign.mapper.DesignMainImageMapper;
import org.jeecg.modules.gooddesign.service.IDesignMainImageService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

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
public class DesignMainImageServiceImpl extends ServiceImpl<DesignMainImageMapper, DesignMainImage> implements IDesignMainImageService {

    @Override
    public List<DesignMainImageVO> queryByMainId(int mainId) {

        QueryWrapper<DesignMainImage> queryWrapper = new QueryWrapper();
        queryWrapper.eq("main_id", mainId);
        List<DesignMainImage> list = this.list(queryWrapper);
        List<DesignMainImageVO> result = list.stream().map(designMainMovie -> {
            DesignMainImageVO vo = new DesignMainImageVO();
            BeanUtils.copyProperties(designMainMovie, vo);
            return vo;
        }).collect(Collectors.toList());
        return result;

    }

    @Override
    public List<DesignMainImageVO> queryByMainIds(List<Integer> mainIds) {
        QueryWrapper<DesignMainImage> queryWrapper = new QueryWrapper();
        queryWrapper.in("main_id", mainIds);
        List<DesignMainImage> list = this.list(queryWrapper);
        List<DesignMainImageVO> result = list.stream().map(designMainMovie -> {
            DesignMainImageVO vo = new DesignMainImageVO();
            BeanUtils.copyProperties(designMainMovie, vo);
            return vo;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public void batchEdit(List<DesignMainImageVO> designMainImageVOS) {
        if (CollectionUtils.isNotEmpty(designMainImageVOS)) {
            Integer mainId = designMainImageVOS.get(0).getMainId();
            QueryWrapper<DesignMainImage> queryWrapper = new QueryWrapper();
            queryWrapper.eq("main_id", mainId);
            this.remove(queryWrapper);
        }
        batchAdd(designMainImageVOS);
    }

    @Override
    public void batchAdd(List<DesignMainImageVO> designMainImageVOS) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (CollectionUtils.isNotEmpty(designMainImageVOS)) {
            List<DesignMainImage> result = designMainImageVOS.stream().map(designMainImageVO -> {
                DesignMainImage designMainImage = new DesignMainImage();
                BeanUtils.copyProperties(designMainImageVO, designMainImage);
                if (sysUser != null) {
                    designMainImage.setCreateBy(sysUser.getUsername());
                }
                designMainImage.setCreateTime(new Date());
                return designMainImage;
            }).collect(Collectors.toList());
            this.saveBatch(result);
        }
    }
}
