package org.jeecg.modules.gooddesign.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.gooddesign.entity.DesignMainStakeholder;
import org.jeecg.modules.gooddesign.entity.DesignStakeholder;
import org.jeecg.modules.gooddesign.entity.vo.DesignStakeholderMainVO;
import org.jeecg.modules.gooddesign.entity.vo.DesignStakeholderVO;
import org.jeecg.modules.gooddesign.service.IDesignMainStakeholderService;
import org.jeecg.modules.gooddesign.service.IDesignStakeholderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: 相关人员（推荐委员、发现大使、观点讲者）
 * @Author: jeecg-boot
 * @Date: 2023-08-19
 * @Version: V1.0
 */
@Api(tags = "好设计-设计壮游-编辑（推荐委员、发现大使、观点讲者）")
@RestController
@RequestMapping("/designStakeholder")
@Slf4j
public class DesignStakeholderController extends JeecgController<DesignStakeholder, IDesignStakeholderService> {
    @Autowired
    IDesignMainStakeholderService designMainStakeholderService;
    @Autowired
    private IDesignStakeholderService designStakeholderService;

    /**
     * 分页列表查询
     *
     * @param designStakeholder
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "相关人员（推荐委员、发现大使、观点讲者）-分页列表查询")
    @ApiOperation(value = "相关人员-分页列表查询", notes = "相关人员-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<DesignStakeholder>> queryPageList(DesignStakeholderVO designStakeholderVO,
                                                          @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                          @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                          HttpServletRequest req) {

        DesignStakeholder designStakeholder = new DesignStakeholder();
        BeanUtils.copyProperties(designStakeholderVO, designStakeholder);
        QueryWrapper<DesignStakeholder> queryWrapper = QueryGenerator.initQueryWrapper(designStakeholder, req.getParameterMap());
        Page<DesignStakeholder> page = new Page<DesignStakeholder>(pageNo, pageSize);
        IPage<DesignStakeholder> pageList = designStakeholderService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param
     * @return
     */
    @AutoLog(value = "相关人员-添加")
    @ApiOperation(value = "相关人员-添加", notes = "相关人员-添加")
    //@RequiresPermissions("gooddesign:design_stakeholder:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody DesignStakeholderVO designStakeholderVO) {
        DesignStakeholder designStakeholder = new DesignStakeholder();
        BeanUtils.copyProperties(designStakeholderVO, designStakeholder);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        designStakeholder.setUpdateBy(sysUser.getUsername());
        designStakeholder.setUpdateTime(new Date());
        designStakeholderService.save(designStakeholder);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param designStakeholder
     * @return
     */
    @AutoLog(value = "相关人员-编辑")
    @ApiOperation(value = "相关人员-编辑", notes = "相关人员-编辑")
    //@RequiresPermissions("gooddesign:design_stakeholder:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public Result<String> edit(@RequestBody DesignStakeholderVO designStakeholderVO) {
        DesignStakeholder designStakeholder = new DesignStakeholder();
        BeanUtils.copyProperties(designStakeholderVO, designStakeholder);
        designStakeholderService.updateById(designStakeholder);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "相关人员-通过id删除")
    @ApiOperation(value = "相关人员-通过id删除", notes = "相关人员-通过id删除")
    //@RequiresPermissions("gooddesign:design_stakeholder:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) Integer id) {
        designStakeholderService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "相关人员-批量删除")
    @ApiOperation(value = "相关人员-批量删除", notes = "相关人员-批量删除")
    //@RequiresPermissions("gooddesign:design_stakeholder:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.designStakeholderService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "相关人员-通过id查询")
    @ApiOperation(value = "相关人员-通过id查询", notes = "相关人员-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<DesignStakeholder> queryById(@RequestParam(name = "id", required = true) String id) {
        DesignStakeholder designStakeholder = designStakeholderService.getById(id);
        if (designStakeholder == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(designStakeholder);
    }


    @ApiOperation(value = "相关人员-列表查询-关联编辑壮游", notes = "相关人员-列表查询-关联编辑壮游判断是否以添加")
    @GetMapping(value = "/listByMain")
    public Result<List<DesignStakeholderMainVO>> listByMain(@RequestParam(name = "mainId") @ApiParam("当前编辑壮游ID") Integer mainId,
                                                            @RequestParam(name = "type") @ApiParam("查询类型 1推荐委员 2发现大使 3观点讲者") Integer type,
                                                            HttpServletRequest req) {


        QueryWrapper<DesignStakeholder> queryWrapper = new QueryWrapper();
        queryWrapper.eq("type", type);
        List<DesignStakeholder> designStakeholders = designStakeholderService.list(queryWrapper);


        QueryWrapper<DesignMainStakeholder> mainStakeholderQueryWrapper = new QueryWrapper();
        mainStakeholderQueryWrapper.eq("main_id", mainId);
        mainStakeholderQueryWrapper.eq("type", type);
        List<DesignMainStakeholder> mainStakeholders = designMainStakeholderService.list(mainStakeholderQueryWrapper);
        List<Integer> stakeholderIds = mainStakeholders != null ? mainStakeholders.stream().map(DesignMainStakeholder::getStakeholderId).collect(Collectors.toList()) : new ArrayList<>();


        if (designStakeholders != null) {
            List<DesignStakeholderMainVO> result = designStakeholders.stream().map(designStakeholder -> {
                DesignStakeholderMainVO designStakeholderMainVO = new DesignStakeholderMainVO();
                BeanUtils.copyProperties(designStakeholder, designStakeholderMainVO);
                if (stakeholderIds != null && stakeholderIds.contains(designStakeholder.getId())) {
                    designStakeholderMainVO.setRecentAdd(1);
                } else {
                    designStakeholderMainVO.setRecentAdd(0);
                }
                return designStakeholderMainVO;
            }).collect(Collectors.toList());
            result.sort(Comparator.comparing(DesignStakeholderMainVO::getRecentAdd).reversed());
            return Result.OK(result);
        }
        return Result.OK(new ArrayList<>());
    }


}