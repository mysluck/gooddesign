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
import org.jeecg.modules.gooddesign.entity.DesignTopProductWork;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopProductWorkIdVO;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopProductWorkVO;
import org.jeecg.modules.gooddesign.service.IDesignTopProductWorkService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Description: 好设计-发现100-项目作品
 * @Author: jeecg-boot
 * @Date: 2023-08-20
 * @Version: V1.0
 */
@Api(tags = "好设计-发现100-项目作品")
@RestController
@RequestMapping("/gooddesign/designTopProductWork")
@Slf4j
public class DesignTopProductWorkController extends JeecgController<DesignTopProductWork, IDesignTopProductWorkService> {
    @Autowired
    private IDesignTopProductWorkService designTopProductWorkService;

    /**
     * 分页列表查询
     *
     * @param designTopProductWork
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "好设计-发现100-项目作品-分页列表查询")
    @ApiOperation(value = "好设计-发现100-项目作品-分页列表查询", notes = "好设计-发现100-项目作品-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<DesignTopProductWork>> queryPageList(DesignTopProductWork designTopProductWork,
                                                             @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                             @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                             HttpServletRequest req) {
        QueryWrapper<DesignTopProductWork> queryWrapper = QueryGenerator.initQueryWrapper(designTopProductWork, req.getParameterMap());
        Page<DesignTopProductWork> page = new Page<DesignTopProductWork>(pageNo, pageSize);
        IPage<DesignTopProductWork> pageList = designTopProductWorkService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param designTopProductWork
     * @return
     */
    @AutoLog(value = "好设计-发现100-项目作品-添加")
    @ApiOperation(value = "好设计-发现100-项目作品-添加", notes = "好设计-发现100-项目作品-添加")
    //@RequiresPermissions("gooddesign:design_top_product_work:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody DesignTopProductWorkVO designTopProductWorkVO) {
        DesignTopProductWork bean = new DesignTopProductWork();
        BeanUtils.copyProperties(designTopProductWorkVO, bean);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        bean.setCreateBy(sysUser.getUsername());
        bean.setCreateTime(new Date());

        designTopProductWorkService.save(bean);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param designTopProductWork
     * @return
     */
    @AutoLog(value = "好设计-发现100-项目作品-编辑")
    @ApiOperation(value = "好设计-发现100-项目作品-编辑", notes = "好设计-发现100-项目作品-编辑")
    //@RequiresPermissions("gooddesign:design_top_product_work:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.POST})
    public Result<String> edit(@RequestBody DesignTopProductWorkVO designTopProductWorkVO) {
        DesignTopProductWork bean = new DesignTopProductWork();
        BeanUtils.copyProperties(designTopProductWorkVO, bean);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        bean.setUpdateBy(sysUser.getUsername());
        bean.setUpdateTime(new Date());

        designTopProductWorkService.updateById(bean);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "好设计-发现100-项目作品-通过id删除")
    @ApiOperation(value = "好设计-发现100-项目作品-通过id删除", notes = "好设计-发现100-项目作品-通过id删除")
    //@RequiresPermissions("gooddesign:design_top_product_work:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        designTopProductWorkService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "好设计-发现100-项目作品-批量删除")
    @ApiOperation(value = "好设计-发现100-项目作品-批量删除", notes = "好设计-发现100-项目作品-批量删除")
    //@RequiresPermissions("gooddesign:design_top_product_work:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.designTopProductWorkService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "好设计-发现100-项目作品-通过id查询")
    @ApiOperation(value = "好设计-发现100-项目作品-通过id查询", notes = "好设计-发现100-项目作品-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<DesignTopProductWork> queryById(@RequestParam(name = "id", required = true) String id) {
        DesignTopProductWork designTopProductWork = designTopProductWorkService.getById(id);
        if (designTopProductWork == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(designTopProductWork);
    }


    @ApiOperation(value = "好设计-发现100-项目作品-批量保存", notes = "好设计-发现100-项目作品-批量保存（先删除，在保存）")
    @PostMapping(value = "/saveBatch")
    public Result<DesignTopProductWork> saveBatch(@RequestBody DesignTopProductWorkIdVO designTopProductWorkIdVO) {
        QueryWrapper<DesignTopProductWork> productWorkQueryWrapper = new QueryWrapper<>();
        Integer productId = designTopProductWorkIdVO.getProductId();
        productWorkQueryWrapper.eq("product_id", productId);
        designTopProductWorkService.remove(productWorkQueryWrapper);

        List<String> workUrl = designTopProductWorkIdVO.getWorkUrl();
        List<DesignTopProductWork>addList=new ArrayList<>();
        for (int i = 0; i < workUrl.size(); i++) {
            DesignTopProductWork designTopProductWork = new DesignTopProductWork();
            designTopProductWork.setProductId(productId);
            designTopProductWork.setWorkUrl(workUrl.get(i));
            designTopProductWork.setSort(i + 1);
            addList.add(designTopProductWork);
        }
        designTopProductWorkService.saveBatch(addList);

        return Result.OK("保存成功！");
    }


    @ApiOperation(value = "好设计-发现100-项目作品-序号检查", notes = "序号检查，判断编码是否存在，存在，返回true")
    @GetMapping(value = "/checkSort")
    public Result checkSort(@RequestParam("sort")@ApiParam("序号") Integer sort) {
        QueryWrapper<DesignTopProductWork> productWorkQueryWrapper = new QueryWrapper<>();
        productWorkQueryWrapper.eq("sort", sort);
        List<DesignTopProductWork> list = designTopProductWorkService.list(productWorkQueryWrapper);

        if (list == null || list.isEmpty()) {
            return Result.OK("编码不存在，可以添加！", false);

        }
        return Result.OK("编码已存在，请勿添加！", true);
    }

}
