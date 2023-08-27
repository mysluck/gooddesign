package org.jeecg.modules.gooddesign.entity.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.modules.gooddesign.entity.DesignTopJudges;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;
import java.util.Date;

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
public class DesignTopJudgesVO extends DesignTopJudges implements Serializable {
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


    /**职务*/
    @Excel(name = "职务", width = 15)
    @ApiModelProperty(value = "职务")
    private String post;


    @ApiModelProperty(value = "省")
    private String provinces;
    @ApiModelProperty(value = "市")
    private String city;

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

    @ApiModelProperty(value = "个人描述")
    private String userDesc;

    @ApiModelProperty(value = "活动ID")
    private Integer activityId;

    @ApiModelProperty(value = "活动名称")
    private String activityName;

    @ApiModelProperty(value = "开始时间")
    private Date publishTime;

}
