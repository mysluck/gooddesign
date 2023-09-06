package org.jeecg.modules.gooddesign.controller;

import java.util.Arrays;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.gooddesign.entity.DesignTopJudgesParticipants;
import org.jeecg.modules.gooddesign.entity.vo.DesignJudgesParticipantsVO;
import org.jeecg.modules.gooddesign.service.IDesignTopJudgesParticipantsService;

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
 * @Description: 评委通过表，保存评委评分数据
 * @Author: jeecg-boot
 * @Date: 2023-08-17
 * @Version: V1.0
 */
@Api(tags = "好设计-top100-评委打分")
@RestController
@RequestMapping("/designJudgesParticipants")
@Slf4j
public class DesignTopJudgesParticipantsController extends JeecgController<DesignTopJudgesParticipants, IDesignTopJudgesParticipantsService> {
    @Autowired
    private IDesignTopJudgesParticipantsService designJudgesParticipantsService;

    /**
     * 分页列表查询
     *
     * @param designJudgesParticipants
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "评委通过表，保存评委评分数据-分页列表查询")
    @ApiOperation(value = "评委打分-分页列表查询", notes = "评委打分-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<DesignTopJudgesParticipants>> queryPageList(DesignTopJudgesParticipants designJudgesParticipants,
                                                                    @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                    @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                                    HttpServletRequest req) {
        QueryWrapper<DesignTopJudgesParticipants> queryWrapper = QueryGenerator.initQueryWrapper(designJudgesParticipants, req.getParameterMap());
        Page<DesignTopJudgesParticipants> page = new Page<DesignTopJudgesParticipants>(pageNo, pageSize);
        IPage<DesignTopJudgesParticipants> pageList = designJudgesParticipantsService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param designJudgesParticipants
     * @return
     */
    @AutoLog(value = "评委通过表，保存评委评分数据-添加")
    @ApiOperation(value = "评委通过表，保存评委评分数据-添加", notes = "评委通过表，保存评委评分数据-添加")
    //@RequiresPermissions("gooddesign:design_judges_participants:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody DesignJudgesParticipantsVO designJudgesParticipantsVO) {
        DesignTopJudgesParticipants designJudgesParticipants = new DesignTopJudgesParticipants();
        BeanUtils.copyProperties(designJudgesParticipantsVO, designJudgesParticipants);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if(sysUser!=null) {
            designJudgesParticipants.setCreateBy(sysUser.getUsername());
        }
        designJudgesParticipants.setCreateTime(new Date());

        designJudgesParticipantsService.save(designJudgesParticipants);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param designJudgesParticipants
     * @return
     */
    @AutoLog(value = "评委通过表，保存评委评分数据-编辑")
    @ApiOperation(value = "评委通过表，保存评委评分数据-编辑", notes = "评委通过表，保存评委评分数据-编辑")
    //@RequiresPermissions("gooddesign:design_judges_participants:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.POST})
    public Result<String> edit(@RequestBody DesignJudgesParticipantsVO designJudgesParticipantsVO) {

        DesignTopJudgesParticipants designJudgesParticipants = new DesignTopJudgesParticipants();
        BeanUtils.copyProperties(designJudgesParticipantsVO, designJudgesParticipants);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if(sysUser!=null) {
            designJudgesParticipants.setUpdateBy(sysUser.getUsername());
        }
        designJudgesParticipants.setUpdateTime(new Date());

        designJudgesParticipantsService.updateById(designJudgesParticipants);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "评委通过表，保存评委评分数据-通过id删除")
    @ApiOperation(value = "评委通过表，保存评委评分数据-通过id删除", notes = "评委通过表，保存评委评分数据-通过id删除")
    //@RequiresPermissions("gooddesign:design_judges_participants:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        designJudgesParticipantsService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "评委通过表，保存评委评分数据-批量删除")
    @ApiOperation(value = "评委通过表，保存评委评分数据-批量删除", notes = "评委通过表，保存评委评分数据-批量删除")
    //@RequiresPermissions("gooddesign:design_judges_participants:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.designJudgesParticipantsService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "评委通过表，保存评委评分数据-通过id查询")
    @ApiOperation(value = "评委通过表，保存评委评分数据-通过id查询", notes = "评委通过表，保存评委评分数据-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<DesignJudgesParticipantsVO> queryById(@RequestParam(name = "id", required = true) String id) {
        DesignTopJudgesParticipants designJudgesParticipants = designJudgesParticipantsService.getById(id);
        if (designJudgesParticipants == null) {
            return Result.error("未找到对应数据");
        }
        DesignJudgesParticipantsVO designJudgesParticipantsVO = new DesignJudgesParticipantsVO();
        BeanUtils.copyProperties(designJudgesParticipants, designJudgesParticipantsVO);
        return Result.OK(designJudgesParticipantsVO);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param designJudgesParticipants
     */
    //@RequiresPermissions("gooddesign:design_judges_participants:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DesignTopJudgesParticipants designJudgesParticipants) {
        return super.exportXls(request, designJudgesParticipants, DesignTopJudgesParticipants.class, "评委通过表，保存评委评分数据");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    //@RequiresPermissions("gooddesign:design_judges_participants:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, DesignTopJudgesParticipants.class);
    }

}
