package org.jeecg.modules.gooddesign.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @Description: 设计壮游
 * @Author: jeecg-boot
 * @Date: 2023-08-18
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "设计壮游-基础信息对象", description = "设计壮游-基础信息对象")
public class DesignMainBasicVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;

    @Excel(name = "年份", width = 15)
    @ApiModelProperty(value = "年份")
    private int yearId;
    /**
     * 城市
     */
    @Excel(name = "城市", width = 15)
    @ApiModelProperty(value = "城市")
    private int cityId;
    /**
     * 标题
     */
    @Excel(name = "标题", width = 15)
    @ApiModelProperty(value = "标题")
    private String title;
    /**
     * 介绍
     */
    @Excel(name = "介绍", width = 15)
    @ApiModelProperty(value = "介绍")
    private String content;


    @ApiModelProperty(value = "状态 0：报名 1:提交")
    private Integer commitStatus;

}
