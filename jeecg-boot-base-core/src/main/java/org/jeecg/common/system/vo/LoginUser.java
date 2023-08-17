package org.jeecg.common.system.vo;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import org.jeecg.common.desensitization.annotation.SensitiveField;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 在线用户信息
 * </p>
 *
 * @Author scott
 * @since 2018-12-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class LoginUser {

    /**
     * 登录人id
     */
    @SensitiveField
    private String id;

    /**
     * 登录人账号
     */
    @SensitiveField
    private String username;

    /**
     * 登录人名字
     */
    @SensitiveField
    private String realname;

    /**
     * 登录人密码
     */
    @SensitiveField
    private String password;

    /**
     * 当前登录部门code
     */
    private String orgCode;
    /**
     * 头像
     */
    @SensitiveField
    private String avatar;

    /**
     * 生日
     */
    @SensitiveField
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    /**
     * 性别（1：男 2：女）
     */
    private Integer sex;

    /**
     * 电子邮件
     */
    @SensitiveField
    private String email;

    /**
     * 电话
     */
    @SensitiveField
    private String phone;

    /**
     * 状态(1：正常 2：冻结 ）
     */
    @ApiModelProperty(value = "状态(1：正常 2：冻结 ）")
    private Integer status;

    @ApiModelProperty(value = "删除状态(0-正常,1-已删除)")
    private Integer delFlag;
    /**
     * 打分权重
     */
    @Excel(name = "打分权重", width = 15)
    @ApiModelProperty(value = "打分权重")
    private Integer weight;

    @Excel(name = "用户描述", width = 15)
    @ApiModelProperty(value = "用户描述")
    private String userDesc;
    /**
     * 用户身份 0发起人 1评委
     */
    @Excel(name = "用户身份 1超级管理员 2专家评委", width = 15)
    @ApiModelProperty(value = "用户身份 1超级管理员 2专家评委")
    private Integer roleId;

    /**
     * 序号
     */
    @Excel(name = "序号", width = 15)
    @ApiModelProperty(value = "序号")
    private Integer sort;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @ApiModelProperty(hidden = true)
    private String departIds;
    @ApiModelProperty(hidden = true)
    private String relTenantIds;
    @ApiModelProperty(hidden = true)
    private String clientId;
    @ApiModelProperty(hidden = true)
    private Integer userIdentity;

}
