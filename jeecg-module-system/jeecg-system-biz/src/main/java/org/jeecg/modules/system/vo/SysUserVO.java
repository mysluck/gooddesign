package org.jeecg.modules.system.vo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @Author scott
 * @since 2018-12-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="设计师对象", description="设计师")
public class SysUserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID，添加时为空，编辑时传入此ID")
    private String id;

    /**
     * 登录账号
     */
    @ApiModelProperty(value = "登录账号")
    private String username;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value = "真实姓名")
    private String realname;

    /**
     * 密码
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ApiModelProperty(value = "用户密码")
    private String password;


    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String avatar;

    /**
     * 生日
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "生日")
    private Date birthday;

    /**
     * 性别（1：男 2：女）
     */
    @ApiModelProperty(value = "性别（1：男 2：女）")
    private Integer sex;

    /**
     * 电子邮件
     */
    @ApiModelProperty(value = "电子邮件")
    private String email;

    /**
     * 电话
     */
    @ApiModelProperty(value = "电话")
    private String phone;



    /**
     * 状态(1：正常  2：冻结 ）
     */
    @ApiModelProperty(value = "状态(1：正常  2：冻结 ）")
    private Integer status;

    /**
     * 删除状态（0，正常，1已删除）
     */
    @ApiModelProperty(value = "删除状态（0，正常，1已删除）")
    private Integer delFlag;

    /**
     * 打分权重
     */
    @ApiModelProperty(value = "打分权重")
    private Double weight;

    @ApiModelProperty(value = "用户描述")
    private String userDesc;
    /**
     * 用户身份 0发起人 1评委
     */
    @ApiModelProperty(value = "用户身份 0发起人 1评委")
    private String userType;

    /**
     * 序号
     */
    @ApiModelProperty(value = "序号")
    private Integer sort;    /**
     * 序号
     */
    @ApiModelProperty(value = "用户身份 1超级管理员 2专家评委")
    private Integer roleId;
}
