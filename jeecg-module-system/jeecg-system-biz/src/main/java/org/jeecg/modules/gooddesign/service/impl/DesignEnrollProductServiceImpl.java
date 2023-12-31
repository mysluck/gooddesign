package org.jeecg.modules.gooddesign.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeecg.weibo.exception.BusinessException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.gooddesign.entity.*;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopProductVO;
import org.jeecg.modules.gooddesign.mapper.DesignEnrollProductMapper;
import org.jeecg.modules.gooddesign.service.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
public class DesignEnrollProductServiceImpl extends ServiceImpl<DesignEnrollProductMapper, DesignEnrollProduct> implements IDesignEnrollProductService {

    @Autowired
    IDesignActivityService designActivityService;
    @Autowired
    IDesignEnrollProductWorkService designEnrollProductWorkService;


    @Override
    public void saveProduct(DesignTopProductVO designTopProductVO) {
        DesignEnrollProduct bean = new DesignEnrollProduct();
        BeanUtils.copyProperties(designTopProductVO, bean);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (sysUser != null) {

            bean.setCreateBy(sysUser.getUsername());
        }
        bean.setCreateTime(new Date());
        this.save(bean);

        Integer productId = bean.getId();

        List<String> productImgUrls = designTopProductVO.getProductImgUrls();
        List<DesignEnrollProductWork> designTopProductWorkList = new ArrayList<>();
        for (int i = 0; i < productImgUrls.size(); i++) {
            DesignEnrollProductWork designTopProductWork = new DesignEnrollProductWork();
            designTopProductWork.setProductId(productId);
            designTopProductWork.setWorkUrl(productImgUrls.get(i));
            designTopProductWork.setSort(i + 1);
            if (sysUser != null) {
                designTopProductWork.setCreateBy(sysUser.getUsername());
            }
            designTopProductWork.setCreateTime(new Date());
            designTopProductWorkList.add(designTopProductWork);
        }
        designEnrollProductWorkService.saveBatch(designTopProductWorkList);
    }

    @Override
    public void editProduct(DesignTopProductVO designTopProductVO) {
        if (designTopProductVO.getId() == null) {
            throw new BusinessException("请输入项目ID");
        }

        DesignEnrollProduct bean = new DesignEnrollProduct();
        BeanUtils.copyProperties(designTopProductVO, bean);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (sysUser != null) {
            bean.setUpdateBy(sysUser.getUsername());
        }
        bean.setUpdateTime(new Date());
        this.updateById(bean);

        QueryWrapper<DesignEnrollProductWork> queryWrapper = new QueryWrapper();
        queryWrapper.eq("product_id", designTopProductVO.getId());

        designEnrollProductWorkService.remove(queryWrapper);

        List<String> productImgUrls = designTopProductVO.getProductImgUrls();
        List<DesignEnrollProductWork> designEnrollProductWorkList = new ArrayList<>();
        for (int i = 0; i < productImgUrls.size(); i++) {
            DesignEnrollProductWork designEnrollProductWork = new DesignEnrollProductWork();
            designEnrollProductWork.setProductId(designTopProductVO.getId());
            designEnrollProductWork.setWorkUrl(productImgUrls.get(i));
            designEnrollProductWork.setSort(i + 1);
            if (sysUser != null) {
                designEnrollProductWork.setCreateBy(sysUser.getUsername());
            }
            designEnrollProductWork.setCreateTime(new Date());
            designEnrollProductWorkList.add(designEnrollProductWork);
        }

        designEnrollProductWorkService.saveBatch(designEnrollProductWorkList);

    }

    /**
     * 批量编辑项目信息 删除在添加
     *
     * @param designTopProductVOs
     * @param judgesId
     */
    @Override
    public void editProducsts(List<DesignTopProductVO> designTopProductVOs, int judgesId) {

        QueryWrapper<DesignEnrollProduct> productQueryWrapper = new QueryWrapper();
        productQueryWrapper.eq("top_judges_id", judgesId);

        List<DesignEnrollProduct> designTopProductVOS = this.list(productQueryWrapper);

        if (CollectionUtils.isNotEmpty(designTopProductVOS)) {
            //  删除
            List<Integer> productIds = designTopProductVOS.stream().map(DesignEnrollProduct::getId).collect(Collectors.toList());
            QueryWrapper<DesignEnrollProductWork> queryWrapper = new QueryWrapper<>();
            queryWrapper.in("product_id", productIds);
            designEnrollProductWorkService.remove(queryWrapper);
            this.removeByIds(productIds);
        }
        designTopProductVOs.forEach(productvo -> {
            productvo.setTopJudgesId(judgesId);
            this.saveProduct(productvo);
        });
    }


    @Override
    public DesignTopProductVO queryByProductId(Integer id) {
        DesignEnrollProduct product = this.getById(id);
        DesignTopProductVO resultVO = new DesignTopProductVO();
        BeanUtils.copyProperties(product, resultVO);

        QueryWrapper<DesignEnrollProductWork> queryWrapper = new QueryWrapper();
        queryWrapper.eq("product_id", id);
        queryWrapper.orderByAsc("sort");
        List<DesignEnrollProductWork> list = designEnrollProductWorkService.list(queryWrapper);
        resultVO.setProductImgUrls(list.stream().map(DesignEnrollProductWork::getWorkUrl).collect(Collectors.toList()));

        return resultVO;
    }


    @Override
    public List<DesignTopProductVO> queryDetailByJudgesId(Integer id) {
        List<DesignTopProductVO> result = new ArrayList<>();
        DesignEnrollProduct product = this.getById(id);
        DesignTopProductVO resultVO = new DesignTopProductVO();
        BeanUtils.copyProperties(product, resultVO);

        QueryWrapper<DesignEnrollProduct> productQueryWrapper = new QueryWrapper();
        productQueryWrapper.eq("top_judges_id", id);

        List<DesignEnrollProduct> products = this.list(productQueryWrapper);
        if (products == null || products.isEmpty()) {
            return result;
        }
        result = products.stream().map(pro -> {
            DesignTopProductVO designTopProductVO = new DesignTopProductVO();
            BeanUtils.copyProperties(pro, designTopProductVO);

            QueryWrapper<DesignEnrollProductWork> queryWrapper = new QueryWrapper();
            queryWrapper.eq("product_id", id);
            List<DesignEnrollProductWork> list = designEnrollProductWorkService.list(queryWrapper);
            designTopProductVO.setProductImgUrls(list.stream().map(DesignEnrollProductWork::getWorkUrl).collect(Collectors.toList()));

            return designTopProductVO;
        }).collect(Collectors.toList());

        return result;
    }

    @Override
    public void updateTopStatusByDesignNos(List<String> designNos) {
        this.baseMapper.updateTopStatusByDesignNos(designNos,2);
    }

    @Override
    public List<DesignTopProductVO> queryByTopJudgesId(Integer topJudgesId) {
        QueryWrapper<DesignEnrollProduct> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("top_judges_id", topJudgesId);
        List<DesignEnrollProduct> list = this.list(queryWrapper);
        List<DesignTopProductVO> result = list.stream().map(product -> {
            DesignTopProductVO resultVO = new DesignTopProductVO();
            BeanUtils.copyProperties(product, resultVO);

            QueryWrapper<DesignEnrollProductWork> workQueryWrapper = new QueryWrapper();
            workQueryWrapper.eq("product_id", product.getId());
            workQueryWrapper.orderByAsc("sort");
            List<DesignEnrollProductWork> workList = designEnrollProductWorkService.list(workQueryWrapper);
            resultVO.setProductImgUrls(workList.stream().map(DesignEnrollProductWork::getWorkUrl).collect(Collectors.toList()));
            return resultVO;
        }).collect(Collectors.toList());
        return result;
    }


    private String getDesignNo() {
        return "FX" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmsss");
    }

}
