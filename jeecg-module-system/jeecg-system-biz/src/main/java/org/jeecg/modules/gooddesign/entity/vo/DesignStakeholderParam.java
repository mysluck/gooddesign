package org.jeecg.modules.gooddesign.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 相关人员（推荐委员、发现大使、观点讲者）
 * @Author: jeecg-boot
 * @Date: 2023-08-19
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "相关人员对象", description = "相关人员（推荐委员、发现大使、观点讲者）")
public class DesignStakeholderParam implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;
    /**
     * 姓名
     */
    @Excel(name = "姓名", width = 15)
    @ApiModelProperty(value = "姓名")
    private String name;
    /**
     * 介绍
     */
    @Excel(name = "介绍", width = 15)
    @ApiModelProperty(value = "介绍")
    private String userDesc;
    /**
     * 肖像照
     */
    @Excel(name = "肖像照", width = 15)
    @ApiModelProperty(value = "肖像照")
    private String userImgUrl;
    /**
     * 相关人员类型（1推荐委员 2发现大使 3观点讲者）
     */
    @Excel(name = "相关人员类型（1推荐委员 2发现大使 3观点讲者）", width = 15)
    @ApiModelProperty(value = "相关人员类型（1推荐委员 2发现大使 3观点讲者）")
    private Integer type;

    @ApiModelProperty(value = "作品集合")
    List<String> productUrls;
}
