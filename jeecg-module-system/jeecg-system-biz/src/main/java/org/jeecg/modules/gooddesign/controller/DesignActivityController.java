package org.jeecg.modules.gooddesign.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.gooddesign.entity.DesignActivity;
import org.jeecg.modules.gooddesign.entity.vo.DesignActivityVO;
import org.jeecg.modules.gooddesign.service.IDesignActivityService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Description: 好设计-跨年启停
 * @Author: jeecg-boot
 * @Date: 2023-08-19
 * @Version: V1.0
 */
@Api(tags = "好设计-跨年启停")
@RestController
@RequestMapping("/designActivity")
@Slf4j
public class DesignActivityController extends JeecgController<DesignActivity, IDesignActivityService> {
    @Autowired
    private IDesignActivityService designActivityService;

    /**
     * 分页列表查询
     *
     * @param designActivity
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "好设计-跨年启停-分页列表查询")
    @ApiOperation(value = "好设计-跨年启停-分页列表查询", notes = "好设计-跨年启停-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<DesignActivity>> queryPageList(DesignActivity designActivity,
                                                       @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                       @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                       HttpServletRequest req) {
        if (designActivity != null && org.apache.commons.lang.StringUtils.isNotEmpty(designActivity.getActivityName())) {
            designActivity.setActivityName("*" + designActivity.getActivityName() + "*");
        }
        QueryWrapper<DesignActivity> queryWrapper = QueryGenerator.initQueryWrapper(designActivity, req.getParameterMap());
        Page<DesignActivity> page = new Page<DesignActivity>(pageNo, pageSize);
        IPage<DesignActivity> pageList = designActivityService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param designActivity
     * @return
     */
    @AutoLog(value = "好设计-跨年启停-添加")
    @ApiOperation(value = "好设计-跨年启停-添加", notes = "好设计-跨年启停-添加")
    //@RequiresPermissions("gooddesign:design_activity:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody DesignActivityVO designActivityVO) {
        DesignActivity bean = new DesignActivity();
        if (1 == designActivityVO.getActivityStatus()) {
            if (designActivityService.checkActivityStatus()) {
                return Result.OK("存在正在进行中的活动，请处理！");
            }
            bean.setPublishTime(new Date());
        }
        BeanUtils.copyProperties(designActivityVO, bean);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (sysUser != null) {
            bean.setCreateBy(sysUser.getUsername());
        }
        bean.setCreateTime(new Date());
        designActivityService.save(bean);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param designActivity
     * @return
     */
    @AutoLog(value = "好设计-跨年启停-编辑")
    @ApiOperation(value = "好设计-跨年启停-编辑", notes = "好设计-跨年启停-编辑,传入ID和发布状态即可")
    //@RequiresPermissions("gooddesign:design_activity:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.POST})
    public Result<String> edit(@RequestBody DesignActivityVO designActivityVO) {


        DesignActivity bean = new DesignActivity();
        BeanUtils.copyProperties(designActivityVO, bean);
        if (designActivityVO.getActivityStatus() != null && 1 == designActivityVO.getActivityStatus()) {
            DesignActivity activity = designActivityService.getActivity();
            if (activity != null && activity.getId() != null && !designActivityVO.getId().equals(activity.getId())) {
                return Result.OK("存在正在进行中的活动，请处理！");
            }
            bean.setPublishTime(new Date());
        }
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (sysUser != null) {
            bean.setUpdateBy(sysUser.getUsername());
        }
        bean.setUpdateTime(new Date());
        designActivityService.updateById(bean);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "好设计-跨年启停-通过id删除")
    @ApiOperation(value = "好设计-跨年启停-通过id删除", notes = "好设计-跨年启停-通过id删除")
    //@RequiresPermissions("gooddesign:design_activity:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        designActivityService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "好设计-跨年启停-批量删除")
    @ApiOperation(value = "好设计-跨年启停-批量删除", notes = "好设计-跨年启停-批量删除")
    //@RequiresPermissions("gooddesign:design_activity:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.designActivityService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "好设计-跨年启停-通过id查询")
    @ApiOperation(value = "好设计-跨年启停-通过id查询", notes = "好设计-跨年启停-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<DesignActivity> queryById(@RequestParam(name = "id", required = true) String id) {
        DesignActivity designActivity = designActivityService.getById(id);
        if (designActivity == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(designActivity);
    }


    @ApiOperation(value = "合作伙伴-序号检查", notes = "合作伙伴-序号检查，判断编码是否存在，存在，返回true")
    @PostMapping(value = "/checkActivityStatus")
    public Result checkActivityStatus() {
        if (designActivityService.checkActivityStatus()) {
            return Result.OK("存在正在进行中的活动，请确认！", true);
        }
        return Result.OK("当前无进行中活动，可以修改！", false);
    }

}
