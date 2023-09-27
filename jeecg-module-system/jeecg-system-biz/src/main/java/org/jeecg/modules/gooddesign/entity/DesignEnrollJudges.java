package org.jeecg.modules.gooddesign.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 好设计-发现100-设计师信息
 * @Author: jeecg-boot
 * @Date: 2023-08-20
 * @Version: V1.0
 */
@Data
@TableName("design_enroll_judges")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "报名信息", description = "好设计-报名-报名信息")
public class DesignEnrollJudges implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    @Excel(name = "ID", width = 15)
    private Integer id;
    /**
     * 创建人
     */
    @ApiModelProperty(value = "创建人")
    private String createBy;
    /**
     * 创建日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(value = "更新人")
    private String updateBy;
    /**
     * 更新日期
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "更新日期")
    private Date updateTime;
    /**
     * 序号
     */
    @Excel(name = "序号", width = 15)
    @ApiModelProperty(value = "序号")
    private Integer sort;
    /**
     * 姓名
     */
    @Excel(name = "设计师序号", width = 15)
    @ApiModelProperty(value = "姓名")
    private String realName;
    /**
     * 所属公司
     */
    @Excel(name = "所属公司", width = 15)
    @ApiModelProperty(value = "所属公司")
    private String company;
    /**
     * 联系方式
     */
    @Excel(name = "联系方式", width = 15)
    @ApiModelProperty(value = "联系方式")
    private String phone;

    /**
     * 职务
     */
    @Excel(name = "职务", width = 15)
    @ApiModelProperty(value = "职务")
    private String post;


    @Excel(name = "省", width = 15)
    @ApiModelProperty(value = "省")
    private String provinces;
    @ApiModelProperty(value = "市")
    private String city;

    /**
     * 地址
     */
    @Excel(name = "地址", width = 15)
    @ApiModelProperty(value = "地址")
    private String address;
    /**
     * 微信号
     */
    @Excel(name = "微信号", width = 15)
    @ApiModelProperty(value = "微信号")
    private String wechat;
    /**
     * 邮箱
     */
    @Excel(name = "邮箱", width = 15)
    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "个人描述")
    @Excel(name = "个人描述", width = 15)
    private String userDesc;


    @ApiModelProperty(value = "活动ID")
    @Excel(name = "活动ID", width = 15)
    private Integer activityId;

    @TableField(exist = false)
    @ApiModelProperty(value = "活动名称")
    @Excel(name = "活动名称", width = 15)
    private String activityName;

    @TableField(exist = false)
    @ApiModelProperty(value = "开始时间")
    private Date publishTime;

    @ApiModelProperty(value = "筛选状态 通过1 不通过0")
    @Excel(name = "筛选状态", width = 15)
    private Integer screenStatus;

    @ApiModelProperty(value = "肖像")
    @Excel(name = "肖像", width = 15)
    private String userImgUrl;

    @ApiModelProperty(value = "设计报奖编号")
    private String designNo;


    @ApiModelProperty(value = "loginId")
    private String loginId;


    @ApiModelProperty(value = "意愿参与壮观点演讲 1同意 0不同意")
    private Integer willStatus;

    @ApiModelProperty(value = "知识产权无争议 1是 0否")
    private Integer disputeStatus;

    @ApiModelProperty(value = "管理员删除标志 1删除 0未删除")
    private Integer managerDelStatus;

    @ApiModelProperty(value = "管理员推荐到top100标志 1推荐 0未推荐")
    private Integer topRecommendStatus;



}
