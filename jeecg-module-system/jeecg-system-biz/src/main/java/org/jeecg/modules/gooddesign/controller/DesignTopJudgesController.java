package org.jeecg.modules.gooddesign.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.gooddesign.entity.DesignActivity;
import org.jeecg.modules.gooddesign.entity.DesignTopJudges;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopJudgesAllVO;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopJudgesScoreVO;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopJudgesVO;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopProductVO;
import org.jeecg.modules.gooddesign.service.IDesignActivityService;
import org.jeecg.modules.gooddesign.service.IDesignTopJudgesService;
import org.jeecg.modules.gooddesign.service.IDesignTopProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description: 好设计-发现100-设计师信息
 * @Author: jeecg-boot
 * @Date: 2023-08-20
 * @Version: V1.0
 */
@Api(tags = "好设计-发现100-设计师信息")
@RestController
@RequestMapping("/designTopJudges")
@Slf4j
public class DesignTopJudgesController extends JeecgController<DesignTopJudges, IDesignTopJudgesService> {
    @Autowired
    private IDesignTopJudgesService designTopJudgesService;
    @Autowired
    IDesignActivityService designActivityService;
    @Autowired
    IDesignTopProductService designTopProductService;

    /**
     * 分页列表查询
     *
     * @param designTopJudges
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "好设计-发现100-设计师信息-分页列表查询")
    @ApiOperation(value = "好设计-发现100-设计师信息-分页列表查询", notes = "好设计-发现100-设计师信息-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<DesignTopJudges>> queryPageList(DesignTopJudges designTopJudges,
                                                        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                        HttpServletRequest req) {
        QueryWrapper<DesignTopJudges> queryWrapper = QueryGenerator.initQueryWrapper(designTopJudges, req.getParameterMap());
        Page<DesignTopJudges> page = new Page<DesignTopJudges>(pageNo, pageSize);
        IPage<DesignTopJudges> pageList = designTopJudgesService.page(page, queryWrapper);
        List<DesignTopJudges> records = pageList.getRecords();
        List<Integer> activityIds = records.stream().map(DesignTopJudges::getActivityId).collect(Collectors.toList());
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
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param designTopJudges
     * @return
     */
    @AutoLog(value = "好设计-发现100-设计师信息-添加")
    @ApiOperation(value = "好设计-发现100-设计师信息-添加", notes = "好设计-发现100-设计师信息-添加")
    //@RequiresPermissions("gooddesign:design_top_judges:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody DesignTopJudgesVO designTopJudgesVO) {
        DesignTopJudges bean = new DesignTopJudges();
        BeanUtils.copyProperties(designTopJudgesVO, bean);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        bean.setCreateBy(sysUser.getUsername());
        bean.setCreateTime(new Date());
        bean.setDesignNo(getDesignNo());
        designTopJudgesService.save(bean);
        return Result.OK("添加成功！");
    }

    @AutoLog(value = "好设计-发现100-设计师信息-完整添加设计师基本信息和项目信息")
    @ApiOperation(value = "好设计-发现100-设计师信息-完整添加设计师基本信息和项目信息", notes = "好设计-发现100-设计师信息-完整添加设计师基本信息和项目信息")
    //@RequiresPermissions("gooddesign:design_top_judges:add")
    @PostMapping(value = "/addDetail")
    public Result<String> addDetail(@RequestBody DesignTopJudgesAllVO designTopJudgesAllVO) {
        designTopJudgesService.addDetail(designTopJudgesAllVO);
        return Result.OK("添加成功！");
    }


    private String getDesignNo() {
        return "FX" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmsss");
    }

    /**
     * 编辑
     *
     * @param designTopJudgesVO
     * @return
     */
    @AutoLog(value = "好设计-发现100-设计师信息-编辑")
    @ApiOperation(value = "好设计-发现100-设计师信息-编辑", notes = "好设计-发现100-设计师信息-编辑")
    //@RequiresPermissions("gooddesign:design_top_judges:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.POST})
    public Result<String> edit(@RequestBody DesignTopJudgesVO designTopJudgesVO) {
        DesignTopJudges bean = new DesignTopJudges();
        BeanUtils.copyProperties(designTopJudgesVO, bean);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        bean.setUpdateBy(sysUser.getUsername());
        bean.setUpdateTime(new Date());

        designTopJudgesService.updateById(bean);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "好设计-发现100-设计师信息-通过id删除")
    @ApiOperation(value = "好设计-发现100-设计师信息-通过id删除", notes = "好设计-发现100-设计师信息-通过id删除")
    //@RequiresPermissions("gooddesign:design_top_judges:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) Integer id) {
        designTopJudgesService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "好设计-发现100-设计师信息-批量删除")
    @ApiOperation(value = "好设计-发现100-设计师信息-批量删除", notes = "好设计-发现100-设计师信息-批量删除")
    //@RequiresPermissions("gooddesign:design_top_judges:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.designTopJudgesService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "好设计-发现100-设计师信息-通过id查询")
    @ApiOperation(value = "好设计-发现100-设计师信息-通过id查询", notes = "好设计-发现100-设计师信息-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<DesignTopJudges> queryById(@RequestParam(name = "id", required = true) Integer id) {
        DesignTopJudges designTopJudges = designTopJudgesService.getById(id);
        if (designTopJudges == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(designTopJudges);
    }



    @ApiOperation(value = "好设计-发现100-top100得分数据", notes = "好设计-发现100-top100得分数据")
    @GetMapping(value = "/queryTop100")
    public Result<List<DesignTopJudgesScoreVO>> queryTop100() {
        List<DesignTopJudgesScoreVO> designTopJudgesScoreVOS = designTopJudgesService.queryByTopJudgesId();

        return Result.OK();
    }



}
