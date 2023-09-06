package org.jeecg.modules.gooddesign.entity;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.TableLogic;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.jeecg.common.aspect.annotation.Dict;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 评委通过表，保存评委评分数据
 * @Author: jeecg-boot
 * @Date:   2023-08-17
 * @Version: V1.0
 */
@Data
@TableName("design_judges_participants")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="design_top_judges_participants对象", description="评委通过表，保存评委评分数据")
public class DesignTopJudgesParticipants implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private java.lang.Integer id;
    /**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
    /**创建日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
    /**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
    /**更新日期*/
    @JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;

    /**评委ID*/
	@Excel(name = "评委ID", width = 15)
    @ApiModelProperty(value = "评委ID")
    private java.lang.Integer judgeId;
	/**参赛者ID*/
	@Excel(name = "参赛者ID", width = 15)
    @ApiModelProperty(value = "参赛者ID")
    private java.lang.Integer participantId;

    @ApiModelProperty(value = "打分状态 0待定  1推荐 2不推荐")
    private java.lang.Integer scoreStatus;
}