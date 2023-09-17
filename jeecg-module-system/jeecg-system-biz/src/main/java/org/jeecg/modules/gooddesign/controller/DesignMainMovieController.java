package org.jeecg.modules.gooddesign.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.gooddesign.entity.DesignMain;
import org.jeecg.modules.gooddesign.entity.DesignMainMovie;
import org.jeecg.modules.gooddesign.entity.DesignMainMovie;
import org.jeecg.modules.gooddesign.entity.vo.DesignMainMovieVO;
import org.jeecg.modules.gooddesign.service.IDesignMainMovieService;
import org.jeecg.modules.gooddesign.service.IDesignMainMovieService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 设计壮游
 * @Author: jeecg-boot
 * @Date: 2023-08-18
 * @Version: V1.0
 */
@Api(tags = "好设计-设计壮游-现场视频")
@RestController
@RequestMapping("/DesignMainMovie")
@Slf4j
public class DesignMainMovieController extends JeecgController<DesignMainMovie, IDesignMainMovieService> {
    @Autowired
    IDesignMainMovieService designMainMovieService;

    /**
     * 分页列表查询
     *
     * @param designMainMovie
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "设计壮游-分页列表查询")
    @ApiOperation(value = "设计壮游-分页列表查询", notes = "设计壮游-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<DesignMainMovie>> queryPageList(DesignMainMovie designMainMovie,
                                                        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                        HttpServletRequest req) {
        QueryWrapper<DesignMainMovie> queryWrapper = QueryGenerator.initQueryWrapper(designMainMovie, req.getParameterMap());
        Page<DesignMainMovie> page = new Page<DesignMainMovie>(pageNo, pageSize);
        IPage<DesignMainMovie> pageList = designMainMovieService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @return
     */
    @AutoLog(value = "设计壮游-编辑壮游-现场视频-添加")
    @ApiOperation(value = "设计壮游-编辑壮游-现场视频-添加", notes = "设计壮游-编辑壮游-现场视频-添加")
    @PostMapping(value = "/add")
    public Result<String> addMovie(@RequestBody DesignMainMovieVO designMainMovieVO) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();


        DesignMainMovie designMainMovie = new DesignMainMovie();
        BeanUtils.copyProperties(designMainMovieVO, designMainMovie);
        if (sysUser != null) {
            designMainMovie.setCreateBy(sysUser.getUsername());
        }
        designMainMovie.setCreateTime(new Date());
        designMainMovieService.save(designMainMovie);
        return Result.OK("添加成功！");
    }

    @AutoLog(value = "设计壮游-编辑壮游-现场视频-批量添加")
    @ApiOperation(value = "设计壮游-编辑壮游-现场视频-批量添加", notes = "设计壮游-编辑壮游-现场视频-批量添加")
    @PostMapping(value = "/batchAdd")
    public Result<String> addMovie(@RequestBody List<DesignMainMovieVO> designMainMovieVOs) {
        designMainMovieService.batchAdd(designMainMovieVOs);
        return Result.OK("添加成功！");
    }


    /**
     * 编辑
     *
     * @param DesignMainMovies
     * @return
     */
    @AutoLog(value = "设计壮游-编辑")
    @ApiOperation(value = "设计壮游-编辑", notes = "设计壮游-编辑")
    //@RequiresPermissions("gooddesign:design_main_image:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.POST})
    public Result<String> edit(@RequestBody DesignMainMovie designMainMovie) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (sysUser != null) {
            designMainMovie.setUpdateBy(sysUser.getUsername());
        }
        designMainMovie.setUpdateTime(new Date());
        designMainMovieService.updateById(designMainMovie);
        return Result.OK("编辑成功!");
    }


    @AutoLog(value = "设计壮游-批量编辑")
    @ApiOperation(value = "设计壮游-批量编辑", notes = "设计壮游-批量编辑")
    //@RequiresPermissions("gooddesign:design_main_image:edit")
    @RequestMapping(value = "/batchEdit", method = {RequestMethod.POST})
    public Result<String> batchEdit(@RequestBody List<DesignMainMovieVO> designMainMovies) {
        designMainMovieService.batchEdit(designMainMovies);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "设计壮游-通过id删除")
    @ApiOperation(value = "设计壮游-通过id删除", notes = "设计壮游-通过id删除")
    //@RequiresPermissions("gooddesign:design_main_image:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        designMainMovieService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "设计壮游-批量删除")
    @ApiOperation(value = "设计壮游-批量删除", notes = "设计壮游-批量删除")
    //@RequiresPermissions("gooddesign:design_main_image:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.designMainMovieService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "设计壮游-通过id查询")
    @ApiOperation(value = "设计壮游-通过id查询", notes = "设计壮游-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<DesignMainMovie> queryById(@RequestParam(name = "id", required = true) String id) {
        DesignMainMovie DesignMainMovie = designMainMovieService.getById(id);
        if (DesignMainMovie == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(DesignMainMovie);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param DesignMainMovie
     */
    //@RequiresPermissions("gooddesign:design_main_image:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DesignMainMovie DesignMainMovie) {
        return super.exportXls(request, DesignMainMovie, DesignMainMovie.class, "设计壮游");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    //@RequiresPermissions("gooddesign:design_main_image:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, DesignMainMovie.class);
    }

}
