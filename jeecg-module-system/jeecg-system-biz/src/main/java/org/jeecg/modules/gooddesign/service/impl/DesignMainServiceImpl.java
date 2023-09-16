package org.jeecg.modules.gooddesign.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections.CollectionUtils;
import org.jeecg.modules.gooddesign.entity.DesignMain;
import org.jeecg.modules.gooddesign.entity.vo.DesignMainBasicVO;
import org.jeecg.modules.gooddesign.entity.vo.DesignMainDetailVO;
import org.jeecg.modules.gooddesign.entity.vo.DesignMainStakeholderVO;
import org.jeecg.modules.gooddesign.mapper.DesignMainMapper;
import org.jeecg.modules.gooddesign.service.IDesignMainImageService;
import org.jeecg.modules.gooddesign.service.IDesignMainMovieService;
import org.jeecg.modules.gooddesign.service.IDesignMainService;
import org.jeecg.modules.gooddesign.service.IDesignMainStakeholderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    IDesignMainStakeholderService designMainStakeholderService;
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
        addDetail(designMainDetailVO, designMain.getId());

        return designMainDetailVO;
    }

    @Override
    public DesignMainDetailVO queryDetailByYearAndCity(int yearId, int cityId) {
        DesignMainDetailVO designMainDetailVO = new DesignMainDetailVO();
        DesignMainBasicVO designMainBasicVO = new DesignMainBasicVO();
        QueryWrapper<DesignMain> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("city_id", cityId);
        DesignMain designMain = this.baseMapper.selectOne(queryWrapper);
        if (designMain == null || designMain.getId() == 0) {
            return designMainDetailVO;
        }
        BeanUtils.copyProperties(designMain, designMainBasicVO);
        designMainDetailVO.setDesignMainBasic(designMainBasicVO);
        addDetail(designMainDetailVO, designMain.getId());
        return designMainDetailVO;
    }

    private void addDetail(DesignMainDetailVO designMainDetailVO, int mainId) {
        designMainDetailVO.setImages(designMainImageService.queryByMainId(mainId));
        designMainDetailVO.setMovies(designMainMovieService.queryByMainId(mainId));
        List<DesignMainStakeholderVO> designMainStakeholderVOS = designMainStakeholderService.queryMainStakeholder(mainId);
        if (CollectionUtils.isNotEmpty(designMainStakeholderVOS)) {
            Map<Integer, List<DesignMainStakeholderVO>> listMap = designMainStakeholderVOS.stream().collect(Collectors.groupingBy(DesignMainStakeholderVO::getType));
            designMainDetailVO.setUser1List(listMap.get(1));
            designMainDetailVO.setUser2List(listMap.get(2));
            designMainDetailVO.setUser3List(listMap.get(3));
        }

    }
}
