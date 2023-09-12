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
import org.jeecg.modules.gooddesign.entity.DesignPartners;
import org.jeecg.modules.gooddesign.entity.vo.DesignPartnersSortExistVO;
import org.jeecg.modules.gooddesign.entity.vo.DesignPartnersVO;
import org.jeecg.modules.gooddesign.service.IDesignPartnersService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Description: 合作伙伴
 * @Author: jeecg-boot
 * @Date: 2023-08-19
 * @Version: V1.0
 */
@Api(tags = "好设计-合作伙伴")
@RestController
@RequestMapping("/designPartners")
@Slf4j
public class DesignPartnersController extends JeecgController<DesignPartners, IDesignPartnersService> {
    @Autowired
    private IDesignPartnersService designPartnersService;

    /**
     * 分页列表查询
     *
     * @param designPartners
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "合作伙伴-分页列表查询")
    @ApiOperation(value = "合作伙伴-分页列表查询", notes = "合作伙伴-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<DesignPartners>> queryPageList(DesignPartners designPartners,
                                                       @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                       @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                       HttpServletRequest req) {
        QueryWrapper<DesignPartners> queryWrapper = QueryGenerator.initQueryWrapper(designPartners, req.getParameterMap());
        Page<DesignPartners> page = new Page<DesignPartners>(pageNo, pageSize);
        IPage<DesignPartners> pageList = designPartnersService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    /**
     * 添加
     *
     * @param designPartners
     * @return
     */
    @AutoLog(value = "合作伙伴-添加")
    @ApiOperation(value = "合作伙伴-添加", notes = "合作伙伴-添加")
    //@RequiresPermissions("gooddesign:design_partners:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody DesignPartnersVO designPartnersVO) {
        DesignPartners bean = new DesignPartners();
        BeanUtils.copyProperties(designPartnersVO, bean);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (sysUser != null) {
            bean.setCreateBy(sysUser.getUsername());
        }
        bean.setCreateTime(new Date());

        designPartnersService.save(bean);
        return Result.OK("添加成功！");
    }

    /**
     * 编辑
     *
     * @param designPartners
     * @return
     */
    @AutoLog(value = "合作伙伴-编辑")
    @ApiOperation(value = "合作伙伴-编辑", notes = "合作伙伴-编辑")
    //@RequiresPermissions("gooddesign:design_partners:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.POST})
    public Result<String> edit(@RequestBody DesignPartnersVO designPartnersVO) {
        DesignPartners bean = new DesignPartners();
        BeanUtils.copyProperties(designPartnersVO, bean);
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (sysUser != null) {
            bean.setUpdateBy(sysUser.getUsername());
        }
        bean.setUpdateTime(new Date());
        designPartnersService.updateById(bean);
        return Result.OK("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "合作伙伴-通过id删除")
    @ApiOperation(value = "合作伙伴-通过id删除", notes = "合作伙伴-通过id删除")
    //@RequiresPermissions("gooddesign:design_partners:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        designPartnersService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "合作伙伴-批量删除")
    @ApiOperation(value = "合作伙伴-批量删除", notes = "合作伙伴-批量删除")
    //@RequiresPermissions("gooddesign:design_partners:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.designPartnersService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "合作伙伴-通过id查询")
    @ApiOperation(value = "合作伙伴-通过id查询", notes = "合作伙伴-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<DesignPartners> queryById(@RequestParam(name = "id", required = true) String id) {
        DesignPartners designPartners = designPartnersService.getById(id);
        if (designPartners == null) {
            return Result.error("未找到对应数据");
        }
        return Result.OK(designPartners);
    }


    @ApiOperation(value = "合作伙伴-序号检查", notes = "合作伙伴-序号检查，判断编码是否存在，存在，返回true")
    @PostMapping(value = "/checkSort")
    public Result checkSort(@RequestBody DesignPartnersSortExistVO designPartnersSortExistVO) {
        QueryWrapper<DesignPartners> queryWrapper = new QueryWrapper();
        queryWrapper.eq("sort", designPartnersSortExistVO.getSort());
        queryWrapper.eq("partner_type", designPartnersSortExistVO.getPartnerType());
        List<DesignPartners> list = designPartnersService.list(queryWrapper);
        if (list == null || list.isEmpty()) {
            return Result.OK("编码不存在，可以添加！", false);

        }
        return Result.OK("编码已存在，请勿添加！", true);
    }

}
