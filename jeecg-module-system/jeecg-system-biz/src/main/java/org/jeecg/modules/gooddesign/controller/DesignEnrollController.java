package org.jeecg.modules.gooddesign.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.gooddesign.entity.DesignActivity;
import org.jeecg.modules.gooddesign.entity.DesignEnrollJudges;
import org.jeecg.modules.gooddesign.entity.DesignEnrollProduct;
import org.jeecg.modules.gooddesign.entity.DesignTopJudges;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopJudgesDetailVO;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopJudgesVO;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopProductVO;
import org.jeecg.modules.gooddesign.service.IDesignActivityService;
import org.jeecg.modules.gooddesign.service.IDesignEnrollJudgesService;
import org.jeecg.modules.gooddesign.service.IDesignEnrollProductService;
import org.jeecg.modules.gooddesign.service.IDesignTopJudgesService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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
    IDesignEnrollJudgesService designEnrollJudgesService;
    @Autowired
    IDesignActivityService designActivityService;
    @Autowired
    IDesignEnrollProductService designEnrollProductService;
    @Autowired
    IDesignTopJudgesService designTopJudgesService;

    @ApiOperation(value = "好设计-发现100-设计师信息-分页列表查询", notes = "好设计-发现100-设计师信息-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<DesignEnrollJudges>> queryPageList(DesignEnrollJudges designEnrollJudges,
                                                           @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                           HttpServletRequest req) {
        QueryWrapper<DesignEnrollJudges> queryWrapper = QueryGenerator.initQueryWrapper(designEnrollJudges, req.getParameterMap());
        Page<DesignEnrollJudges> page = new Page<DesignEnrollJudges>(pageNo, pageSize);
        IPage<DesignEnrollJudges> pageList = designEnrollJudgesService.page(page, queryWrapper);
        List<DesignEnrollJudges> records = pageList.getRecords();
        if (!records.isEmpty()) {
            List<Integer> activityIds = records.stream().map(DesignEnrollJudges::getActivityId).collect(Collectors.toList());
            QueryWrapper<DesignActivity> designActivityWrapper = new QueryWrapper();
            designActivityWrapper.in("id", activityIds);
            List<DesignActivity> list = designActivityService.list(designActivityWrapper);

            Map<Integer, DesignActivity> designActivityMap = list.stream().collect(Collectors.groupingBy(DesignActivity::getId, Collectors.collectingAndThen(Collectors.toList(), value -> value.get(0))));

            records.stream().forEach(designTopJudge -> {
                Integer activityId = designTopJudge.getActivityId();
                if (designActivityMap.containsKey(activityId) && designActivityMap.get(activityId) != null) {
                    DesignActivity designActivity = designActivityMap.get(activityId);
                    designTopJudge.setActivityName(designActivity.getActivityName());
                    designTopJudge.setPublishTime(designActivity.getPublishTime());
                }
            });
            pageList.setRecords(records);
        }

        return Result.OK(pageList);
    }

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
    public Result<String> addDetail(HttpServletRequest request, HttpServletResponse response, @RequestBody DesignTopJudgesDetailVO designTopJudgesAllVO) {
        String token = request.getHeader(CommonConstant.X_ACCESS_TOKEN);
        if (StringUtils.isEmpty(token)) {
            return Result.error("请先登录！");
        }
        String username = JwtUtil.getUsername(token);
        if (StringUtils.isEmpty(username)) {
            return Result.error("请先登录！");
        }
        if (designTopJudgesAllVO.getLoginId() == null) {
            return Result.OK("请输入报名唯一信息！");
        }
        designEnrollProductService.addDetail(designTopJudgesAllVO);
        return Result.OK("添加成功！");
    }


    @ApiOperation(value = "好设计-报名-根据设计师ID查询(项目信息)", notes = "好设计-报名-根据设计师ID查询(项目信息)")
    @GetMapping(value = "/queryDetailByJudgesId")
    public Result<List<DesignTopProductVO>> queryDetailByJudgesId(@RequestParam(name = "id", required = true) Integer id) {

        List<DesignTopProductVO> designTopProduct = designEnrollProductService.queryDetailByJudgesId(id);
        if (designTopProduct == null || designTopProduct.isEmpty()) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(designTopProduct);
    }


    @ApiOperation(value = "好设计-报名-通过id查询详细信息(个人信息+项目信息)", notes = "好设计-报名-通过id查询详细信息(个人信息+项目信息)")
    @GetMapping(value = "/queryDetailById")
    public Result<DesignTopJudgesDetailVO> queryDetailById(@RequestParam(name = "id", required = true) Integer id) {
        DesignTopJudgesDetailVO designTopJudges = designEnrollJudgesService.queryDetailById(id);
//        DesignTopJudges designTopJudges = designTopJudgesService.getById(id);

        if (designTopJudges == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(designTopJudges);
    }

    @AutoLog(value = "好设计-报名-修改报名信息")
    @ApiOperation(value = "好设计-报名-修改报名设计师基本信息", notes = "好设计-报名-修改报名设计师基本信息")
    //@RequiresPermissions("gooddesign:design_top_judges:edit")
    @RequestMapping(value = "/editBasic", method = {RequestMethod.POST})
    public Result<String> edit(@RequestBody DesignTopJudgesVO designTopJudgesVO) {
        DesignEnrollJudges bean = new DesignEnrollJudges();
        BeanUtils.copyProperties(designTopJudgesVO, bean);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (sysUser != null) {
            bean.setUpdateBy(sysUser.getUsername());
        }
        bean.setUpdateTime(new Date());
        designEnrollJudgesService.updateById(bean);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "好设计-报名-通过id删除")
    @ApiOperation(value = "好设计-发现100-设计师信息-通过id删除", notes = "好设计-发现100-设计师信息-通过id删除")
    //@RequiresPermissions("gooddesign:design_top_judges:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) Integer id) {
        designEnrollJudgesService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "好设计-报名-批量删除")
    @ApiOperation(value = "好设计-发现100-设计师信息-批量删除", notes = "好设计-发现100-设计师信息-批量删除")
    //@RequiresPermissions("gooddesign:design_top_judges:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.designEnrollJudgesService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    @AutoLog(value = "好设计-报名-完整编辑设计师基本信息、项目信息和作品")
    @ApiOperation(value = "好设计-报名-完整编辑设计师基本信息、项目信息和作品", notes = "好设计-报名-完整编辑设计师基本信息、项目信息和作品")
    @PostMapping(value = "/editDetail")
    public Result<String> editDetail(@RequestBody DesignTopJudgesDetailVO designTopJudgesAllVO) {
        designEnrollJudgesService.editDetail(designTopJudgesAllVO);
        return Result.OK("添加成功！");
    }

    @AutoLog(value = "好设计-报名-将报名数据添加到top100")
    @ApiOperation(value = "好设计-报名-评委通过后将报名数据添加到top100", notes = "好设计-报名-评委通过后将报名数据添加到top100")
    @GetMapping(value = "/addTop100")
    public Result<String> addTop100(@RequestBody Integer id) {
        DesignTopJudgesDetailVO designTopJudgesDetailVO = designEnrollJudgesService.queryDetailById(id);
        designTopJudgesService.addDetail(designTopJudgesDetailVO);
        return Result.OK("添加成功！");
    }





}
