package org.jeecg.modules.gooddesign.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.gooddesign.entity.DesignMainImage;
import org.jeecg.modules.gooddesign.service.IDesignMainImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @Description: 现场照片
 * @Author: jeecg-boot
 * @Date: 2023-08-18
 * @Version: V1.0
 */
@Api(tags = "好设计-设计壮游-现场照片")
@RestController
@RequestMapping("/designMainImage")
@Slf4j
public class DesignMainImageController extends JeecgController<DesignMainImage, IDesignMainImageService> {
    @Autowired
    private IDesignMainImageService designMainImageService;

    /**
     * 分页列表查询
     *
     * @param designMainImage
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "现场照片-分页列表查询")
    @ApiOperation(value = "现场照片-分页列表查询", notes = "现场照片-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<DesignMainImage>> queryPageList(DesignMainImage designMainImage,
                                                        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                        HttpServletRequest req) {
        QueryWrapper<DesignMainImage> queryWrapper = QueryGenerator.initQueryWrapper(designMainImage, req.getParameterMap());
        Page<DesignMainImage> page = new Page<DesignMainImage>(pageNo, pageSize);
        IPage<DesignMainImage> pageList = designMainImageService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param designMainImage
     * @return
     */
    @AutoLog(value = "现场照片-添加")
    @ApiOperation(value = "现场照片-添加", notes = "现场照片-添加")
    //@RequiresPermissions("gooddesign:design_main_image:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody DesignMainImage designMainImage) {
        designMainImageService.save(designMainImage);
        return Result.OK("添加成功！");
    }

    @AutoLog(value = "现场照片-批量添加")
    @ApiOperation(value = "现场照片-批量添加", notes = "现场照片-批量添加")
    //@RequiresPermissions("gooddesign:design_main_image:add")
    @PostMapping(value = "/batchAdd")
    public Result<String> batchAdd(@RequestBody List<DesignMainImage> designMainImages) {
        designMainImageService.saveBatch(designMainImages);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param designMainImage
     * @return
     */
    @AutoLog(value = "现场照片-编辑")
    @ApiOperation(value = "现场照片-编辑", notes = "现场照片-编辑")
    //@RequiresPermissions("gooddesign:design_main_image:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.POST})
    public Result<String> edit(@RequestBody DesignMainImage designMainImage) {
        designMainImageService.updateById(designMainImage);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "现场照片-通过id删除")
    @ApiOperation(value = "现场照片-通过id删除", notes = "现场照片-通过id删除")
    //@RequiresPermissions("gooddesign:design_main_image:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        designMainImageService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "现场照片-批量删除")
    @ApiOperation(value = "现场照片-批量删除", notes = "现场照片-批量删除")
    //@RequiresPermissions("gooddesign:design_main_image:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.designMainImageService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "现场照片-通过id查询")
    @ApiOperation(value = "现场照片-通过id查询", notes = "现场照片-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<DesignMainImage> queryById(@RequestParam(name = "id", required = true) String id) {
        DesignMainImage designMainImage = designMainImageService.getById(id);
        if (designMainImage == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(designMainImage);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param designMainImage
     */
    //@RequiresPermissions("gooddesign:design_main_image:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DesignMainImage designMainImage) {
        return super.exportXls(request, designMainImage, DesignMainImage.class, "现场照片");
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
        return super.importExcel(request, response, DesignMainImage.class);
    }

}
