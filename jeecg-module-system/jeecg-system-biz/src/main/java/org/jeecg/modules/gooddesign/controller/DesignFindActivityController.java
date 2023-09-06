package org.jeecg.modules.gooddesign.controller;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.gooddesign.entity.DesignFindActivity;
import org.jeecg.modules.gooddesign.entity.DesignJudges;
import org.jeecg.modules.gooddesign.entity.vo.DesignFindActivityVO;
import org.jeecg.modules.gooddesign.service.IDesignFindActivityService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * @Description: 好设计-设计壮游-活动
 * @Author: jeecg-boot
 * @Date: 2023-08-24
 * @Version: V1.0
 */
@Api(tags = "好设计-设计壮游-首页活动")
@RestController
@RequestMapping("/designFindActivity")
@Slf4j
public class DesignFindActivityController extends JeecgController<DesignFindActivity, IDesignFindActivityService> {
    @Autowired
    private IDesignFindActivityService designFindActivityService;

    /**
     * 分页列表查询
     *
     * @param designFindActivity
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "好设计-设计壮游-活动-分页列表查询")
    @ApiOperation(value = "好设计-设计壮游-活动-分页列表查询", notes = "好设计-设计壮游-活动-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<DesignFindActivity>> queryPageList(DesignFindActivity designFindActivity,
                                                           @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                           @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                           HttpServletRequest req) {
        QueryWrapper<DesignFindActivity> queryWrapper = QueryGenerator.initQueryWrapper(designFindActivity, req.getParameterMap());
        Page<DesignFindActivity> page = new Page<DesignFindActivity>(pageNo, pageSize);
        IPage<DesignFindActivity> pageList = designFindActivityService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param designFindActivity
     * @return
     */
    @AutoLog(value = "好设计-设计壮游-活动-添加")
    @ApiOperation(value = "好设计-设计壮游-活动-添加", notes = "好设计-设计壮游-活动-添加")
    //@RequiresPermissions("gooddesign:design_find_activity:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody  DesignFindActivityVO designFindActivityVO) {
        DesignFindActivity designFindActivity = new DesignFindActivity();
        BeanUtils.copyProperties(designFindActivityVO, designFindActivity);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if(sysUser!=null) {
            designFindActivity.setCreateBy(sysUser.getUsername());
        }
        designFindActivity.setCreateTime(new Date());
        designFindActivityService.updateById(designFindActivity);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param designFindActivity
     * @return
     */
    @AutoLog(value = "好设计-设计壮游-活动-编辑")
    @ApiOperation(value = "好设计-设计壮游-活动-编辑", notes = "好设计-设计壮游-活动-编辑")
    //@RequiresPermissions("gooddesign:design_find_activity:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.POST})
    public Result<String> edit(@RequestBody DesignFindActivityVO designFindActivityVO) {
        DesignFindActivity designFindActivity = new DesignFindActivity();
        BeanUtils.copyProperties(designFindActivityVO, designFindActivity);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if(sysUser!=null) {
            designFindActivity.setUpdateBy(sysUser.getUsername());
        }
        designFindActivity.setUpdateTime(new Date());
        designFindActivityService.updateById(designFindActivity);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "好设计-设计壮游-活动-通过id删除")
    @ApiOperation(value = "好设计-设计壮游-活动-通过id删除", notes = "好设计-设计壮游-活动-通过id删除")
    //@RequiresPermissions("gooddesign:design_find_activity:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        designFindActivityService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "好设计-设计壮游-活动-批量删除")
    @ApiOperation(value = "好设计-设计壮游-活动-批量删除", notes = "好设计-设计壮游-活动-批量删除")
    //@RequiresPermissions("gooddesign:design_find_activity:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.designFindActivityService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "好设计-设计壮游-活动-通过id查询")
    @ApiOperation(value = "好设计-设计壮游-活动-通过id查询", notes = "好设计-设计壮游-活动-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<DesignFindActivity> queryById(@RequestParam(name = "id", required = true) String id) {
        DesignFindActivity designFindActivity = designFindActivityService.getById(id);
        if (designFindActivity == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(designFindActivity);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param designFindActivity
     */
    //@RequiresPermissions("gooddesign:design_find_activity:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DesignFindActivity designFindActivity) {
        return super.exportXls(request, designFindActivity, DesignFindActivity.class, "好设计-设计壮游-活动");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    //@RequiresPermissions("gooddesign:design_find_activity:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, DesignFindActivity.class);
    }

}
