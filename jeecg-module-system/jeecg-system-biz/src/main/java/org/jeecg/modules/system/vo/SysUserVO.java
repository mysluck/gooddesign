package org.jeecg.modules.system.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
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
    @Excel(name = "真实姓名", width = 15)
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
    @Excel(name = "头像", width = 15, type = 2)
    @ApiModelProperty(value = "头像")
    private String avatar;

    /**
     * 生日
     */
    @Excel(name = "生日", width = 15, format = "yyyy-MM-dd")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "生日")
    private Date birthday;

    /**
     * 性别（1：男 2：女）
     */
    @Excel(name = "性别", width = 15, dicCode = "sex")
    @Dict(dicCode = "sex")
    @ApiModelProperty(value = "性别（1：男 2：女）")
    private Integer sex;

    /**
     * 电子邮件
     */
    @Excel(name = "电子邮件", width = 15)
    @ApiModelProperty(value = "电子邮件")
    private String email;

    /**
     * 电话
     */
    @Excel(name = "电话", width = 15)
    @ApiModelProperty(value = "电话")
    private String phone;



    /**
     * 状态(1：正常  2：冻结 ）
     */
    @Excel(name = "状态", width = 15, dicCode = "user_status")
    @Dict(dicCode = "user_status")
    @ApiModelProperty(value = "状态(1：正常  2：冻结 ）")
    private Integer status;

    /**
     * 删除状态（0，正常，1已删除）
     */
    @Excel(name = "删除状态", width = 15, dicCode = "del_flag")
    @TableLogic
    @ApiModelProperty(value = "删除状态（0，正常，1已删除）")
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
    @Excel(name = "用户身份 0发起人 1评委", width = 15)
    @ApiModelProperty(value = "用户身份 0发起人 1评委")
    private String userType;

    /**
     * 序号
     */
    @Excel(name = "序号", width = 15)
    @ApiModelProperty(value = "序号")
    private Integer sort;
}
