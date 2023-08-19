package org.jeecg.modules.gooddesign.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * @Description: 好设计-编辑壮游-相关人员映射表
 * @Author: jeecg-boot
 * @Date:   2023-08-19
 * @Version: V1.0
 */
@Data
@TableName("design_main_stakeholder")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="design_main_stakeholder对象", description="好设计-编辑壮游-相关人员映射表")
public class DesignMainStakeholder implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private java.util.Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private java.util.Date updateTime;
	/**mainId，关联main*/
	@Excel(name = "mainId，关联main", width = 15)
    @ApiModelProperty(value = "mainId，关联main")
    private Integer mainId;
	/**相关人员ID*/
	@Excel(name = "相关人员ID", width = 15)
    @ApiModelProperty(value = "相关人员ID")
    private Integer stakeholderId;
	/**相关人员类型（1推荐委员 2发现大使 3观点讲者）*/
	@Excel(name = "相关人员类型（1推荐委员 2发现大使 3观点讲者）", width = 15)
    @ApiModelProperty(value = "相关人员类型（1推荐委员 2发现大使 3观点讲者）")
    private Integer type;
}
