package org.jeecg.modules.gooddesign.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
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
	/**用户描述*/
	@Excel(name = "用户描述", width = 15)
    @ApiModelProperty(value = "用户描述")
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
	/**密码*/
	@Excel(name = "密码", width = 15)
    @ApiModelProperty(value = "密码")
    private String userPwd;
	/**打分权重*/
	@Excel(name = "打分权重", width = 15)
    @ApiModelProperty(value = "打分权重")
    private Integer weight;
}
