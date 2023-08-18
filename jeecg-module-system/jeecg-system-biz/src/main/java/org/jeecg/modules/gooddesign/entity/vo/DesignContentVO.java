package org.jeecg.modules.gooddesign.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: 文本描述
 * @Author: jeecg-boot
 * @Date:   2023-08-18
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="首页内容编辑对象", description="文本描述")
public class DesignContentVO implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
    @ApiModelProperty(value = "主键")
    private Integer id;
	/**文本*/
    @ApiModelProperty(value = "文本")
    private String content;
	/**类型（1发现设计 2设计壮游）*/
    @ApiModelProperty(value = "类型（1发现设计 2设计壮游 3发现100）")
    private Integer type;
}
