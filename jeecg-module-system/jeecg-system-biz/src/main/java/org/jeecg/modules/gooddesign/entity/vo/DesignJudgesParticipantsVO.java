package org.jeecg.modules.gooddesign.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

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
public class DesignJudgesParticipantsVO implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer id;
	/**评委ID*/
	@Excel(name = "评委ID", width = 15)
    @ApiModelProperty(value = "评委ID")
    private Integer judgeId;
	/**参赛者ID*/
	@Excel(name = "参赛者ID", width = 15)
    @ApiModelProperty(value = "参赛者ID")
    private Integer participantId;

    @ApiModelProperty(value = "打分状态 0待定  1推荐 2不推荐")
    private Integer scoreStatus;
}
