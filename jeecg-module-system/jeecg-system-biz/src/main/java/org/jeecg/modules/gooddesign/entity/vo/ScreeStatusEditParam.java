package org.jeecg.modules.gooddesign.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @Description:
 * @Author: zhouziyu
 * @Date: 2023/9/24 11:24
 * @Version: V1.0
 */

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "报名信息", description = "报名信息")
public class ScreeStatusEditParam {

    @ApiModelProperty(value = "报名ID（主键ID）集合")
    List<Integer> enrollIds;

    @ApiModelProperty(value = "通过1 不通过0")
    int screenStatus;

}
