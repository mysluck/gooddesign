//package org.jeecg.modules.gooddesign.controller;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import lombok.extern.slf4j.Slf4j;
//import org.jeecg.common.api.vo.Result;
//import org.jeecg.common.aspect.annotation.AutoLog;
//import org.jeecg.common.system.base.controller.JeecgController;
//import org.jeecg.common.system.query.QueryGenerator;
//import org.jeecg.modules.gooddesign.entity.DesignExtraDict;
//import org.jeecg.modules.gooddesign.service.IDesignExtraDictService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.Arrays;
//
// /**
// * @Description: 壮游字典（年份、城市）
// * @Author: jeecg-boot
// * @Date:   2023-08-18
// * @Version: V1.0
// */
//@Api(tags="壮游字典（年份、城市）")
//@RestController
//@RequestMapping("/designExtraDict")
//@Slf4j
//public class DesignExtraDictController extends JeecgController<DesignExtraDict, IDesignExtraDictService> {
//	@Autowired
//	private IDesignExtraDictService designExtraDictService;
//
//	/**
//	 * 分页列表查询
//	 *
//	 * @param designExtraDict
//	 * @param pageNo
//	 * @param pageSize
//	 * @param req
//	 * @return
//	 */
//	//@AutoLog(value = "壮游字典（年份、城市）-分页列表查询")
//	@ApiOperation(value="壮游字典（年份、城市）-分页列表查询", notes="壮游字典（年份、城市）-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<DesignExtraDict>> queryPageList(DesignExtraDict designExtraDict,
//								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//								   HttpServletRequest req) {
//		QueryWrapper<DesignExtraDict> queryWrapper = QueryGenerator.initQueryWrapper(designExtraDict, req.getParameterMap());
//		Page<DesignExtraDict> page = new Page<DesignExtraDict>(pageNo, pageSize);
//		IPage<DesignExtraDict> pageList = designExtraDictService.page(page, queryWrapper);
//		return Result.OK(pageList);
//	}
//
//	/**
//	 *   添加
//	 *
//	 * @param designExtraDict
//	 * @return
//	 */
//	@AutoLog(value = "壮游字典（年份、城市）-添加")
//	@ApiOperation(value="壮游字典（年份、城市）-添加", notes="壮游字典（年份、城市）-添加")
//	//@RequiresPermissions("gooddesign:design_extra_dict:add")
//	@PostMapping(value = "/add")
//	public Result<String> add(@RequestBody DesignExtraDict designExtraDict) {
//		designExtraDictService.save(designExtraDict);
//		return Result.OK("添加成功！");
//	}
//
//	/**
//	 *  编辑
//	 *
//	 * @param designExtraDict
//	 * @return
//	 */
//	@AutoLog(value = "壮游字典（年份、城市）-编辑")
//	@ApiOperation(value="壮游字典（年份、城市）-编辑", notes="壮游字典（年份、城市）-编辑")
//	//@RequiresPermissions("gooddesign:design_extra_dict:edit")
//	@RequestMapping(value = "/edit", method = {RequestMethod.POST})
//	public Result<String> edit(@RequestBody DesignExtraDict designExtraDict) {
//		designExtraDictService.updateById(designExtraDict);
//		return Result.OK("编辑成功!");
//	}
//
//	/**
//	 *   通过id删除
//	 *
//	 * @param id
//	 * @return
//	 */
//	@AutoLog(value = "壮游字典（年份、城市）-通过id删除")
//	@ApiOperation(value="壮游字典（年份、城市）-通过id删除", notes="壮游字典（年份、城市）-通过id删除")
//	//@RequiresPermissions("gooddesign:design_extra_dict:delete")
//	@DeleteMapping(value = "/delete")
//	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
//		designExtraDictService.removeById(id);
//		return Result.OK("删除成功!");
//	}
//
//	/**
//	 *  批量删除
//	 *
//	 * @param ids
//	 * @return
//	 */
//	@AutoLog(value = "壮游字典（年份、城市）-批量删除")
//	@ApiOperation(value="壮游字典（年份、城市）-批量删除", notes="壮游字典（年份、城市）-批量删除")
//	//@RequiresPermissions("gooddesign:design_extra_dict:deleteBatch")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		this.designExtraDictService.removeByIds(Arrays.asList(ids.split(",")));
//		return Result.OK("批量删除成功!");
//	}
//
//	/**
//	 * 通过id查询
//	 *
//	 * @param id
//	 * @return
//	 */
//	//@AutoLog(value = "壮游字典（年份、城市）-通过id查询")
//	@ApiOperation(value="壮游字典（年份、城市）-通过id查询", notes="壮游字典（年份、城市）-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<DesignExtraDict> queryById(@RequestParam(name="id",required=true) String id) {
//		DesignExtraDict designExtraDict = designExtraDictService.getById(id);
//		if(designExtraDict==null) {
//			return Result.error("未找到对应数据");
//		}
//		return Result.OK(designExtraDict);
//	}
//
//    /**
//    * 导出excel
//    *
//    * @param request
//    * @param designExtraDict
//    */
//    //@RequiresPermissions("gooddesign:design_extra_dict:exportXls")
//    @RequestMapping(value = "/exportXls")
//    public ModelAndView exportXls(HttpServletRequest request, DesignExtraDict designExtraDict) {
//        return super.exportXls(request, designExtraDict, DesignExtraDict.class, "壮游字典（年份、城市）");
//    }
//
//    /**
//      * 通过excel导入数据
//    *
//    * @param request
//    * @param response
//    * @return
//    */
//    //@RequiresPermissions("gooddesign:design_extra_dict:importExcel")
//    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
//    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
//        return super.importExcel(request, response, DesignExtraDict.class);
//    }
//
//}
