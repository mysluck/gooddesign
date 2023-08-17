package org.jeecg.modules.gooddesign.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import org.jeecgframework.poi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description: 设计师
 * @Author: jeecg-boot
 * @Date:   2023-08-17
 * @Version: V1.0
 */
@Data
@TableName("design_participants")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="user_participants对象", description="设计师")
public class UserParticipants implements Serializable {
    private static final long serialVersionUID = 1L;

	/**主键*/
	@TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer id;
	/**创建人*/
    @ApiModelProperty(value = "创建人")
    private String createBy;
	/**创建日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
	/**更新人*/
    @ApiModelProperty(value = "更新人")
    private String updateBy;
	/**更新日期*/
	@JsonFormat(timezone = "GMT+8",pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
	/**所属部门*/
    @ApiModelProperty(value = "所属部门")
    private String sysOrgCode;
	/**是否删除*/
	@Excel(name = "是否删除", width = 15)
    @ApiModelProperty(value = "是否删除")
    private Integer isDel;
	/**姓名*/
	@Excel(name = "姓名", width = 15)
    @ApiModelProperty(value = "姓名")
    private String realName;
	/**用户名*/
	@Excel(name = "用户名", width = 15)
    @ApiModelProperty(value = "用户名")
    private String userName;
	/**个人简介*/
	@Excel(name = "个人简介", width = 15)
    @ApiModelProperty(value = "个人简介")
    private String userDesc;
	/**用户身份 0发起人 1评委*/
	@Excel(name = "用户身份 0发起人 1评委", width = 15)
    @ApiModelProperty(value = "用户身份 0发起人 1评委")
    private String userStatus;
	/**肖像照*/
	@Excel(name = "肖像照", width = 15)
    @ApiModelProperty(value = "肖像照")
    private String userImgUrl;
	/**序号*/
	@Excel(name = "序号", width = 15)
    @ApiModelProperty(value = "序号")
    private Integer sort;
	/**打分权重*/
	@Excel(name = "打分权重", width = 15)
    @ApiModelProperty(value = "打分权重")
    private Integer weight;
	/**性别(0-默认未知,1-男,2-女)*/
	@Excel(name = "性别(0-默认未知,1-男,2-女)", width = 15)
    @ApiModelProperty(value = "性别(0-默认未知,1-男,2-女)")
    private Integer sex;
	/**公司名称*/
	@Excel(name = "公司名称", width = 15)
    @ApiModelProperty(value = "公司名称")
    private String company;
	/**职务*/
	@Excel(name = "职务", width = 15)
    @ApiModelProperty(value = "职务")
    private String post;
	/**地址*/
	@Excel(name = "地址", width = 15)
    @ApiModelProperty(value = "地址")
    private String address;
	/**微信号*/
	@Excel(name = "微信号", width = 15)
    @ApiModelProperty(value = "微信号")
    private String wechat;
	/**邮箱*/
	@Excel(name = "邮箱", width = 15)
    @ApiModelProperty(value = "邮箱")
    private String email;
}
