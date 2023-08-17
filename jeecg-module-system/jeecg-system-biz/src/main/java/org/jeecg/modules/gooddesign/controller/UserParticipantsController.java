package org.jeecg.modules.gooddesign.controller;

import java.util.Arrays;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.gooddesign.entity.UserParticipants;
import org.jeecg.modules.gooddesign.entity.vo.UserParticipantsVO;
import org.jeecg.modules.gooddesign.service.IUserParticipantsService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: 设计师
 * @Author: jeecg-boot
 * @Date: 2023-08-17
 * @Version: V1.0
 */
@Api(tags = "好设计-参赛用户信息")
@RestController
@RequestMapping("/designParticipants")
@Slf4j
public class UserParticipantsController extends JeecgController<UserParticipants, IUserParticipantsService> {
    @Autowired
    private IUserParticipantsService userParticipantsService;

    /**
     * 分页列表查询
     *
     * @param
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    @ApiOperation(value = "设计师-分页列表查询", notes = "设计师-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<UserParticipants>> queryPageList(UserParticipantsVO userParticipantsvo,
                                                         @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                         @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                         HttpServletRequest req) {
        UserParticipants userParticipants = new UserParticipants();
        BeanUtils.copyProperties(userParticipantsvo, userParticipants);
        QueryWrapper<UserParticipants> queryWrapper = QueryGenerator.initQueryWrapper(userParticipants, req.getParameterMap());
        Page<UserParticipants> page = new Page<UserParticipants>(pageNo, pageSize);
        IPage<UserParticipants> pageList = userParticipantsService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param
     * @return
     */
    @AutoLog(value = "设计师-添加")
    @ApiOperation(value = "设计师-添加", notes = "设计师-添加")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody UserParticipantsVO userParticipantsvo) {
        UserParticipants userParticipants = new UserParticipants();
        BeanUtils.copyProperties(userParticipantsvo, userParticipants);
        userParticipantsService.save(userParticipants);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param userParticipantsvo
     * @return
     */
    @AutoLog(value = "设计师-编辑")
    @ApiOperation(value = "设计师-编辑", notes = "设计师-编辑")
    @RequestMapping(value = "/edit", method = {RequestMethod.POST})
    public Result<String> edit(@RequestBody UserParticipantsVO userParticipantsvo) {
        UserParticipants userParticipants = new UserParticipants();
        BeanUtils.copyProperties(userParticipantsvo, userParticipants);
        userParticipantsService.updateById(userParticipants);
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
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        userParticipantsService.removeById(id);
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
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.userParticipantsService.removeByIds(Arrays.asList(ids.split(",")));
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
    public Result<UserParticipants> queryById(@RequestParam(name = "id", required = true) String id) {
        UserParticipants userParticipants = userParticipantsService.getById(id);
        if (userParticipants == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(userParticipants);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param userParticipants
     */
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, UserParticipants userParticipants) {
        return super.exportXls(request, userParticipants, UserParticipants.class, "设计师");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, UserParticipants.class);
    }

}
