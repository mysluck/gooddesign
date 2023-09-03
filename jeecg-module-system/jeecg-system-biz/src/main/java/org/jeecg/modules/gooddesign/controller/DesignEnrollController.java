package org.jeecg.modules.gooddesign.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.gooddesign.entity.DesignEnrollProduct;
import org.jeecg.modules.gooddesign.entity.DesignTopJudges;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopProductVO;
import org.jeecg.modules.gooddesign.service.IDesignActivityService;
import org.jeecg.modules.gooddesign.service.IDesignEnrollProductService;
import org.jeecg.modules.gooddesign.service.IDesignTopJudgesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * @Description: 好设计-报名
 * @Author: jeecg-boot
 * @Date: 2023-08-20
 * @Version: V1.0
 */
@Api(tags = "好设计-报名")
@RestController
@RequestMapping("/designEnroll")
@Slf4j
public class DesignEnrollController extends JeecgController<DesignEnrollProduct, IDesignEnrollProductService> {
    @Autowired
    IDesignActivityService designActivityService;
    @Autowired
    IDesignEnrollProductService designEnrollProductService;


    @AutoLog(value = "好设计-报名-添加作品信息")
    @ApiOperation(value = "好设计-报名-添加作品信息", notes = "好设计-报名-添加作品信息")
    @PostMapping(value = "/addProduct")
    public Result<String> addProduct(@RequestBody DesignTopProductVO designTopProductVO) {
        designEnrollProductService.saveProduct(designTopProductVO);
        return Result.OK("添加成功！");
    }


}
