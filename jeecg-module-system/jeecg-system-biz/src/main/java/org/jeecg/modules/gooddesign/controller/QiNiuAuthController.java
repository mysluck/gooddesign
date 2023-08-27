package org.jeecg.modules.gooddesign.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.util.qiniu.Auth;
import org.jeecg.config.oss.QiNiuConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Description:
 * @Author: zhouziyu
 * @Date: 2023/8/22 8:40
 * @Version: V1.0
 */
@Api(tags = "好设计-七牛云")
@RestController
@RequestMapping("/auth")
@Slf4j
public class QiNiuAuthController {
    @Autowired
    QiNiuConfiguration qiNiuConfiguration;

    @ApiOperation(value = "好设计-七牛云-获取TOKEN", notes = "好设计-七牛云-获取TOKEN")
    @GetMapping("/getToken")
    public Result getToken() {
        Auth auth = Auth.create(qiNiuConfiguration.getAccessKeyId(), qiNiuConfiguration.getAccessKeySecret());
        String design = auth.uploadToken("design");
        return Result.OK(design);
    }
}
