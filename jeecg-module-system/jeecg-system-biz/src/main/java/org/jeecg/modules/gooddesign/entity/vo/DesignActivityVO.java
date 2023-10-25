package org.jeecg.modules.gooddesign.entity.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @Description: 好设计-跨年启停
 * @Author: jeecg-boot
 * @Date: 2023-08-19
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "design_activity对象", description = "好设计-跨年启停")
public class DesignActivityVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;

    /**
     * 名称
     */
    @ApiModelProperty(value = "名称")
    private String activityName;


    @ApiModelProperty(value = "报名状态（0：未启动 1报名开始、2报名结束）")
    private Integer activityStatus;

    @ApiModelProperty(value = "打分状态 0：未开始 1：打分开始 2打分结束")
    private Integer scoreStatus;

    @ApiModelProperty(value = "发现100状态：0未生成 1：生成")
    private Integer topStatus;
}
