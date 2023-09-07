package org.jeecg.modules.gooddesign.service;

import org.jeecg.modules.gooddesign.entity.vo.WxAccessTokenVO;

/**
 * @Description:
 * @Author: zhouziyu
 * @Date: 2023/9/7 20:18
 * @Version: V1.0
 */

public interface WeChatAuthService {
    /**
     * 获取token
     *
     * @param code
     * @return
     */
    WxAccessTokenVO assess_token(String code);

    String refresh_token(String refresh_token);

    /**
     * {
     * "openid":"OPENID",
     * "nickname":"NICKNAME",
     * "sex":1,
     * "province":"PROVINCE",
     * "city":"CITY",
     * "country":"COUNTRY",
     * "headimgurl": "https://thirdwx.qlogo.cn/mmopen/g3MonUZtNHkdmzicIlibx6iaFqAc56vxLSUfpb6n5WKSYVY0ChQKkiaJSgQ1dZuTOgvLLrhJbERQQ4eMsv84eavHiaiceqxibJxCfHe/0",
     * "privilege":[
     * "PRIVILEGE1",
     * "PRIVILEGE2"
     * ],
     * "unionid": " o6_bmasdasdsad6_2sgVt7hMZOPfL"
     * <p>
     * }
     *
     * @param refresh_token
     * @return
     */
    String userinfo(String refresh_token);


}
