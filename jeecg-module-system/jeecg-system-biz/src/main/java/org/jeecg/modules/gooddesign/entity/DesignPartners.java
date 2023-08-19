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
 * @Description: 合作伙伴
 * @Author: jeecg-boot
 * @Date:   2023-08-19
 * @Version: V1.0
 */
@Data
@TableName("design_partners")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="design_partners对象", description="合作伙伴")
public class DesignPartners implements Serializable {
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
	/**是否删除 0未删除 1已删除*/
	@Excel(name = "是否删除 0未删除 1已删除", width = 15)
    @ApiModelProperty(value = "是否删除 0未删除 1已删除")
    private Integer isDel;
	/**名称*/
	@Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private String partnerName;
	/**链接*/
	@Excel(name = "链接", width = 15)
    @ApiModelProperty(value = "链接")
    private String partnerUrl;
	/**排序*/
	@Excel(name = "排序", width = 15)
    @ApiModelProperty(value = "排序")
    private Integer sort;
	/**发布状态 0未发布 1已发布*/
	@Excel(name = "发布状态 0未发布 1已发布", width = 15)
    @ApiModelProperty(value = "发布状态 0未发布 1已发布")
    private Integer publishStatus;
	/**图片链接*/
	@Excel(name = "图片链接", width = 15)
    @ApiModelProperty(value = "图片链接")
    private String partnerImgUrl;
	/**类型 1战略合作伙伴 2合作机构 3媒体支持*/
	@Excel(name = "类型 1战略合作伙伴 2合作机构 3媒体支持", width = 15)
    @ApiModelProperty(value = "类型 1战略合作伙伴 2合作机构 3媒体支持")
    private Integer partnerType;
}
