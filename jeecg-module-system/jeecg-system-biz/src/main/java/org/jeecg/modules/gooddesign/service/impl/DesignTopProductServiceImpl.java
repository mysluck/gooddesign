package org.jeecg.modules.gooddesign.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeecg.weibo.exception.BusinessException;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
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
public class DesignTopProductServiceImpl extends ServiceImpl<DesignTopProductMapper, DesignTopProduct> implements IDesignTopProductService {

    @Autowired
    IDesignActivityService designActivityService;
    @Autowired
    IDesignTopProductWorkService designTopProductWorkService;

    @Override
    public void saveProduct(DesignTopProductVO designTopProductVO) {

        DesignTopProduct bean = new DesignTopProduct();
        BeanUtils.copyProperties(designTopProductVO, bean);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (sysUser != null) {
            bean.setCreateBy(sysUser.getUsername());
        }
        bean.setCreateTime(new Date());
        this.save(bean);

        Integer productId = bean.getId();

        List<String> productImgUrls = designTopProductVO.getProductImgUrls();
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
}
