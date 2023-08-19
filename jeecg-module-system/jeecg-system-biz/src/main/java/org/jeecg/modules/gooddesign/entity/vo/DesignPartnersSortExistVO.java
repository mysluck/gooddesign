package org.jeecg.modules.gooddesign.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: 合作伙伴
 * @Author: jeecg-boot
 * @Date: 2023-08-19
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "合作伙伴序号", description = "合作伙伴序号")
public class DesignPartnersSortExistVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "类型 1战略合作伙伴 2合作机构 3媒体支持")
    private Integer partnerType;
}
