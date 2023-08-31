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
@ApiModel(value="design_judges_participants对象", description="评委通过表，保存评委评分数据")
public class DesignJudgesParticipants implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private java.lang.Integer id;
	/**评委ID*/
	@Excel(name = "评委ID", width = 15)
    @ApiModelProperty(value = "评委ID")
    private java.lang.Integer judgeId;
	/**参赛者ID*/
	@Excel(name = "参赛者ID", width = 15)
    @ApiModelProperty(value = "参赛者ID")
    private java.lang.Integer participantId;
}
