package org.jeecg.modules.gooddesign.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: 好设计-发现100-项目信息
 * @Author: jeecg-boot
 * @Date:   2023-08-20
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="design_top_product对象", description="好设计-发现100-项目信息")
public class DesignTopProductVO implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer id;
	/**项目名称*/
    @ApiModelProperty(value = "项目名称")
    private String productName;
	/**发现100设计师id*/
    @ApiModelProperty(value = "发现100设计师id")
    private Integer topJudgesId;
}
