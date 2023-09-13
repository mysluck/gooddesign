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
@ApiModel(value = "跨年启停活动信息", description = "好设计-跨年启停活动信息")
public class DesignActivityDetailVO extends DesignActivityVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 状态（1 启动 0未启动 2结束）
     */
    @ApiModelProperty(value = "活动发布年份")
    private String publishYear;
}
