package org.jeecg.modules.gooddesign.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeecg.weibo.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.gooddesign.entity.DesignEnrollProductWork;
import org.jeecg.modules.gooddesign.entity.DesignTopProduct;
import org.jeecg.modules.gooddesign.entity.DesignTopProductWork;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopProductVO;
import org.jeecg.modules.gooddesign.mapper.DesignTopProductMapper;
import org.jeecg.modules.gooddesign.service.IDesignActivityService;
import org.jeecg.modules.gooddesign.service.IDesignTopProductService;
import org.jeecg.modules.gooddesign.service.IDesignTopProductWorkService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 好设计-发现100-项目信息
 * @Author: jeecg-boot
 * @Date: 2023-08-20
 * @Version: V1.0
 */
@Service
@Slf4j
public class DesignTopProductServiceImpl extends ServiceImpl<DesignTopProductMapper, DesignTopProduct> implements IDesignTopProductService {

    @Autowired
    IDesignActivityService designActivityService;
    @Autowired
    IDesignTopProductWorkService designTopProductWorkService;

    @Transactional
    @Override
    public void saveProduct(DesignTopProductVO designTopProductVO) {

        DesignTopProduct bean = new DesignTopProduct();
        BeanUtils.copyProperties(designTopProductVO, bean);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (sysUser != null) {
            bean.setCreateBy(sysUser.getUsername());
        }
        bean.setCreateTime(new Date());
        bean.setId(null);
        this.save(bean);

        Integer productId = bean.getId();

        if (designTopProductVO == null) {
            log.info("当前数据存在问题：{}", designTopProductVO);
            return;
        }
        List<String> productImgUrls = designTopProductVO.getProductImgUrls();
        if (productImgUrls == null) {
            return;
        }
        List<DesignTopProductWork> designTopProductWorkList = new ArrayList<>();
        for (int i = 0; i < productImgUrls.size(); i++) {
            DesignTopProductWork designTopProductWork = new DesignTopProductWork();
            designTopProductWork.setProductId(productId);
            designTopProductWork.setWorkUrl(productImgUrls.get(i));
            designTopProductWork.setSort(i + 1);
            if (sysUser != null) {
                designTopProductWork.setCreateBy(sysUser.getUsername());
            }
            designTopProductWork.setCreateTime(new Date());
            designTopProductWork.setId(null);
            designTopProductWorkList.add(designTopProductWork);
        }

        designTopProductWorkService.saveBatch(designTopProductWorkList);

    }

    @Override
    public void editProduct(DesignTopProductVO designTopProductVO) {
        if (designTopProductVO.getId() == null) {
            throw new BusinessException("请输入项目ID");
        }

        DesignTopProduct bean = new DesignTopProduct();
        BeanUtils.copyProperties(designTopProductVO, bean);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (sysUser != null) {
            bean.setUpdateBy(sysUser.getUsername());
        }
        bean.setUpdateTime(new Date());
        this.updateById(bean);

        QueryWrapper<DesignTopProductWork> queryWrapper = new QueryWrapper();
        queryWrapper.eq("product_id", designTopProductVO.getId());

        designTopProductWorkService.remove(queryWrapper);

        List<String> productImgUrls = designTopProductVO.getProductImgUrls();
        List<DesignTopProductWork> designTopProductWorkList = new ArrayList<>();
        for (int i = 0; i < productImgUrls.size(); i++) {
            DesignTopProductWork designTopProductWork = new DesignTopProductWork();
            designTopProductWork.setProductId(designTopProductVO.getId());
            designTopProductWork.setWorkUrl(productImgUrls.get(i));
            designTopProductWork.setSort(i + 1);
            if (sysUser != null) {
                designTopProductWork.setCreateBy(sysUser.getUsername());
            }
            designTopProductWork.setCreateTime(new Date());
            designTopProductWorkList.add(designTopProductWork);
        }

        designTopProductWorkService.saveBatch(designTopProductWorkList);

    }


    @Override
    public void editProducts(List<DesignTopProductVO> designTopProductVOs, int judgesId) {
        List<DesignTopProductVO> designTopProductVOS = this.queryByTopJudgesId(judgesId);
        if (CollectionUtils.isNotEmpty(designTopProductVOS)) {
            //  删除
            List<Integer> productIds = designTopProductVOS.stream().map(DesignTopProductVO::getId).collect(Collectors.toList());
            QueryWrapper<DesignTopProductWork> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("product_id", productIds);
            designTopProductWorkService.remove(queryWrapper);
            this.removeByIds(productIds);
        }
        designTopProductVOs.forEach(productvo -> {
            productvo.setTopJudgesId(judgesId);
            this.saveProduct(productvo);
        });
    }


    @Override
    public DesignTopProductVO queryByProductId(Integer id) {
        DesignTopProduct product = this.getById(id);
        DesignTopProductVO resultVO = new DesignTopProductVO();
        BeanUtils.copyProperties(product, resultVO);

        QueryWrapper<DesignTopProductWork> queryWrapper = new QueryWrapper();
        queryWrapper.eq("product_id", id);
        queryWrapper.orderByAsc("sort");
        List<DesignTopProductWork> list = designTopProductWorkService.list(queryWrapper);
        resultVO.setProductImgUrls(list.stream().map(DesignTopProductWork::getWorkUrl).collect(Collectors.toList()));

        return resultVO;
    }

    @Override
    public List<DesignTopProductVO> queryByTopJudgesId(Integer topJudgesId) {
        QueryWrapper<DesignTopProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("top_judges_id", topJudgesId);
        List<DesignTopProduct> list = this.list(queryWrapper);
        List<DesignTopProductVO> result = list.stream().map(product -> {
            DesignTopProductVO resultVO = new DesignTopProductVO();
            BeanUtils.copyProperties(product, resultVO);

            QueryWrapper<DesignTopProductWork> workQueryWrapper = new QueryWrapper();
            workQueryWrapper.eq("product_id", product.getId());
            workQueryWrapper.orderByAsc("sort");
            List<DesignTopProductWork> workList = designTopProductWorkService.list(workQueryWrapper);
            resultVO.setProductImgUrls(workList.stream().map(DesignTopProductWork::getWorkUrl).collect(Collectors.toList()));
            return resultVO;
        }).collect(Collectors.toList());
        return result;
    }

    @Override
    public void deleteByTopJudgesIds(List<Integer> asList) {
        QueryWrapper<DesignTopProduct> queryWrapper = new QueryWrapper();
        queryWrapper.in("top_judges_id", asList);
        List<DesignTopProduct> designTopProducts = this.baseMapper.selectList(queryWrapper);
        List<Integer> productIdList = designTopProducts.stream().map(DesignTopProduct::getId).collect(Collectors.toList());
        designTopProductWorkService.deleteByProductIds(productIdList);
        this.remove(queryWrapper);
    }
}
