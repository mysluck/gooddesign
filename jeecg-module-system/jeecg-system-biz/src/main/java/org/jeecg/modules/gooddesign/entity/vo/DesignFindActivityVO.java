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
 * @Description: 好设计-发现设计-活动
 * @Author: jeecg-boot
 * @Date:   2023-08-24
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="好设计-发现设计-活动", description="好设计-发现设计-活动")
public class DesignFindActivityVO implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
    @ApiModelProperty(value = "主键")
    private Integer id;

	/**活动名称*/
	@Excel(name = "活动名称", width = 15)
    @ApiModelProperty(value = "活动名称")
    private String activityName;
	/**年份*/
	@Excel(name = "年份", width = 15)
    @ApiModelProperty(value = "年份")
    private String year;
	/**地点*/
	@Excel(name = "地点", width = 15)
    @ApiModelProperty(value = "地点")
    private String city;
	/**状态（0未发布 1已发布）*/
	@Excel(name = "状态（0未发布 1已发布）", width = 15)
    @ApiModelProperty(value = "状态（0未发布 1已发布）")
    private Integer activityStatus;
}
