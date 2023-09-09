package org.jeecg.modules.gooddesign.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "报名信息", description = "报名信息")
public class LoginVO {
    @ApiModelProperty("第三方登陆微信ID")
    private String loginId;

    @ApiModelProperty("是否报名 true 是 false 否")
    private boolean enroll;

    @ApiModelProperty("报名ID")
    private Integer id;


}
