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
import org.jeecg.modules.gooddesign.entity.DesignMain;
import org.jeecg.modules.gooddesign.entity.DesignMainMovie;
import org.jeecg.modules.gooddesign.entity.DesignMainStakeholder;
import org.jeecg.modules.gooddesign.entity.vo.DesignMainBasicVO;
import org.jeecg.modules.gooddesign.entity.vo.DesignMainMovieVO;
import org.jeecg.modules.gooddesign.entity.vo.DesignStakeholderMainAddVO;
import org.jeecg.modules.gooddesign.service.IDesignMainMovieService;
import org.jeecg.modules.gooddesign.service.IDesignMainService;
import org.jeecg.modules.gooddesign.service.IDesignMainStakeholderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
@Api(tags = "好设计-设计壮游")
@RestController
@RequestMapping("/designMain")
@Slf4j
public class DesignMainController extends JeecgController<DesignMain, IDesignMainService> {
    @Autowired
    IDesignMainStakeholderService designMainStakeholderService;
    @Autowired
    private IDesignMainService designMainService;
    @Autowired
    private IDesignMainMovieService designMainMovieService;

    /**
     * 分页列表查询
     *
     * @param designMain
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "设计壮游-分页列表查询")
    @ApiOperation(value = "设计壮游-分页列表查询", notes = "设计壮游-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<DesignMain>> queryPageList(DesignMain designMain,
                                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                   HttpServletRequest req) {
        QueryWrapper<DesignMain> queryWrapper = QueryGenerator.initQueryWrapper(designMain, req.getParameterMap());
        queryWrapper.orderByDesc("id");
        Page<DesignMain> page = new Page<DesignMain>(pageNo, pageSize);
        IPage<DesignMain> pageList = designMainService.page(page, queryWrapper);
        return Result.OK(pageList);
    }


    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "设计壮游-通过id删除")
    @ApiOperation(value = "设计壮游-通过id删除", notes = "设计壮游-通过id删除")
    //@RequiresPermissions("gooddesign:design_main:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) Integer id) {
        designMainService.removeById(id);
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
    //@RequiresPermissions("gooddesign:design_main:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.designMainService.removeByIds(Arrays.asList(ids.split(",")));
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
    public Result<DesignMain> queryById(@RequestParam(name = "id", required = true) String id) {
        DesignMain designMain = designMainService.getById(id);
        if (designMain == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(designMain);
    }


    @AutoLog(value = "设计壮游-编辑壮游-基础信息-添加")
    @ApiOperation(value = "设计壮游-编辑壮游-基础信息-添加", notes = "编辑壮游-基础信息-添加")
    @PostMapping(value = "/addBasic")
    public Result<DesignMain> addBasic(@RequestBody @Valid DesignMainBasicVO designMainBasicVO) {
        DesignMain designContent = new DesignMain();
        BeanUtils.copyProperties(designMainBasicVO, designContent);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        designContent.setCreateBy(sysUser.getUsername());
        designContent.setCreateTime(new Date());
        designMainService.save(designContent);
        return Result.OK(designContent);
    }


    @AutoLog(value = "设计壮游-编辑壮游-相关人员映射表-添加")
    @ApiOperation(value = "设计壮游-编辑壮游-相关人员映射表-添加", notes = "编辑壮游-相关人员映射表-添加")
    @PostMapping(value = "/addRecommended")
    public Result<String> addRecommended(@RequestBody @Valid DesignStakeholderMainAddVO designStakeholderMainAddVO) {
        List<Integer> stakeholderIds = designStakeholderMainAddVO.getStakeholderIds();
        int mainId = designStakeholderMainAddVO.getMainId();
        int type = designStakeholderMainAddVO.getType();
        List<DesignMainStakeholder> result = stakeholderIds.stream().map(stakeholderId -> {
            DesignMainStakeholder designMainStakeholder = new DesignMainStakeholder();
            designMainStakeholder.setMainId(mainId);
            designMainStakeholder.setType(type);
            designMainStakeholder.setStakeholderId(stakeholderId);
            return designMainStakeholder;
        }).collect(Collectors.toList());
        designMainStakeholderService.saveBatch(result);
        return Result.OK("添加成功！");
    }

    @AutoLog(value = "设计壮游-编辑壮游-相关人员映射表-删除")
    @ApiOperation(value = "设计壮游-编辑壮游-相关人员映射表-删除", notes = "编辑壮游-相关人员映射表-删除")
    //@RequiresPermissions("gooddesign:design_main_stakeholder:add")
    @DeleteMapping(value = "/removeRecommended")
    public Result<String> reoveRecommended(@RequestParam("id") Integer id) {
        designMainStakeholderService.removeById(id);
        return Result.OK("删除成功！");
    }


}
