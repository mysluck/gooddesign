package org.jeecg.modules.gooddesign.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.commons.collections.CollectionUtils;
import org.jeecg.modules.gooddesign.entity.DesignMainStakeholder;
import org.jeecg.modules.gooddesign.entity.vo.DesignMainStakeholderAddParam;
import org.jeecg.modules.gooddesign.entity.vo.DesignMainStakeholderVO;
import org.jeecg.modules.gooddesign.entity.vo.DesignStakeholderMainAddVO;
import org.jeecg.modules.gooddesign.mapper.DesignMainStakeholderMapper;
import org.jeecg.modules.gooddesign.service.IDesignMainStakeholderService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 好设计-编辑壮游-相关人员映射表
 * @Author: jeecg-boot
 * @Date: 2023-08-19
 * @Version: V1.0
 */
@Service
public class DesignMainStakeholderServiceImpl extends ServiceImpl<DesignMainStakeholderMapper, DesignMainStakeholder> implements IDesignMainStakeholderService {

    @Override
    public List<Integer> getStakeholderIds(int mainId, int type) {
        return this.baseMapper.getStakeholderIds(mainId, type);
    }

    @Override
    public List<DesignMainStakeholderVO> queryMainStakeholder(int mainId) {
        List<DesignMainStakeholderVO> designMainStakeholderVOS = this.baseMapper.queryMainStakeholder(mainId);
        return designMainStakeholderVOS;
    }

    @Override
    public List<DesignMainStakeholderVO> queryMainStakeholderByType(int mainId, int type) {
        List<DesignMainStakeholderVO> designMainStakeholderVOS = this.baseMapper.queryMainStakeholderByType(mainId, type);
        return designMainStakeholderVOS;
    }

    @Override
    public void batchEdit(DesignStakeholderMainAddVO designMainMovieVOs) {
        int mainId = designMainMovieVOs.getMainId();
        int type = designMainMovieVOs.getType();

        QueryWrapper<DesignMainStakeholder> queryWrapper = new QueryWrapper();
        queryWrapper.eq("main_id", mainId);
        queryWrapper.eq("type", type);
        this.remove(queryWrapper);

        batchAdd(designMainMovieVOs);
    }

    @Override
    public void batchAdd(DesignStakeholderMainAddVO designStakeholderMainAddVO) {

        List<Integer> stakeholderIds = designStakeholderMainAddVO.getStakeholderIds();
        int mainId = designStakeholderMainAddVO.getMainId();
        int type = designStakeholderMainAddVO.getType();
        List<DesignMainStakeholder> result = stakeholderIds.stream().map(stakeholderId -> {
            DesignMainStakeholder designMainStakeholder = new DesignMainStakeholder();
            designMainStakeholder.setMainId(mainId);
            designMainStakeholder.setType(type);
            designMainStakeholder.setStakeholderId(stakeholderId);
            return designMainStakeholder;
        }).collect(Collectors.toList());
        this.saveBatch(result);
    }
}
