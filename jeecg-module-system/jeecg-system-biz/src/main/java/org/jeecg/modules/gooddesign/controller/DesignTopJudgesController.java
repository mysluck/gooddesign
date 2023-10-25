package org.jeecg.modules.gooddesign.controller;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeecg.weibo.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.gooddesign.entity.DesignActivity;
import org.jeecg.modules.gooddesign.entity.DesignTopJudges;
import org.jeecg.modules.gooddesign.entity.vo.*;
import org.jeecg.modules.gooddesign.service.IDesignActivityService;
import org.jeecg.modules.gooddesign.service.IDesignTopJudgesService;
import org.jeecg.modules.gooddesign.service.IDesignTopProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
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
                                                        @RequestParam(name = "sortFlag", required = false) Integer sortFlag,
                                                        @RequestParam(name = "historyStatus", defaultValue = "0") @ApiParam("0：查询当前top数据(默认) 1：查询历史数据 2：查询所有数据") int historyStatus,
                                                        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                        HttpServletRequest req) {

        List<Integer> activityIds = new ArrayList<>();
        Map<Integer, DesignActivity> designActivityMap = new HashMap<>();
        if (historyStatus == 0) {
            DesignActivity nowActivity = designActivityService.getNowActivity();
            if (nowActivity == null) {
                throw new BusinessException("未获取到发现100状态为未生成的活动，请确认!");
            }
            designActivityMap.put(nowActivity.getId(), nowActivity);
            activityIds.add(nowActivity.getId());
        } else if (historyStatus == 1) {
            List<DesignActivity> activityBy = designActivityService.getActivityBy(null, null, 1);
            if (CollectionUtils.isEmpty(activityBy)) {
                throw new BusinessException("未获取到历史活动，请确认!");
            }
            activityIds.addAll(activityBy.stream().map(DesignActivity::getId).collect(Collectors.toList()));
            activityBy.forEach(data -> {
                designActivityMap.put(data.getId(), data);
            });
        }

        if (designTopJudges != null && StringUtils.isNotEmpty(designTopJudges.getRealName())) {
            designTopJudges.setRealName("*" + designTopJudges.getRealName() + "*");
        }
        QueryWrapper<DesignTopJudges> queryWrapper = QueryGenerator.initQueryWrapper(designTopJudges, req.getParameterMap());
        if (sortFlag != null && 1 == sortFlag) {
            queryWrapper.gt("sort", 0);
        }
        queryWrapper.orderByAsc("sort");
        if (CollectionUtils.isNotEmpty(activityIds)) {
            queryWrapper.in("activity_id", activityIds);
        }
        Page<DesignTopJudges> page = new Page<DesignTopJudges>(pageNo, pageSize);
        IPage<DesignTopJudges> pageList = designTopJudgesService.page(page, queryWrapper);
        List<DesignTopJudges> records = pageList.getRecords();
        if (!records.isEmpty()) {
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
     * 添加
     *
     * @param
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
        if (sysUser != null) {
            bean.setCreateBy(sysUser.getUsername());
        }
        bean.setCreateTime(new Date());
        bean.setDesignNo(getDesignNo());
        designTopJudgesService.save(bean);
        return Result.OK("添加成功！");
    }


    @AutoLog(value = "好设计-发现100-完整添加")
    @ApiOperation(value = "好设计-发现100-完整添加", notes = "好设计-发现100-设计师信息-完整添加设计师基本信息和项目信息")
    @PostMapping(value = "/addDetail")
    public Result<String> addDetail(@RequestBody DesignTopJudgesDetailVO designTopJudgesAllVO) {
        designTopJudgesService.addDetail(designTopJudgesAllVO);
        return Result.OK("添加成功！");
    }


    @AutoLog(value = "好设计-发现100-完整编辑")
    @ApiOperation(value = "好设计-发现100-完整编辑", notes = "好设计-发现100-完整编辑")
    @PostMapping(value = "/editDetail")
    public Result<String> editDetail(@RequestBody DesignTopJudgesDetailVO designTopJudgesAllVO) {
        designTopJudgesService.editDetail(designTopJudgesAllVO);
        return Result.OK("编辑成功！");
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
        if (sysUser != null) {
            bean.setUpdateBy(sysUser.getUsername());
        }
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

    @AutoLog(value = "好设计-发现100-设计师信息-通过id删除top100信息和作品信息")
    @ApiOperation(value = "好设计-发现100-设计师信息-通过id删除top100信息和作品信息", notes = "好设计-发现100-设计师信息-通过id删除top100信息和作品信息")
    //@RequiresPermissions("gooddesign:design_top_judges:delete")
    @DeleteMapping(value = "/deleteBatchDetail")
    public Result<String> deleteBatchDetail(@RequestParam(name = "ids", required = true) String ids) {
        List<Integer> idList = Arrays.asList(ids.split(",")).stream().map(Integer::new).collect(Collectors.toList());
        designTopJudgesService.deleteBatchDetail(idList);
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

    @ApiOperation(value = "好设计-发现100-设计师信息-通过id查询详细信息", notes = "好设计-发现100-设计师信息-通过id查询详细信息")
    @GetMapping(value = "/queryDetailById")
    public Result<DesignTopJudgesDetailVO> queryDetailById(@RequestParam(name = "id", required = true) Integer id) {
        DesignTopJudgesDetailVO designTopJudges = designTopJudgesService.queryDetailById(id);
//        DesignTopJudges designTopJudges = designTopJudgesService.getById(id);

        if (designTopJudges == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(designTopJudges);
    }


    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, DesignTopJudges.class);
    }


    @ApiOperation(value = "好设计-发现100-获取所有活动信息", notes = "好设计-发现100-获取所有活动信息")
    @RequestMapping(value = "/getAdctivity", method = RequestMethod.POST)
    public Result<List<DesignActivityDetailVO>> upgetAdctivity() {
        QueryWrapper<DesignActivity> queryWrapper = new QueryWrapper();
        queryWrapper.eq("top_status", 1);
        List<DesignActivity> list = designActivityService.list(queryWrapper);

        List<DesignActivityDetailVO> result = list.stream().map(activity -> {
            DesignActivityDetailVO designActivityDetailVO = new DesignActivityDetailVO();
            BeanUtils.copyProperties(activity, designActivityDetailVO);
            Date publishTime = activity.getPublishTime();
            try {
                String year = DateUtil.format(publishTime, "yyyy");
                designActivityDetailVO.setPublishYear(year);
            } catch (Exception e) {
                log.error(e.getMessage());
            }
            return designActivityDetailVO;
        }).collect(Collectors.toList());
        result.sort(Comparator.comparing(DesignActivityDetailVO::getPublishYear));
        return Result.OK(result);
    }


    @ApiOperation(value = "好设计-管理员修改序号", notes = "好设计-管理员修改序号")
    @RequestMapping(value = "/updateSort", method = {RequestMethod.POST})
    public Result<String> updateSort(@RequestBody UpdateSortParam updateSortParam) {
        try {
            designTopJudgesService.updateSortByDesignNo(updateSortParam.getDesignNo(), updateSortParam.getSort());
            return Result.OK("编辑成功!");
        } catch (Exception e) {
            return Result.error("推荐编号已存在，请重新输入!");
        }

    }


}
