package org.jeecg.modules.gooddesign.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.gooddesign.entity.DesignJudges;
import org.jeecg.modules.gooddesign.entity.vo.DesignJudgesDTO;
import org.jeecg.modules.gooddesign.service.IUserDesignerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Date;

/**
 * @Description: 设计师
 * @Author: jeecg-boot
 * @Date: 2023-08-17
 * @Version: V1.0
 */
@Api(tags = "好设计-发现设计")
@RestController
@RequestMapping("/designerJudges")
@Slf4j
public class DesignerJudgesController extends JeecgController<DesignJudges, IUserDesignerService> {
    @Autowired
    private IUserDesignerService userDesignerService;

    /**
     * 分页列表查询
     *
     * @param userDesigner
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "设计师-分页列表查询")
    @ApiOperation(value = "设计师-分页列表查询", notes = "设计师-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<DesignJudges>> queryPageList(DesignJudges userDesigner,
                                                     @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                     HttpServletRequest req) {
        if (userDesigner.getRealName() != null) {
            userDesigner.setRealName("*" + userDesigner.getRealName() + "*");
        }
        QueryWrapper<DesignJudges> queryWrapper = QueryGenerator.initQueryWrapper(userDesigner, req.getParameterMap());
        queryWrapper.orderByAsc("sort");
        Page<DesignJudges> page = new Page<DesignJudges>(pageNo, pageSize);
        IPage<DesignJudges> pageList = userDesignerService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param
     * @return
     */
    @AutoLog(value = "发现设计-添加设计师")
    @ApiOperation(value = "发现设计-添加设计师", notes = "发现设计-添加设计师")
    //@RequiresPermissions("user_designer:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody DesignJudgesDTO userDesignerDTO) {

        if (userDesignerService.checkSortNoExist(userDesignerDTO.getSort())) {
            return Result.error("编码存在，请修改编码！");

        }
        DesignJudges userDesigner = new DesignJudges();
        BeanUtils.copyProperties(userDesignerDTO, userDesigner);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (sysUser != null) {
            userDesigner.setCreateBy(sysUser.getUsername());
            userDesigner.setCreateTime(new Date());
        }
        userDesignerService.save(userDesigner);
        return Result.OK("添加成功！");
    }

    @AutoLog(value = "发现设计-设计师编码检查")
    @ApiOperation(value = "发现设计-设计师编码检查", notes = "发现设计-设计师编码检查,true:编码存在，false：编码不存在")
    @GetMapping(value = "/checkSortNoExist")
    public Result<Boolean> checkSortNoExist(@Param("sort") Integer sort) {
        return Result.OK(userDesignerService.checkSortNoExist(sort));
    }

    /**
     * 编辑
     *
     * @param userDesigner
     * @return
     */
    @AutoLog(value = "设计师-编辑")
    @ApiOperation(value = "设计师-编辑", notes = "设计师-编辑")
    //@RequiresPermissions("user_designer:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.POST})
    public Result<String> edit(@RequestBody DesignJudges userDesigner) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (null == userDesigner.getId()) {
            return Result.error(500, "请传入ID!");
        }
        if (sysUser != null) {
            userDesigner.setUpdateBy(sysUser.getUsername());
            userDesigner.setUpdateTime(new Date());
        }

        userDesignerService.updateById(userDesigner);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "设计师-通过id删除")
    @ApiOperation(value = "设计师-通过id删除", notes = "设计师-通过id删除")
    //@RequiresPermissions("user_designer:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) Integer id) {
        userDesignerService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "设计师-批量删除")
    @ApiOperation(value = "设计师-批量删除", notes = "设计师-批量删除")
    //@RequiresPermissions("user_designer:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.userDesignerService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "设计师-通过id查询")
    @ApiOperation(value = "设计师-通过id查询", notes = "设计师-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<DesignJudges> queryById(@RequestParam(name = "id", required = true) String id) {
        DesignJudges userDesigner = userDesignerService.getById(id);
        if (userDesigner == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(userDesigner);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param userDesigner
     */
    //@RequiresPermissions("user_designer:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DesignJudges userDesigner) {
        return super.exportXls(request, userDesigner, DesignJudges.class, "设计师");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    //@RequiresPermissions("user_designer:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, DesignJudges.class);
    }

}
