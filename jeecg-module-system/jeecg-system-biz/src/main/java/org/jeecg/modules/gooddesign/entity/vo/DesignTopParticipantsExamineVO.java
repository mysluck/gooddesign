package org.jeecg.modules.gooddesign.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.modules.gooddesign.entity.DesignEnrollJudges;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @Description: 评委通过表，保存评委评分数据
 * @Author: jeecg-boot
 * @Date: 2023-08-17
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "评委打分", description = "评委打分")
public class DesignTopParticipantsExamineVO extends DesignEnrollJudges implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "打分状态 0待定  1推荐 2不推荐")
    private Integer scoreStatus;
}
