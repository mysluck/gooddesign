package org.jeecg.modules.gooddesign.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: 相关人员（推荐委员、发现大使、观点讲者）
 * @Author: jeecg-boot
 * @Date: 2023-08-19
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "相关人员对象", description = "相关人员（推荐委员、发现大使、观点讲者）")
public class DesignStakeholderMainParam extends DesignStakeholderParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "是否最近添加（1添加 0未添加）")
    private int recentAdd;
}
