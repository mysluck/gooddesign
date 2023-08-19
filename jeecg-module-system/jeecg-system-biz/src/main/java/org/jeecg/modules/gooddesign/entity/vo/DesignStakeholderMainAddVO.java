package org.jeecg.modules.gooddesign.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

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
public class DesignStakeholderMainAddVO implements Serializable {
    private static final long serialVersionUID = 1L;
    @NotNull
    @ApiModelProperty(value = "壮游ID")
    private int mainId;

    @NotNull
    @ApiModelProperty(value = "相关人员类型（1推荐委员 2发现大使 3观点讲者）")
    private int type;

    @NotNull
    @ApiModelProperty(value = "相关人员ID")
    private List<Integer> stakeholderIds;
}
