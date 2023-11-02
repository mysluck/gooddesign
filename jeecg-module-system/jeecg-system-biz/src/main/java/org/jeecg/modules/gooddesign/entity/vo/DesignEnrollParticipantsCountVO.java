package org.jeecg.modules.gooddesign.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: 报名数据评委打分表
 * @Author: jeecg-boot
 * @Date: 2023-08-17
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(value = "报名数据-评分数量", description = "评分数量")
public class DesignEnrollParticipantsCountVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "已完成评选数")
    private int totalCount;
    @ApiModelProperty(value = "总数")
    private int scoreCount;


}
