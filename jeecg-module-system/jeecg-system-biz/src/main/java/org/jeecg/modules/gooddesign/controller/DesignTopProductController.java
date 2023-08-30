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
import org.jeecg.modules.gooddesign.entity.DesignTopProduct;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopProductVO;
import org.jeecg.modules.gooddesign.service.IDesignTopProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

/**
 * @Description: 好设计-发现100-项目信息
 * @Author: jeecg-boot
 * @Date: 2023-08-20
 * @Version: V1.0
 */
@Api(tags = "好设计-发现100-项目信息")
@RestController
@RequestMapping("/designTopProduct")
@Slf4j
public class DesignTopProductController extends JeecgController<DesignTopProduct, IDesignTopProductService> {
    @Autowired
    private IDesignTopProductService designTopProductService;

    /**
     * 分页列表查询
     *
     * @param designTopProduct
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "好设计-发现100-项目信息-分页列表查询")
    @ApiOperation(value = "好设计-发现100-项目信息-分页列表查询", notes = "好设计-发现100-项目信息-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<DesignTopProduct>> queryPageList(DesignTopProduct designTopProduct,
                                                         @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                         @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                         HttpServletRequest req) {
        QueryWrapper<DesignTopProduct> queryWrapper = QueryGenerator.initQueryWrapper(designTopProduct, req.getParameterMap());
        Page<DesignTopProduct> page = new Page<DesignTopProduct>(pageNo, pageSize);
        IPage<DesignTopProduct> pageList = designTopProductService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param designTopProduct
     * @return
     */
    @AutoLog(value = "好设计-发现100-项目信息-添加")
    @ApiOperation(value = "好设计-发现100-项目信息-添加", notes = "好设计-发现100-项目信息-添加")
    //@RequiresPermissions("gooddesign:design_top_product:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody DesignTopProductVO designTopProductVO) {
        DesignTopProduct bean = new DesignTopProduct();
        BeanUtils.copyProperties(designTopProductVO, bean);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        bean.setCreateBy(sysUser.getUsername());
        bean.setCreateTime(new Date());
        designTopProductService.save(bean);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param designTopProduct
     * @return
     */
    @AutoLog(value = "好设计-发现100-项目信息-编辑")
    @ApiOperation(value = "好设计-发现100-项目信息-编辑", notes = "好设计-发现100-项目信息-编辑")
    //@RequiresPermissions("gooddesign:design_top_product:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.POST})
    public Result<String> edit(@RequestBody DesignTopProductVO designTopProductVO) {
        DesignTopProduct bean = new DesignTopProduct();
        BeanUtils.copyProperties(designTopProductVO, bean);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        bean.setCreateBy(sysUser.getUsername());
        bean.setCreateTime(new Date());
        designTopProductService.updateById(bean);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "好设计-发现100-项目信息-通过id删除")
    @ApiOperation(value = "好设计-发现100-项目信息-通过id删除", notes = "好设计-发现100-项目信息-通过id删除")
    //@RequiresPermissions("gooddesign:design_top_product:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) Integer id) {
        designTopProductService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "好设计-发现100-项目信息-批量删除")
    @ApiOperation(value = "好设计-发现100-项目信息-批量删除", notes = "好设计-发现100-项目信息-批量删除")
    //@RequiresPermissions("gooddesign:design_top_product:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.designTopProductService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "好设计-发现100-项目信息-通过id查询")
    @ApiOperation(value = "好设计-发现100-项目信息-通过id查询", notes = "好设计-发现100-项目信息-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<DesignTopProduct> queryById(@RequestParam(name = "id", required = true) Integer id) {
        DesignTopProduct designTopProduct = designTopProductService.getById(id);
        if (designTopProduct == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(designTopProduct);
    }

}
