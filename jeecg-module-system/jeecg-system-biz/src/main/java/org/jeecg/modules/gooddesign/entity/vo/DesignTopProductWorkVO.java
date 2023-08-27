package org.jeecg.modules.gooddesign.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @Description: 好设计-发现100-项目作品
 * @Author: jeecg-boot
 * @Date:   2023-08-20
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="design_top_product_work对象", description="好设计-发现100-项目作品")
public class DesignTopProductWorkVO implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer id;
	/**项目名称*/
	@Excel(name = "项目名称", width = 15)
    @ApiModelProperty(value = "项目ID")
    private Integer productId;
	/**作品链接*/
	@Excel(name = "作品链接", width = 15)
    @ApiModelProperty(value = "作品链接")
    private String workUrl;
	/**排序*/
	@Excel(name = "排序", width = 15)
    @ApiModelProperty(value = "排序,不传则按顺序保存排序")
    private Integer sort;
}
