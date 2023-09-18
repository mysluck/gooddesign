package org.jeecg.modules.gooddesign.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @Description: 好设计-编辑壮游-相关人员映射表
 * @Author: jeecg-boot
 * @Date: 2023-08-19
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "壮游-相关人员映射", description = "壮游-相关人员映射")
public class DesignMainStakeholderAddParam implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;

    /**
     * mainId，关联main
     */
    @Excel(name = "mainId，关联main", width = 15)
    @ApiModelProperty(value = "mainId，关联main")
    private Integer mainId;
    /**
     * 相关人员ID
     */
    @Excel(name = "相关人员ID", width = 15)
    @ApiModelProperty(value = "相关人员ID")
    private Integer stakeholderId;

    @Excel(name = "相关人员类型（1推荐委员 2发现大使 3观点讲者）", width = 15)
    @ApiModelProperty(value = "相关人员类型（1推荐委员 2发现大使 3观点讲者）")
    private Integer type;
}
