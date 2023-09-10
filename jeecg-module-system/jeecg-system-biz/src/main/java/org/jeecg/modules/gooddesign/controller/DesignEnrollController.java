package org.jeecg.modules.gooddesign.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.modules.gooddesign.entity.DesignEnrollProduct;
import org.jeecg.modules.gooddesign.entity.DesignTopJudges;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopJudgesDetailVO;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopProductVO;
import org.jeecg.modules.gooddesign.service.IDesignActivityService;
import org.jeecg.modules.gooddesign.service.IDesignEnrollProductService;
import org.jeecg.modules.gooddesign.service.IDesignTopJudgesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


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
    public Result<String> addProduct(HttpServletRequest request, HttpServletResponse response, @RequestBody DesignTopProductVO designTopProductVO) {
        String token = request.getHeader(CommonConstant.X_ACCESS_TOKEN);
        if (StringUtils.isEmpty(token)) {
            return Result.error("请先登录！");
        }
        String username = JwtUtil.getUsername(token);
        if (StringUtils.isEmpty(username)) {
            return Result.error("请先登录！");
        }

        designEnrollProductService.saveProduct(designTopProductVO);
        return Result.OK("添加成功！");
    }


    @AutoLog(value = "好设计-报名-添加设计师信息和作品信息")
    @ApiOperation(value = "好设计-报名-添加设计师信息和作品信息", notes = "好设计-报名-添加设计师信息和作品信息")
    @PostMapping(value = "/addDetail")
    public Result<String> addDetail(HttpServletRequest request, HttpServletResponse response,@RequestBody DesignTopJudgesDetailVO designTopJudgesAllVO) {
        String token = request.getHeader(CommonConstant.X_ACCESS_TOKEN);
        if (StringUtils.isEmpty(token)) {
            return Result.error("请先登录！");
        }
        String username = JwtUtil.getUsername(token);
        if (StringUtils.isEmpty(username)) {
            return Result.error("请先登录！");
        }   if (designTopJudgesAllVO.getLoginId() == null) {
            return Result.OK("请输入报名唯一信息！");
        }
        designEnrollProductService.addDetail(designTopJudgesAllVO);
        return Result.OK("添加成功！");
    }


    @ApiOperation(value = "好设计-报名-根据设计师ID查询", notes = "好设计-报名-根据设计师ID查询")
    @GetMapping(value = "/queryDetailByJudgesId")
    public Result<List<DesignTopProductVO>> queryDetailByJudgesId(@RequestParam(name = "id", required = true) Integer id) {

        List<DesignTopProductVO> designTopProduct = designEnrollProductService.queryDetailByJudgesId(id);
        if (designTopProduct == null || designTopProduct.isEmpty()) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(designTopProduct);
    }


}
