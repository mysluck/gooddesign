package org.jeecg.modules.gooddesign.entity.vo;

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
import java.util.Date;

/**
 * @Description: 咨询中心
 * @Author: jeecg-boot
 * @Date:   2023-08-18
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="咨询中心对象", description="咨询中心")
public class DesignNewsVO implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
    @ApiModelProperty(value = "主键")
    private Integer id;

	/**标题*/
	@Excel(name = "标题", width = 15)
    @ApiModelProperty(value = "标题")
    private String title;
	/**发布时间*/
	@Excel(name = "发布时间", width = 20, format = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "发布时间")
    private Date publishTime;
	/**发布状态，0:未发布 1:已发布*/
	@Excel(name = "发布状态，0:未发布 1:已发布", width = 15)
    @ApiModelProperty(value = "发布状态，0:未发布 1:已发布")
    private Integer publishStatus;
	/**是否删除 0：未删除 1：已删除*/
	@Excel(name = "是否删除 0：未删除 1：已删除", width = 15)
    @ApiModelProperty(value = "是否删除 0：未删除 1：已删除")
    private Integer isDel;
	/**新闻内容*/
	@Excel(name = "新闻内容", width = 15)
    @ApiModelProperty(value = "新闻内容")
    private String newContent;
	/**新闻封面url*/
	@Excel(name = "新闻封面url", width = 15)
    @ApiModelProperty(value = "新闻封面url")
    private String newsCoverUrl;

}
