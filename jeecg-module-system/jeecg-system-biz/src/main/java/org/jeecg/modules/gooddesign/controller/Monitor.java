package org.jeecg.modules.gooddesign.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: 高凡
 * @Date: 2023/6/15 10:28
 * @Description:
 */
@Api(tags = "服务监听")
@RestController
@RequestMapping("/monitor")
public class Monitor {
    @GetMapping("/alive")
    @ApiOperation("服务状态监听接口")
    public String test(){
        return "ok";
    }
}
