package org.jeecg.modules.gooddesign.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.modules.gooddesign.entity.DesignMainImage;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 设计壮游
 * @Author: jeecg-boot
 * @Date: 2023-08-18
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "设计壮游-详细对象", description = "设计壮游-详细对象")
public class DesignMainDetailVO implements Serializable {
    private static final long serialVersionUID = 1L;


    @ApiModelProperty("基础信息")
    DesignMainBasicVO designMainBasic;

    @ApiModelProperty("现场视频")
    List<DesignMainMovieVO> movies;

    @ApiModelProperty("现场照片")
    List<DesignMainImageVO> images;

}
