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
@TableName("design_activity")
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

    /**
     * 状态（1 启动 0未启动 2结束）
     */
    @ApiModelProperty(value = "状态（1 启动 0未启动 2结束）")
    private int activityStatus;
}
