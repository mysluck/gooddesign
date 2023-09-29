package org.jeecg.modules.gooddesign.entity;

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
 * @Description: 报名数据评委打分表
 * @Author: jeecg-boot
 * @Date: 2023-08-17
 * @Version: V1.0
 */
@Data
@TableName("design_enroll_participants")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "报名数据-关联评委打分添加打分状态字段", description = "关联评委打分添加打分状态字段")
public class DesignEnrollParticipantsScoreVO extends DesignEnrollJudges implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "打分状态 0待定 1推荐 2不推荐")
    private java.lang.Integer scoreStatus;


}
