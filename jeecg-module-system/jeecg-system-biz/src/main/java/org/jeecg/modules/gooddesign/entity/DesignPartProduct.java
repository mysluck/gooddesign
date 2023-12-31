//package org.jeecg.modules.gooddesign.entity;
//
//import com.baomidou.mybatisplus.annotation.IdType;
//import com.baomidou.mybatisplus.annotation.TableId;
//import com.baomidou.mybatisplus.annotation.TableName;
//import com.fasterxml.jackson.annotation.JsonFormat;
//import io.swagger.annotations.ApiModel;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//import lombok.EqualsAndHashCode;
//import lombok.experimental.Accessors;
//import org.jeecgframework.poi.excel.annotation.Excel;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import java.io.Serializable;
//
///**
// * @Description: 参赛者设计师作品
// * @Author: jeecg-boot
// * @Date:   2023-08-17
// * @Version: V1.0
// */
//@Data
//@TableName("design_part_product")
//@Accessors(chain = true)
//@EqualsAndHashCode(callSuper = false)
//@ApiModel(value="design_part_product对象", description="参赛者设计师作品")
//public class DesignPartProduct implements Serializable {
//    private static final long serialVersionUID = 1L;
//
//	/**主键*/
//	@TableId(type = IdType.AUTO)
//    @ApiModelProperty(value = "主键")
//    private java.lang.Integer id;
//	/**创建人*/
//    @ApiModelProperty(value = "创建人")
//    private java.lang.String createBy;
//	/**创建日期*/
//	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
//    @ApiModelProperty(value = "创建日期")
//    private java.util.Date createTime;
//	/**更新人*/
//    @ApiModelProperty(value = "更新人")
//    private java.lang.String updateBy;
//	/**更新日期*/
//	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
//    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
//    @ApiModelProperty(value = "更新日期")
//    private java.util.Date updateTime;
//
//	/**序号*/
//	@Excel(name = "序号", width = 15)
//    @ApiModelProperty(value = "序号")
//    private java.lang.Integer sort;
//	/**参赛者ID*/
//	@Excel(name = "参赛者ID", width = 15)
//    @ApiModelProperty(value = "参赛者ID")
//    private java.lang.Integer participantId;
//	/**作品名称*/
//	@Excel(name = "作品名称", width = 15)
//    @ApiModelProperty(value = "作品名称")
//    private java.lang.String prodName;
//	/**作品说明*/
//	@Excel(name = "作品说明", width = 15)
//    @ApiModelProperty(value = "作品说明")
//    private java.lang.String prodDesc;
//	/**初筛（1通过 0未通过）*/
//	@Excel(name = "初筛（1通过 0未通过）", width = 15)
//    @ApiModelProperty(value = "初筛（1通过 0未通过）")
//    private java.lang.Integer primaryScreening;
//}
