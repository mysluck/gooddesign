package org.jeecg.modules.gooddesign.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description:
 * @Author: zhouziyu
 * @Date: 2023/9/3 9:52
 * @Version: V1.0
 */
@Data
@ApiModel(value = "设计壮游-基础信息-图片对象", description = "设计壮游-基础信息-图片对象")
public class DesignMainImageVO {
    @ApiModelProperty(value = "id")
    private java.lang.Integer id;
    @ApiModelProperty(value = "mainId，关联main")
    private java.lang.Integer mainId;
    /**
     * 图片链接
     */
    @ApiModelProperty(value = "图片链接")
    private java.lang.String imgUrl;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private java.lang.Integer sort;
}
