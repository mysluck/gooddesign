package org.jeecg.modules.gooddesign.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;

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
@ApiModel(value = "合作伙伴对象", description = "合作伙伴对象")
public class DesignPartnersPublishVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;

    /**
     * 名称
     */
    @Excel(name = "名称", width = 15)
    @ApiModelProperty(value = "名称")
    private String partnerName;
    /**
     * 链接
     */
    @Excel(name = "链接", width = 15)
    @ApiModelProperty(value = "链接")
    private String partnerUrl;
    /**
     * 排序
     */
    @Excel(name = "排序", width = 15)
    @ApiModelProperty(value = "排序")
    private Integer sort;
    /**
     * 发布状态 0未发布 1已发布
     */
    @ApiModelProperty(value = "发布状态 0未发布 1已发布")
    private Integer publishStatus;
    /**
     * 图片链接
     */
    @Excel(name = "图片链接", width = 15)
    @ApiModelProperty(value = "图片链接")
    private String partnerImgUrl;
    /**
     * 类型 1战略合作伙伴 2合作机构 3媒体支持
     */
    @ApiModelProperty(value = "类型 1战略合作伙伴 2合作机构 3媒体支持")
    private Integer partnerType;
}
