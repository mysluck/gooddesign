package org.jeecg.modules.gooddesign.controller;

import java.util.Arrays;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.gooddesign.entity.UserDesigner;
import org.jeecg.modules.gooddesign.entity.vo.UserDesignerDTO;
import org.jeecg.modules.gooddesign.service.IUserDesignerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

/**
 * @Description: 设计师
 * @Author: jeecg-boot
 * @Date: 2023-08-17
 * @Version: V1.0
 */
@Api(tags = "好设计-设计师（创始人、评委）")
@RestController
@RequestMapping("/userDesigner")
@Slf4j
public class UserDesignerController extends JeecgController<UserDesigner, IUserDesignerService> {
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
    public Result<IPage<UserDesigner>> queryPageList(UserDesigner userDesigner,
                                                     @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                     @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                     HttpServletRequest req) {
        QueryWrapper<UserDesigner> queryWrapper = QueryGenerator.initQueryWrapper(userDesigner, req.getParameterMap());
        Page<UserDesigner> page = new Page<UserDesigner>(pageNo, pageSize);
        IPage<UserDesigner> pageList = userDesignerService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param userDesigner
     * @return
     */
    @AutoLog(value = "设计师-添加")
    @ApiOperation(value = "设计师-添加", notes = "设计师-添加")
    @RequiresPermissions("user_designer:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody UserDesignerDTO userDesignerDTO) {
        UserDesigner userDesigner = new UserDesigner();
        BeanUtils.copyProperties(userDesignerDTO, userDesigner);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        userDesigner.setCreateBy(sysUser.getUsername());
        userDesigner.setCreateTime(new Date());
        userDesignerService.save(userDesigner);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param userDesigner
     * @return
     */
    @AutoLog(value = "设计师-编辑")
    @ApiOperation(value = "设计师-编辑", notes = "设计师-编辑")
    @RequiresPermissions("user_designer:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody UserDesigner userDesigner) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();

        userDesigner.setUpdateBy(sysUser.getUsername());
        userDesigner.setUpdateTime(new Date());
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
    @RequiresPermissions("user_designer:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
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
    @RequiresPermissions("user_designer:deleteBatch")
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
    public Result<UserDesigner> queryById(@RequestParam(name = "id", required = true) String id) {
        UserDesigner userDesigner = userDesignerService.getById(id);
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
    @RequiresPermissions("user_designer:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, UserDesigner userDesigner) {
        return super.exportXls(request, userDesigner, UserDesigner.class, "设计师");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequiresPermissions("user_designer:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, UserDesigner.class);
    }

}
