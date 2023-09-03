package org.jeecg.modules.gooddesign.service.impl;

import org.checkerframework.checker.units.qual.A;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.gooddesign.entity.DesignMain;
import org.jeecg.modules.gooddesign.entity.DesignMainMovie;
import org.jeecg.modules.gooddesign.entity.vo.DesignMainBasicVO;
import org.jeecg.modules.gooddesign.entity.vo.DesignMainDetailVO;
import org.jeecg.modules.gooddesign.mapper.DesignMainMapper;
import org.jeecg.modules.gooddesign.service.IDesignMainImageService;
import org.jeecg.modules.gooddesign.service.IDesignMainMovieService;
import org.jeecg.modules.gooddesign.service.IDesignMainService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @Description: 设计壮游
 * @Author: jeecg-boot
 * @Date: 2023-08-18
 * @Version: V1.0
 */
@Service
public class DesignMainServiceImpl extends ServiceImpl<DesignMainMapper, DesignMain> implements IDesignMainService {
    @Autowired
    IDesignMainMovieService designMainMovieService;
    @Autowired
    IDesignMainImageService designMainImageService;

    @Override
    public DesignMainDetailVO queryDetailById(String id) {
        DesignMainDetailVO designMainDetailVO = new DesignMainDetailVO();
        DesignMainBasicVO designMainBasicVO = new DesignMainBasicVO();
        DesignMain designMain = this.getById(id);
        if (designMain == null || designMain.getId() == 0) {
            return designMainDetailVO;
        }
        BeanUtils.copyProperties(designMain, designMainBasicVO);
        designMainDetailVO.setDesignMainBasic(designMainBasicVO);
        designMainDetailVO.setImages(designMainImageService.queryByMainId(designMain.getId()));
        designMainDetailVO.setMovies(designMainMovieService.queryByMainId(designMain.getId()));
        return designMainDetailVO;
    }
}
