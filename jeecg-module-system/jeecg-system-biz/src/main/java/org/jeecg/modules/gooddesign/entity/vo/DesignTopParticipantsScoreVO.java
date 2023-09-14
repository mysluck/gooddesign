package org.jeecg.modules.gooddesign.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @Description: 评委通过表，保存评委评分数据
 * @Author: jeecg-boot
 * @Date: 2023-08-17
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "评委打分", description = "评委打分")
public class DesignTopParticipantsScoreVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 参赛者ID
     */
    @Excel(name = "参赛者ID", width = 15)
    @ApiModelProperty(value = "参赛者ID")
    private Integer judgesId;

    @ApiModelProperty(value = "总分")
    private Double totalScore;
}
