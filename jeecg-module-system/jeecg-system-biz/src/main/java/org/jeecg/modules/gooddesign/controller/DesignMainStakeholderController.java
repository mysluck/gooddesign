//package org.jeecg.modules.gooddesign.controller;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import io.swagger.annotations.ApiParam;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.shiro.SecurityUtils;
//import org.jeecg.common.api.vo.Result;
//import org.jeecg.common.aspect.annotation.AutoLog;
//import org.jeecg.common.system.base.controller.JeecgController;
//import org.jeecg.common.system.vo.LoginUser;
//import org.jeecg.modules.gooddesign.entity.DesignMainStakeholder;
//import org.jeecg.modules.gooddesign.entity.DesignStakeholder;
//import org.jeecg.modules.gooddesign.entity.vo.*;
//import org.jeecg.modules.gooddesign.service.IDesignMainStakeholderService;
//import org.jeecg.modules.gooddesign.service.IDesignStakeholderService;
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.*;
//import java.util.stream.Collectors;
//
///**
// * @Description: 相关人员（推荐委员、发现大使、观点讲者）
// * @Author: jeecg-boot
// * @Date: 2023-08-19
// * @Version: V1.0
// */
//@Api(tags = "好设计-壮游-相关人员映射关系")
//@RestController
//@RequestMapping("/designMainStakeholder")
//@Slf4j
//public class DesignMainStakeholderController extends JeecgController<DesignMainStakeholder, IDesignMainStakeholderService> {
//    @Autowired
//    IDesignMainStakeholderService designMainStakeholderService;
//    @Autowired
//    private IDesignStakeholderService designStakeholderService;
//
//    /**
//     * 分页列表查询
//     *
//     * @return
//     */
//    //@AutoLog(value = "相关人员（推荐委员、发现大使、观点讲者）-分页列表查询")
//    @ApiOperation(value = "相关人员-根据活动ID查询", notes = "相关人员-根据活动ID查询")
//    @GetMapping(value = "/listByMainId")
//    public Result<List<DesignMainStakeholderVO>> queryPageList(@RequestParam(name = "mainID") Integer mainID) {
//        List<DesignMainStakeholderVO> designMainStakeholderVOS = designMainStakeholderService.queryMainStakeholder(mainID);
//        return Result.OK(designMainStakeholderVOS);
//    }
//
//    @AutoLog(value = "相关人员-批量编辑")
//    @ApiOperation(value = "相关人员-批量编辑", notes = "相关人员-批量编辑")
//    @PostMapping(value = "/batchEdit")
//    public Result<String> batchEdit(@RequestBody DesignStakeholderMainAddVO designMainStakeholderAddParams) {
//        designMainStakeholderService.batchEdit(designMainStakeholderAddParams);
//        return Result.OK("编辑成功！");
//    }
//
//
//}
