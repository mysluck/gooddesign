package org.jeecg.modules.gooddesign.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
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
import org.jeecg.modules.gooddesign.entity.vo.*;
import org.jeecg.modules.gooddesign.service.*;
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
    IDesignTopJudgesParticipantsService designTopJudgesParticipantsService;
    @Autowired
    IDesignTopJudgesService designTopJudgesService;

    @ApiOperation(value = "好设计-报名-设计师信息-分页列表查询", notes = "好设计-报名-设计师信息-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<DesignEnrollJudges>> queryPageList(DesignEnrollJudges designEnrollJudges,
                                                           @RequestParam(name = "historyStatus", defaultValue = "2") @ApiParam("0：查询当前top数据 1：查询历史数据 2：查询所有数据（默认）") int historyStatus,
                                                           @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                           HttpServletRequest req) {
        if (historyStatus == 0) {
            return queryPageList(designEnrollJudges, pageNo, pageSize, req);
        } else if (historyStatus == 1) {
            return listHistory(designEnrollJudges, pageNo, pageSize, req);
        } else {
            return listAll(designEnrollJudges, pageNo, pageSize, req);
        }

    }


    public Result<IPage<DesignEnrollJudges>> queryPageList(DesignEnrollJudges designEnrollJudges, Integer pageNo, Integer pageSize, HttpServletRequest req) {
        DesignActivity nowActivity = designActivityService.getNowActivity();
        if (nowActivity == null) {
            return Result.error("发现100状态已生成，报名结束!");
        }
        if (designEnrollJudges != null && org.apache.commons.lang.StringUtils.isNotEmpty(designEnrollJudges.getRealName())) {
            designEnrollJudges.setRealName("*" + designEnrollJudges.getRealName() + "*");
        }
        designEnrollJudges.setActivityId(nowActivity.getId());
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

    public Result<IPage<DesignEnrollJudges>> listAll(DesignEnrollJudges designEnrollJudges, Integer pageNo, Integer pageSize, HttpServletRequest req) {

        if (designEnrollJudges != null && org.apache.commons.lang.StringUtils.isNotEmpty(designEnrollJudges.getRealName())) {
            designEnrollJudges.setRealName("*" + designEnrollJudges.getRealName() + "*");
        }
        QueryWrapper<DesignEnrollJudges> queryWrapper = QueryGenerator.initQueryWrapper(designEnrollJudges, req.getParameterMap());
        queryWrapper.orderByDesc("create_time");
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


    /**
     * top100状态生成的活动叫历史活动
     *
     * @param designEnrollJudges
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */

    public Result<IPage<DesignEnrollJudges>> listHistory(DesignEnrollJudges designEnrollJudges, Integer pageNo, Integer pageSize, HttpServletRequest req) {
        List<DesignActivity> activityList = designActivityService.getActivityBy(null, null, 1);
        if (CollectionUtils.isEmpty(activityList)) {
            return Result.error("top100状态关闭，无历史报名数据！");
        }
        List<Integer> activityIds = activityList.stream().map(DesignActivity::getId).collect(Collectors.toList());
        if (designEnrollJudges != null && org.apache.commons.lang.StringUtils.isNotEmpty(designEnrollJudges.getRealName())) {
            designEnrollJudges.setRealName("*" + designEnrollJudges.getRealName() + "*");
        }
        QueryWrapper<DesignEnrollJudges> queryWrapper = QueryGenerator.initQueryWrapper(designEnrollJudges, req.getParameterMap());
        queryWrapper.in("activity_id", activityIds);
        Page<DesignEnrollJudges> page = new Page<DesignEnrollJudges>(pageNo, pageSize);
        IPage<DesignEnrollJudges> pageList = designEnrollJudgesService.page(page, queryWrapper);
        List<DesignEnrollJudges> records = pageList.getRecords();
        if (!records.isEmpty()) {
            Map<Integer, DesignActivity> designActivityMap = activityList.stream().collect(Collectors.groupingBy(DesignActivity::getId, Collectors.collectingAndThen(Collectors.toList(), value -> value.get(0))));

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
        designTopJudgesAllVO.setLoginId(username);
        designEnrollJudgesService.addDetail(designTopJudgesAllVO);
        return Result.OK("添加成功！");
    }


    @ApiOperation(value = "好设计-报名-根据设计师ID查询项目信息", notes = "好设计-报名-根据设计师ID查询项目信息")
    @GetMapping(value = "/queryDetailByJudgesId")
    public Result<List<DesignTopProductVO>> queryDetailByJudgesId(@RequestParam(name = "id", required = true) Integer id) {

        List<DesignTopProductVO> designTopProduct = designEnrollProductService.queryDetailByJudgesId(id);
        if (designTopProduct == null || designTopProduct.isEmpty()) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(designTopProduct);
    }


    @ApiOperation(value = "好设计-报名-通过id查询个人信息+项目信息", notes = "好设计-报名-通过id查询个人信息+项目信息")
    @GetMapping(value = "/queryDetailById")
    public Result<DesignTopJudgesDetailVO> queryDetailById(@RequestParam(name = "id", required = true) Integer id) {
        DesignTopJudgesDetailVO designTopJudges = designEnrollJudgesService.queryDetailById(id);
        if (designTopJudges == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(designTopJudges);
    }

    @ApiOperation(value = "好设计-报名-通过报名ID查询所有报名详细信息(个人信息+项目信息)", notes = "好设计-报名-通过报名ID(微信二维码openId或手机号)查询当前用户所有报名详细信息(个人信息+项目信息)")
    @GetMapping(value = "/queryDetailByLoginId")
    public Result<List<DesignTopJudgesDetailVO>> queryDetailByLoginId(@RequestParam(name = "loginId", required = true) String loginId) {
        List<DesignTopJudgesDetailVO> designTopJudgesDetailVOS = designEnrollJudgesService.queryDetailByLoginId(loginId);
        return Result.OK(designTopJudgesDetailVOS);
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


    @AutoLog(value = "好设计-管理员初筛-批量修改通过状态")
    @ApiOperation(value = "好设计-管理员初筛-批量修改通过状态", notes = "好设计-管理员初筛-批量修改通过状态")
    //@RequiresPermissions("gooddesign:design_top_judges:edit")
    @RequestMapping(value = "/batchEditScreenStatus", method = {RequestMethod.POST})
    public Result<String> batchEditScreenStatus(@RequestBody ScreeStatusEditParam screeStatusEditParam) {

        if (CollectionUtils.isEmpty(screeStatusEditParam.getEnrollIds())) {
            return Result.error("请输入设计师ID!");
        }
        designEnrollJudgesService.batchEditScreenStatus(screeStatusEditParam.getEnrollIds(), screeStatusEditParam.getScreenStatus());
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
        return Result.OK("编辑成功！");
    }

    @AutoLog(value = "好设计-报名-将报名数据添加到top100")
    @ApiOperation(value = "好设计-报名-推荐发现100", notes = "好设计-报名-推荐发现100")
    @GetMapping(value = "/addTop100")
    public Result<String> addTop100(@RequestParam @ApiParam("参赛设计师ID") Integer id) {

        designEnrollJudgesService.addTop100(id, 1);
        return Result.OK("添加成功！");
    }

    @AutoLog(value = "好设计-报名-将报名数据添加到top100")
    @ApiOperation(value = "好设计-报名-批量推荐发现100", notes = "好设计-报名-批量推荐发现100")
    @GetMapping(value = "/batchAddTop100")
    public Result<String> batchAddTop100(@RequestParam @ApiParam("参赛设计师ID集合") List<Integer> ids,
                                         @RequestParam @ApiParam("管理员推荐到top100标志 0不推荐  1推荐  2未处理") int topRecommendStatus) {
        if (topRecommendStatus == 1) {
            designEnrollJudgesService.batchAddTop100(ids);
            return Result.OK("添加成功！");
        } else {
            designEnrollJudgesService.batchRemoveFromTop100(ids);
            return Result.OK("修改成功！");

        }
    }


    @ApiOperation(value = "好设计-报名-根据评委打分数据获取前100名", notes = "好设计-报名-根据评委打分数据获取前100名")
    @GetMapping(value = "/queryTop100")
    public Result<List<DesignTopJudgesScoreVO>> queryTop100() {
        List<DesignTopJudgesScoreVO> designTopJudgesScoreVOS = designEnrollJudgesService.queryByTopJudgesId();
        return Result.OK(designTopJudgesScoreVOS);
    }

    @ApiOperation(value = "好设计-报名-管理员查看报名数据", notes = "好设计-报名-管理员查看报名数据")
    @GetMapping(value = "/pageByNameAndScoreStatus")
    public Result<Page<DesignTopJudgesScoreVO>> pageByNameAndTopStatus(@RequestParam(value = "realName", required = false) @ApiParam("设计师姓名") String realName,
                                                                       @RequestParam(value = "topRecommendStatus", required = false) @ApiParam("管理员推荐到top100标志 0不推荐 1推荐 2未处理") Integer topRecommendStatus,
                                                                       @RequestParam(value = "sortStatus", required = false) @ApiParam("根据总分排序 1正叙 2倒叙，默认不排序") Integer sortStatus,
                                                                       @RequestParam(value = "historyStatus", defaultValue = "0") @ApiParam("0：查询当前活动报名数据 1：查询历史数据") int historyStatus,
                                                                       @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                       @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                                       HttpServletRequest req) {

        Page<DesignTopJudgesScoreVO> page = new Page<DesignTopJudgesScoreVO>(pageNo, pageSize);
        return Result.OK(designEnrollJudgesService.pageByNameAndTopStatus(page, realName, topRecommendStatus, sortStatus, historyStatus));
    }


    @ApiOperation(value = "好设计-报名-判断是否报名", notes = "好设计-报名-判断是否报名")
    @GetMapping(value = "/queryEnrollData")
    public Result<LoginVO> queryEnroll(HttpServletRequest request, HttpServletResponse response, @RequestParam String loginId) {
        List<DesignEnrollJudges> byLoginId = designEnrollJudgesService.getByLoginId(loginId);
        String token = request.getHeader(CommonConstant.X_ACCESS_TOKEN);
        LoginVO loginVO = new LoginVO();
        loginVO.setLoginId(loginId);
        loginVO.setToken(token);
        if (!CollectionUtils.isEmpty(byLoginId)) {
            loginVO.setEnroll(true);
        } else {
            loginVO.setEnroll(false);
        }
        return Result.OK(loginVO);
    }

    @AutoLog(value = "好设计-报名-管理员删除")
    @ApiOperation(value = "好设计-报名-管理员删除", notes = "好设计-报名-管理员删除")
    //@RequiresPermissions("gooddesign:design_top_judges:delete")
    @RequestMapping(value = "/editManagerDeleteStatus", method = RequestMethod.DELETE)
    public Result<String> editManagerDeleteStatus(@RequestParam(name = "id", required = true) @ApiParam("主键ID") Integer id,
                                                  @RequestParam(name = "status", required = true) @ApiParam("管理员删除状态 1删除 0不删除") Integer status) {
        DesignEnrollJudges enrollJudges = new DesignEnrollJudges();
        enrollJudges.setId(id);
        enrollJudges.setManagerDelStatus(status);
        designEnrollJudgesService.updateById(enrollJudges);
        return Result.OK("删除成功!");
    }

    @AutoLog(value = "好设计-报名-管理员批量删除")
    @ApiOperation(value = "好设计-报名-管理员批量删除", notes = "好设计-报名-管理员批量删除")
    //@RequiresPermissions("gooddesign:design_top_judges:delete")
    @GetMapping(value = "/batchEditManagerDeleteStatus")
    public Result<String> batchEditManagerDeleteStatus(@RequestParam(name = "ids", required = true) @ApiParam("主键ID集合") List<Integer> ids,
                                                       @RequestParam(name = "status", required = true) @ApiParam("管理员删除状态 1删除 0不删除") Integer status) {
        if (CollectionUtils.isNotEmpty(ids)) {
            ids.forEach(id -> {
                DesignEnrollJudges enrollJudges = new DesignEnrollJudges();
                enrollJudges.setId(id);
                enrollJudges.setManagerDelStatus(status);
                designEnrollJudgesService.updateById(enrollJudges);
            });
        }
        return Result.OK("删除成功!");
    }


    @AutoLog(value = "好设计-报名-查看评委打分记录")
    @ApiOperation(value = "好设计-报名-查看评委打分记录", notes = "好设计-报名-查看评委打分记录")
    //@RequiresPermissions("gooddesign:design_top_judges:delete")
    @GetMapping(value = "/queryScoreHistory")
    public Result<List<JudgesScoreVO>> queryScoreHistory(@RequestParam(name = "id", required = true) @ApiParam("参赛者ID") int id) {
        List<JudgesScoreVO> result = designEnrollJudgesService.queryScoreHistory(id);
        result.forEach(data -> {
            if (1 == data.getScoreStatus()) {
                data.setScore(data.getWeight());
            } else {
                data.setScore(0L);
            }
        });
        return Result.OK(result);
    }


}
