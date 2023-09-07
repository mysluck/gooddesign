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
public class WxAccessTokenVO {
//    {"access_token":"72_ka_gOEmIJRMGEas1JxVd5FZ1Kf66h5tdfQdMEaGy3NEnaUpRKSFIm0quFpPCFJwlISB0dsYXayH_lKokCuoKsL2ML2_Pk6IBb1luMcHg6Lw","expires_in":7200,"refresh_token":"72_PypxmGideN5pl0XfvrjLrLaJIiIobDZN4-7uyEiTBf1eS8qt1EjW6wgoViPEfBx5JoL4k13wajVtRYa508letubYy4QuebQiejEHMlsxKHo","openid":"oQ63N6cU-IqBkadQFsIgoHav3HzY","scope":"snsapi_login","unionid":"ok5Tc6X7uQyUs_GbgiCB37Av4x04"}

    @ApiModelProperty("token")
    private String access_token;
    private String expires_in;
    private String refresh_token;
    @ApiModelProperty("用户唯一标志")
    private String openid;
    private String scope;
    private String unionid;
}
