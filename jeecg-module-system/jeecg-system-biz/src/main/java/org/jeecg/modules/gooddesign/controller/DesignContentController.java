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
import org.jeecg.modules.gooddesign.entity.DesignContent;
import org.jeecg.modules.gooddesign.entity.vo.DesignContentVO;
import org.jeecg.modules.gooddesign.service.IDesignContentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Description: 文本描述
 * @Author: jeecg-boot
 * @Date: 2023-08-18
 * @Version: V1.0
 */
@Api(tags = "好设计-首页内容编辑")
@RestController
@RequestMapping("/designContent")
@Slf4j
public class DesignContentController extends JeecgController<DesignContent, IDesignContentService> {
    @Autowired
    private IDesignContentService designContentService;

    /**
     * 分页列表查询
     *
     * @param designContent
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "文本描述-分页列表查询")
    @ApiOperation(value = "文本描述-分页列表查询", notes = "文本描述-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<DesignContent>> queryPageList(DesignContentVO designContentVO,
                                                      @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                      @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                      HttpServletRequest req) {

        DesignContent designContent = new DesignContent();
        BeanUtils.copyProperties(designContentVO, designContent);
        QueryWrapper<DesignContent> queryWrapper = QueryGenerator.initQueryWrapper(designContent, req.getParameterMap());
        Page<DesignContent> page = new Page<DesignContent>(pageNo, pageSize);
        IPage<DesignContent> pageList = designContentService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param designContentVO
     * @return
     */
    @AutoLog(value = "文本描述-添加(注意type类型 1发现设计 2设计壮游 3发现100)")
    @ApiOperation(value = "文本描述-添加", notes = "文本描述-添加")
    //@RequiresPermissions("gooddesign:design_content:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody DesignContentVO designContentVO) {
        DesignContent designContent = new DesignContent();
        BeanUtils.copyProperties(designContentVO, designContent);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (sysUser != null) {
            designContent.setCreateBy(sysUser.getUsername());
        }
        designContent.setCreateTime(new Date());
        designContent.setId(null);
        designContentService.save(designContent);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param
     * @return
     */
    @AutoLog(value = "文本描述-编辑")
    @ApiOperation(value = "文本描述-编辑", notes = "文本描述-编辑")
    //@RequiresPermissions("gooddesign:design_content:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.POST})
    public Result<String> edit(@RequestBody DesignContentVO designContentVO) {
        DesignContent designContent = new DesignContent();
        BeanUtils.copyProperties(designContentVO, designContent);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (sysUser != null) {
            designContent.setUpdateBy(sysUser.getUsername());
        }
        designContent.setUpdateTime(new Date());
        designContentService.updateById(designContent);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "文本描述-通过id删除")
    @ApiOperation(value = "文本描述-通过id删除", notes = "文本描述-通过id删除")
    //@RequiresPermissions("gooddesign:design_content:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) Integer id) {
        designContentService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "文本描述-批量删除")
    @ApiOperation(value = "文本描述-批量删除", notes = "文本描述-批量删除")
    //@RequiresPermissions("gooddesign:design_content:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.designContentService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "文本描述-通过id查询")
    @ApiOperation(value = "文本描述-通过id查询", notes = "文本描述-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<DesignContent> queryById(@RequestParam(name = "id", required = true) Integer id) {
        DesignContent designContent = designContentService.getById(id);
        if (designContent == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(designContent);
    }


    @ApiOperation(value = "文本描述-根据类型查询文本", notes = "文本描述-根据类型查询文本(1发现设计 2设计壮游 3发现100)")
    @GetMapping(value = "/queryByType")
    public Result<DesignContent> queryByType(@RequestParam(name = "type", required = true) int type) {
        QueryWrapper<DesignContent> queryWrapper = new QueryWrapper();
        queryWrapper.eq("type", type);
        List<DesignContent> list = designContentService.list(queryWrapper);
        if (list == null || list.isEmpty()) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(list.get(0));
    }


}
