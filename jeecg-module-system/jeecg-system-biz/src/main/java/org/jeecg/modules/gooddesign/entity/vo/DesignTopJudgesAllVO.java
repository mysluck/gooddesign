package org.jeecg.modules.gooddesign.entity.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.modules.gooddesign.entity.DesignTopJudges;
import org.jeecgframework.poi.excel.annotation.Excel;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Description: 好设计-发现100-设计师信息
 * @Author: jeecg-boot
 * @Date: 2023-08-20
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "design_top_judges对象", description = "好设计-发现100-设计师信息")
public class DesignTopJudgesAllVO implements Serializable {

    @ApiModelProperty(value = "设计师信息")
    private DesignTopJudgesVO designTopJudges;

    @ApiModelProperty(value = "作品集合")
    private List<DesignTopProductVO> products;


//    private static final long serialVersionUID = 1L;
//    @ApiModelProperty(value = "项目名称")
//    @NotNull
//    private String productName;
//
//    private Integer topJudgesId;
//
//    @ApiModelProperty(value = "作品链接集合")
//    @NotNull
//    List<String> productImgUrls;

}
