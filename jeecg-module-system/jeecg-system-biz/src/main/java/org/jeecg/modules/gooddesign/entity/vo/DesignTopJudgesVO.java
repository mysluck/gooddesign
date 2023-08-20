package org.jeecg.modules.gooddesign.entity.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: 好设计-发现100-设计师信息
 * @Author: jeecg-boot
 * @Date:   2023-08-20
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="design_top_judges对象", description="好设计-发现100-设计师信息")
public class DesignTopJudgesVO implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
    @ApiModelProperty(value = "主键")
    private Integer id;

	/**序号*/
    @ApiModelProperty(value = "序号")
    private Integer sort;
	/**姓名*/
    @ApiModelProperty(value = "姓名")
    private String realName;
	/**所属公司*/
    @ApiModelProperty(value = "所属公司")
    private String company;
	/**联系方式*/
    @ApiModelProperty(value = "联系方式")
    private String phone;
}
