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
 * @Description: 相关人员（推荐委员、发现大使、观点讲者）
 * @Author: jeecg-boot
 * @Date:   2023-08-19
 * @Version: V1.0
 */
@Data
@TableName("design_stakeholder")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="design_stakeholder对象", description="相关人员（推荐委员、发现大使、观点讲者）")
public class DesignStakeholder implements Serializable {
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
	/**姓名*/
	@Excel(name = "姓名", width = 15)
    @ApiModelProperty(value = "姓名")
    private String name;
	/**介绍*/
	@Excel(name = "介绍", width = 15)
    @ApiModelProperty(value = "介绍")
    private String userDesc;
	/**肖像照*/
	@Excel(name = "肖像照", width = 15)
    @ApiModelProperty(value = "肖像照")
    private String userImgUrl;
	/**相关人员类型（1推荐委员 2发现大使 3观点讲者）*/
	@Excel(name = "相关人员类型（1推荐委员 2发现大使 3观点讲者）", width = 15)
    @ApiModelProperty(value = "相关人员类型（1推荐委员 2发现大使 3观点讲者）")
    private Integer type;
}
