package org.jeecg.modules.gooddesign.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.gooddesign.entity.DesignNews;
import org.jeecg.modules.gooddesign.entity.vo.DesignNewsVO;
import org.jeecg.modules.gooddesign.service.IDesignNewsService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;

/**
 * @Description: 咨询中心
 * @Author: jeecg-boot
 * @Date: 2023-08-18
 * @Version: V1.0
 */
@Api(tags = "好设计-咨询中心")
@RestController
@RequestMapping("/designNews")
@Slf4j
public class DesignNewsController extends JeecgController<DesignNews, IDesignNewsService> {
    @Autowired
    private IDesignNewsService designNewsService;

    /**
     * 分页列表查询
     *
     * @param designNews
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "咨询中心-分页列表查询")
    @ApiOperation(value = "咨询中心-分页列表查询", notes = "咨询中心-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<DesignNews>> queryPageList(DesignNews designNews,
                                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                   HttpServletRequest req) {
        QueryWrapper<DesignNews> queryWrapper = QueryGenerator.initQueryWrapper(designNews, req.getParameterMap());
        Page<DesignNews> page = new Page<DesignNews>(pageNo, pageSize);
        IPage<DesignNews> pageList = designNewsService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param designNewsVO
     * @return
     */
    @AutoLog(value = "咨询中心-添加")
    @ApiOperation(value = "咨询中心-添加", notes = "咨询中心-添加")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody DesignNewsVO designNewsVO) {

        DesignNews designNews = new DesignNews();
        BeanUtils.copyProperties(designNewsVO, designNews);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if(sysUser!=null) {
            designNews.setCreateBy(sysUser.getUsername());
        }
        designNews.setCreateTime(new Date());
        designNewsService.save(designNews);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param designNews
     * @return
     */
    @AutoLog(value = "咨询中心-编辑")
    @ApiOperation(value = "咨询中心-编辑", notes = "咨询中心-编辑")
    @RequestMapping(value = "/edit", method = {RequestMethod.POST})
    public Result<String> edit(@RequestBody DesignNewsVO designNewsVO) {
        DesignNews designNews = new DesignNews();
        BeanUtils.copyProperties(designNewsVO, designNews);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if(sysUser!=null) {
            designNews.setUpdateBy(sysUser.getUsername());
        }
        designNews.setUpdateTime(new Date());
        designNewsService.updateById(designNews);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "咨询中心-通过id删除")
    @ApiOperation(value = "咨询中心-通过id删除", notes = "咨询中心-通过id删除")
    //@RequiresPermissions("gooddesign:design_news:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) Integer id) {
        designNewsService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "咨询中心-批量删除")
    @ApiOperation(value = "咨询中心-批量删除", notes = "咨询中心-批量删除")
    //@RequiresPermissions("gooddesign:design_news:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.designNewsService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "咨询中心-通过id查询")
    @ApiOperation(value = "咨询中心-通过id查询", notes = "咨询中心-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<DesignNews> queryById(@RequestParam(name = "id", required = true) String id) {
        DesignNews designNews = designNewsService.getById(id);
        if (designNews == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(designNews);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param designNews
     */
    //@RequiresPermissions("gooddesign:design_news:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DesignNews designNews) {
        return super.exportXls(request, designNews, DesignNews.class, "咨询中心");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    //@RequiresPermissions("gooddesign:design_news:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, DesignNews.class);
    }

}
