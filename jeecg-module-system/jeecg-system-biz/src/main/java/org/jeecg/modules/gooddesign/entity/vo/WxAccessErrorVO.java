package org.jeecg.modules.gooddesign.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @Description:
 * @Author: zhouziyu
 * @Date: 2023/9/7 21:13
 * @Version: V1.0
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class WxAccessErrorVO {
    private String errcode;
    private String errmsg;

}
