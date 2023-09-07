package org.jeecg.modules.gooddesign.service;

import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.jeecg.modules.gooddesign.entity.vo.WxAccessTokenVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @Description:
 * @Author: zhouziyu
 * @Date: 2023/9/7 20:18
 * @Version: V1.0
 */
@Slf4j
@Service
public class WeChatAuthServiceImpl implements WeChatAuthService {
    @Autowired
    RestTemplate restTemplate;

    /**
     * 获取token
     *
     * @param code
     * @return
     */
    @Override
    public WxAccessTokenVO assess_token(String code) {
        WxAccessTokenVO wxAccessTokenVO = new WxAccessTokenVO();
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx46bd06cbf7d37908&secret=35ba3dc865d6543ee255cd42ad641f98&code=" + code + "&grant_type=authorization_code";
        wxAccessTokenVO = restTemplate.getForObject(url, WxAccessTokenVO.class);
        return wxAccessTokenVO;
    }

    @Override
    public String refresh_token(String refresh_token) {
        return null;

    }


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
    @Override
    public String userinfo(String refresh_token) {
        return null;
    }


}
