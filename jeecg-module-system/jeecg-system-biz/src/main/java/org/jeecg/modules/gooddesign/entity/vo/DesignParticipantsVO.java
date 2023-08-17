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
@ApiModel(value="设计师-参赛人员", description="设计师-参赛人员")
public class DesignParticipantsVO implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
    @ApiModelProperty(value = "主键")
    private Integer id;

	/**是否删除*/
    @ApiModelProperty(value = "是否删除")
    private Integer isDel;
	/**姓名*/
    @ApiModelProperty(value = "姓名")
    private String realName;
	/**用户名*/
    @ApiModelProperty(value = "用户名")
    private String userName;
	/**个人简介*/
    @ApiModelProperty(value = "个人简介")
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

	/**打分权重*/
    @ApiModelProperty(value = "打分权重")
    private Integer weight;
	/**性别(0-默认未知,1-男,2-女)*/
    @ApiModelProperty(value = "性别(0-默认未知,1-男,2-女)")
    private Integer sex;
	/**公司名称*/
    @ApiModelProperty(value = "公司名称")
    private String company;
	/**职务*/
    @ApiModelProperty(value = "职务")
    private String post;
	/**地址*/
    @ApiModelProperty(value = "地址")
    private String address;
	/**微信号*/
    @ApiModelProperty(value = "微信号")
    private String wechat;
	/**邮箱*/
    @ApiModelProperty(value = "邮箱")
    private String email;
}
