package org.jeecg.modules.gooddesign.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;
import java.util.List;

/**
 * @Description: 好设计-发现100-项目作品
 * @Author: jeecg-boot
 * @Date: 2023-08-20
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "好设计-发现100-项目作品批量保存", description = "好设计-发现100-项目作品")
public class DesignTopProductWorkIdVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 项目名称
     */
    @Excel(name = "项目名称", width = 15)
    @ApiModelProperty(value = "项目ID")
    private Integer productId;
    /**
     * 作品链接
     */
    @Excel(name = "作品链接", width = 15)
    @ApiModelProperty(value = "作品链接集合")
    private List<String> workUrl;

}
