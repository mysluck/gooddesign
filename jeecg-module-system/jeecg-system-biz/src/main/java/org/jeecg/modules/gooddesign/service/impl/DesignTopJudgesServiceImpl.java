package org.jeecg.modules.gooddesign.service.impl;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.gooddesign.entity.DesignTopJudges;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopJudgesAllVO;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopJudgesScoreVO;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopProductVO;
import org.jeecg.modules.gooddesign.mapper.DesignTopJudgesMapper;
import org.jeecg.modules.gooddesign.service.IDesignTopJudgesService;
import org.jeecg.modules.gooddesign.service.IDesignTopProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Date;
import java.util.List;

/**
 * @Description: 好设计-发现100-设计师信息
 * @Author: jeecg-boot
 * @Date: 2023-08-20
 * @Version: V1.0
 */
@Service
public class DesignTopJudgesServiceImpl extends ServiceImpl<DesignTopJudgesMapper, DesignTopJudges> implements IDesignTopJudgesService {
    @Autowired
    IDesignTopProductService designTopProductService;

    @Override
    public List<DesignTopJudgesScoreVO> queryByTopJudgesId() {

        return null;
    }

    @Override
    public void addDetail(DesignTopJudgesAllVO designTopJudgesAllVO) {
        DesignTopJudges bean = new DesignTopJudges();
        BeanUtils.copyProperties(designTopJudgesAllVO, bean);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        bean.setCreateBy(sysUser.getUsername());
        bean.setCreateTime(new Date());
        bean.setDesignNo(getDesignNo());
        this.save(bean);

        DesignTopProductVO designTopProductVO = new DesignTopProductVO();
        designTopProductVO.setProductName(designTopJudgesAllVO.getProductName());
        designTopProductVO.setTopJudgesId(bean.getId());
        designTopProductVO.setProductImgUrls(designTopJudgesAllVO.getProductImgUrls());
        designTopProductService.saveProduct(designTopProductVO);
    }


    private String getDesignNo() {
        return "FX" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmsss");
    }

}
