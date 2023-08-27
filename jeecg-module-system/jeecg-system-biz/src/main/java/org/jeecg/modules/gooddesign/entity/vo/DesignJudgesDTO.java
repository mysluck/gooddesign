package org.jeecg.modules.gooddesign.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: 设计师
 * @Author: jeecg-boot
 * @Date:   2023-08-17
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="user_designer对象", description="设计师")
public class DesignJudgesDTO implements Serializable {
    private static final long serialVersionUID = 1L;

	/**是否删除*/
    @ApiModelProperty(value = "是否删除")
    private Integer isDel;
	/**姓名*/
    @ApiModelProperty(value = "姓名")
    private String realName;

	/**用户描述*/
    @ApiModelProperty(value = "用户描述")
    private String userDesc;
	/**用户身份 0发起人 1评委*/
    @ApiModelProperty(value = "用户身份 0发起人 1评委")
    private String userStatus;
	/**肖像照*/
    @ApiModelProperty(value = "肖像照")
    private String userImgUrl;
	/**序号*/
    @ApiModelProperty(value = "序号")
    private Integer sort;

}
