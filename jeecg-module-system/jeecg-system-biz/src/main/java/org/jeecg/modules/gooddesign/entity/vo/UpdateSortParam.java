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
@ApiModel(value = "修改sort", description = "修改sort")
public class UpdateSortParam {

    @ApiModelProperty(value = "报名数据的报奖编号")
    String designNo;

    @ApiModelProperty(value = "序号")
    int sort;

}
