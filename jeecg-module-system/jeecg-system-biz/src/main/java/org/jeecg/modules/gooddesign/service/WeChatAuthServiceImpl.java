package org.jeecg.modules.gooddesign.service;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.util.RedisUtil;
import org.jeecg.modules.gooddesign.entity.vo.WeChatUserInfo;
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
    RedisUtil redisUtil;
    @Autowired
    RestTemplate restTemplate;

    static String appid = "wx46bd06cbf7d37908";
    static String secret = "35ba3dc865d6543ee255cd42ad641f98";

    /**
     * 获取token
     *
     * @param code
     * @return
     */
    @Override
    public WxAccessTokenVO assess_token(String code) {
        if (redisUtil.hasKey(code)) {
            return (WxAccessTokenVO) redisUtil.get(code);
        }
        return assess_token(code, appid, secret);
    }

    public WxAccessTokenVO assess_token(String code, String appId, String secret) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&secret=" + secret + "&code=" + code + "&grant_type=authorization_code";
        log.info("请求URL：{}", url);
        String forObject = restTemplate.getForObject(url, String.class);
        log.info("微信登录返回内容为：{}", forObject);
        WxAccessTokenVO wxAccessTokenVO = JSONObject.parseObject(forObject, WxAccessTokenVO.class);
//        wxAccessTokenVO = restTemplate.getForObject(url, WxAccessTokenVO.class);
        if (wxAccessTokenVO == null || wxAccessTokenVO.getOpenid() == null) {
            return null;
        }
        redisUtil.set(code, wxAccessTokenVO, 7200);
        redisUtil.set("refresh_token:" + code, wxAccessTokenVO.getRefresh_token(), 2592000);
        return wxAccessTokenVO;
    }


    /**
     * 刷新
     *
     * @param refresh_token
     * @return
     */
    @Override
    public WxAccessTokenVO refresh_token(String refresh_token) {
        return refresh_token(appid, refresh_token);
    }


    public WxAccessTokenVO refresh_token(String appId, String refresh_token) {
        WxAccessTokenVO wxAccessTokenVO = new WxAccessTokenVO();
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + appId + "&grant_type=refresh_token&refresh_token=" + refresh_token;
        wxAccessTokenVO = restTemplate.getForObject(url, WxAccessTokenVO.class);
        return wxAccessTokenVO;
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
    public WeChatUserInfo userinfo(String access_token, String openId) {
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openId;
        return restTemplate.getForObject(url, WeChatUserInfo.class);

    }


}
