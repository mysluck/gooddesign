package org.jeecg.modules.gooddesign.entity.vo;

import lombok.Data;

/**
 * @Description:
 * @Author: zhouziyu
 * @Date: 2023/9/10 22:35
 * @Version: V1.0
 */
@Data
public class WeChatUserInfo extends WxAccessErrorVO {
    private String openid;
    private String nickname;
    private Integer sex;
    private String language;
    private String city;
    private String province;
    private String country;
    private String headimgurl;
    private String unionid;
}
