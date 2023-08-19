package org.jeecg.modules.gooddesign.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: 设计壮游
 * @Author: jeecg-boot
 * @Date: 2023-08-18
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "设计壮游-现场视频对象", description = "设计壮游-现场视频对象")
public class DesignMainMovieVO implements Serializable {

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;

    @ApiModelProperty(value = "视频链接")
    private String movieUrl;
    /**封面链接*/
    @ApiModelProperty(value = "封面链接")
    private String imgCoverUrl;

}
