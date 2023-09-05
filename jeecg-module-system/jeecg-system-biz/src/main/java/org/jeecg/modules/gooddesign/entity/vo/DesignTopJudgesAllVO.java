package org.jeecg.modules.gooddesign.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 好设计-发现100-设计师信息
 * @Author: jeecg-boot
 * @Date: 2023-08-20
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "设计师对象", description = "好设计-发现100-设计师信息")
public class DesignTopJudgesAllVO implements Serializable{


    @ApiModelProperty(value = "作品集合")
    private List<DesignTopProductVO> products;

    @ApiModelProperty(value = "设计师信息")
    private DesignTopJudgesVO designTopJudges;

}
