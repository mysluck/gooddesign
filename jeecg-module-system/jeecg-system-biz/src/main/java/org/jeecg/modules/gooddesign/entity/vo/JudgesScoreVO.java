package org.jeecg.modules.gooddesign.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.jeecgframework.poi.excel.annotation.Excel;

/**
 * @Description:
 * @Author: zhouziyu
 * @Date: 2023/10/10 19:52
 * @Version: V1.0
 */
@Data
public class JudgesScoreVO {
    @ApiModelProperty(value = "打分权重")
    private Double weight;

    @ApiModelProperty(value = "真实姓名")
    private String realname;

    @ApiModelProperty(value = "打分状态 0待定 1推荐 2不推荐")
    private Integer scoreStatus;

    @ApiModelProperty(value = "得分")
    private double score;
}
