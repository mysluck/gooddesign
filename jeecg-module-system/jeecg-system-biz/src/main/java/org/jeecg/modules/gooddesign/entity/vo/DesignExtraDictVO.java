package org.jeecg.modules.gooddesign.entity.vo;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 壮游字典（年份、城市）
 * @Author: jeecg-boot
 * @Date:   2023-08-18
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="编辑壮游字典t对象", description="壮游字典（年份、城市）")
public class DesignExtraDictVO implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
    @ApiModelProperty(value = "主键")
    private Integer id;
	/**类型（年份（yyyy-MM-dd）2 城市）*/
    @ApiModelProperty(value = "类型（年份（yyyy-MM-dd）2 城市）")
    private Integer type;
	/**内容*/
    @ApiModelProperty(value = "内容")
    private String value;

    @ApiModelProperty(value = "父亲ID")
    private java.lang.Integer parentId;

    @ApiModelProperty(value = "子集对象")
    private List<DesignExtraDictVO> child;
}
