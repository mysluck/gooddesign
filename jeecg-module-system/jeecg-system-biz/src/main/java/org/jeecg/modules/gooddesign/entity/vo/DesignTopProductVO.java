package org.jeecg.modules.gooddesign.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @Description: 好设计-发现100-项目信息
 * @Author: jeecg-boot
 * @Date: 2023-08-20
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "design_top_product对象", description = "好设计-发现100-项目信息")
public class DesignTopProductVO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer id;
    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称")
    @NotNull
    private String productName;

    @ApiModelProperty(value = "设计说明")
    private String productDesc;
    /**
     * 发现100设计师id
     */
    @ApiModelProperty(value = "发现100设计师id")
    @NotNull
    private Integer topJudgesId;

    @ApiModelProperty(value = "作品链接集合")
    @NotNull
    List<String> productImgUrls;
}
