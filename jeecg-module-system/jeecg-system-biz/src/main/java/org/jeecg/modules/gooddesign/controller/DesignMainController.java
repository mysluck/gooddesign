package org.jeecg.modules.gooddesign.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.gooddesign.entity.DesignMain;
import org.jeecg.modules.gooddesign.service.IDesignMainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

 /**
 * @Description: 设计壮游
 * @Author: jeecg-boot
 * @Date:   2023-08-18
 * @Version: V1.0
 */
@Api(tags="设计壮游")
@RestController
@RequestMapping("/designMain")
@Slf4j
public class DesignMainController extends JeecgController<DesignMain, IDesignMainService> {
	@Autowired
	private IDesignMainService designMainService;
	
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
	@ApiOperation(value="设计壮游-分页列表查询", notes="设计壮游-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<DesignMain>> queryPageList(DesignMain designMain,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<DesignMain> queryWrapper = QueryGenerator.initQueryWrapper(designMain, req.getParameterMap());
		Page<DesignMain> page = new Page<DesignMain>(pageNo, pageSize);
		IPage<DesignMain> pageList = designMainService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param designMain
	 * @return
	 */
	@AutoLog(value = "设计壮游-添加")
	@ApiOperation(value="设计壮游-添加", notes="设计壮游-添加")
	//@RequiresPermissions("gooddesign:design_main:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody DesignMain designMain) {
		designMainService.save(designMain);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param designMain
	 * @return
	 */
	@AutoLog(value = "设计壮游-编辑")
	@ApiOperation(value="设计壮游-编辑", notes="设计壮游-编辑")
	//@RequiresPermissions("gooddesign:design_main:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.PUT,RequestMethod.POST})
	public Result<String> edit(@RequestBody DesignMain designMain) {
		designMainService.updateById(designMain);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "设计壮游-通过id删除")
	@ApiOperation(value="设计壮游-通过id删除", notes="设计壮游-通过id删除")
	//@RequiresPermissions("gooddesign:design_main:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		designMainService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "设计壮游-批量删除")
	@ApiOperation(value="设计壮游-批量删除", notes="设计壮游-批量删除")
	//@RequiresPermissions("gooddesign:design_main:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
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
	@ApiOperation(value="设计壮游-通过id查询", notes="设计壮游-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<DesignMain> queryById(@RequestParam(name="id",required=true) String id) {
		DesignMain designMain = designMainService.getById(id);
		if(designMain==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(designMain);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param designMain
    */
    //@RequiresPermissions("gooddesign:design_main:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DesignMain designMain) {
        return super.exportXls(request, designMain, DesignMain.class, "设计壮游");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("gooddesign:design_main:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, DesignMain.class);
    }

}
