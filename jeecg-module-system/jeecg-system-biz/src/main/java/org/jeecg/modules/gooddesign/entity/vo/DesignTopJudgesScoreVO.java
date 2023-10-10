package org.jeecg.modules.gooddesign.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.modules.gooddesign.entity.DesignTopJudges;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 好设计-发现100-设计师信息
 * @Author: jeecg-boot
 * @Date: 2023-08-20
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "发现100评分对象", description = "发现100评分对象")
public class DesignTopJudgesScoreVO extends DesignTopJudgesVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "评分")
    private Double score;

    @ApiModelProperty(value = "管理员推荐到top100标志 1推荐 0未推荐")
    private Integer topRecommendStatus;

}
