//package org.jeecg.modules.gooddesign.controller;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Collectors;
//import java.io.IOException;
//import java.io.UnsupportedEncodingException;
//import java.net.URLDecoder;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import org.jeecg.common.api.vo.Result;
//import org.jeecg.common.system.query.QueryGenerator;
//import org.jeecg.common.util.oConvertUtils;
//import org.jeecg.modules.gooddesign.entity.DesignPartProduct;
//import org.jeecg.modules.gooddesign.service.IDesignPartProductService;
//
//import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
//import com.baomidou.mybatisplus.core.metadata.IPage;
//import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
//import lombok.extern.slf4j.Slf4j;
//
//import org.jeecgframework.poi.excel.ExcelImportUtil;
//import org.jeecgframework.poi.excel.def.NormalExcelConstants;
//import org.jeecgframework.poi.excel.entity.ExportParams;
//import org.jeecgframework.poi.excel.entity.ImportParams;
//import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
//import org.jeecg.common.system.base.controller.JeecgController;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import org.springframework.web.multipart.MultipartHttpServletRequest;
//import org.springframework.web.servlet.ModelAndView;
//import com.alibaba.fastjson.JSON;
//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
//import org.jeecg.common.aspect.annotation.AutoLog;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//
// /**
// * @Description: 参赛者设计师作品
// * @Author: jeecg-boot
// * @Date:   2023-08-17
// * @Version: V1.0
// */
//@Api(tags="好设计-参赛者报名-设计师作品")
//@RestController
//@RequestMapping("/designPartProduct")
//@Slf4j
//public class DesignPartProductController extends JeecgController<DesignPartProduct, IDesignPartProductService> {
//	@Autowired
//	private IDesignPartProductService designPartProductService;
//
//	/**
//	 * 分页列表查询
//	 *
//	 * @param designPartProduct
//	 * @param pageNo
//	 * @param pageSize
//	 * @param req
//	 * @return
//	 */
//	//@AutoLog(value = "参赛者设计师作品-分页列表查询")
//	@ApiOperation(value="参赛者设计师作品-分页列表查询", notes="参赛者设计师作品-分页列表查询")
//	@GetMapping(value = "/list")
//	public Result<IPage<DesignPartProduct>> queryPageList(DesignPartProduct designPartProduct,
//								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
//								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
//								   HttpServletRequest req) {
//		QueryWrapper<DesignPartProduct> queryWrapper = QueryGenerator.initQueryWrapper(designPartProduct, req.getParameterMap());
//		Page<DesignPartProduct> page = new Page<DesignPartProduct>(pageNo, pageSize);
//		IPage<DesignPartProduct> pageList = designPartProductService.page(page, queryWrapper);
//		return Result.OK(pageList);
//	}
//
//	/**
//	 *   添加
//	 *
//	 * @param designPartProduct
//	 * @return
//	 */
//	@AutoLog(value = "参赛者设计师作品-添加")
//	@ApiOperation(value="参赛者设计师作品-添加", notes="参赛者设计师作品-添加")
//	//@RequiresPermissions("gooddesign:design_part_product:add")
//	@PostMapping(value = "/add")
//	public Result<String> add(@RequestBody DesignPartProduct designPartProduct) {
//		designPartProductService.save(designPartProduct);
//		return Result.OK("添加成功！");
//	}
//
//	/**
//	 *  编辑
//	 *
//	 * @param designPartProduct
//	 * @return
//	 */
//	@AutoLog(value = "参赛者设计师作品-编辑")
//	@ApiOperation(value="参赛者设计师作品-编辑", notes="参赛者设计师作品-编辑")
//	//@RequiresPermissions("gooddesign:design_part_product:edit")
//	@RequestMapping(value = "/edit", method = {RequestMethod.POST})
//	public Result<String> edit(@RequestBody DesignPartProduct designPartProduct) {
//		designPartProductService.updateById(designPartProduct);
//		return Result.OK("编辑成功!");
//	}
//
//	/**
//	 *   通过id删除
//	 *
//	 * @param id
//	 * @return
//	 */
//	@AutoLog(value = "参赛者设计师作品-通过id删除")
//	@ApiOperation(value="参赛者设计师作品-通过id删除", notes="参赛者设计师作品-通过id删除")
//	//@RequiresPermissions("gooddesign:design_part_product:delete")
//	@DeleteMapping(value = "/delete")
//	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
//		designPartProductService.removeById(id);
//		return Result.OK("删除成功!");
//	}
//
//	/**
//	 *  批量删除
//	 *
//	 * @param ids
//	 * @return
//	 */
//	@AutoLog(value = "参赛者设计师作品-批量删除")
//	@ApiOperation(value="参赛者设计师作品-批量删除", notes="参赛者设计师作品-批量删除")
//	//@RequiresPermissions("gooddesign:design_part_product:deleteBatch")
//	@DeleteMapping(value = "/deleteBatch")
//	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
//		this.designPartProductService.removeByIds(Arrays.asList(ids.split(",")));
//		return Result.OK("批量删除成功!");
//	}
//
//	/**
//	 * 通过id查询
//	 *
//	 * @param id
//	 * @return
//	 */
//	//@AutoLog(value = "参赛者设计师作品-通过id查询")
//	@ApiOperation(value="参赛者设计师作品-通过id查询", notes="参赛者设计师作品-通过id查询")
//	@GetMapping(value = "/queryById")
//	public Result<DesignPartProduct> queryById(@RequestParam(name="id",required=true) String id) {
//		DesignPartProduct designPartProduct = designPartProductService.getById(id);
//		if(designPartProduct==null) {
//			return Result.error("未找到对应数据");
//		}
//		return Result.OK(designPartProduct);
//	}
//
//    /**
//    * 导出excel
//    *
//    * @param request
//    * @param designPartProduct
//    */
//    //@RequiresPermissions("gooddesign:design_part_product:exportXls")
//    @RequestMapping(value = "/exportXls")
//    public ModelAndView exportXls(HttpServletRequest request, DesignPartProduct designPartProduct) {
//        return super.exportXls(request, designPartProduct, DesignPartProduct.class, "参赛者设计师作品");
//    }
//
//    /**
//      * 通过excel导入数据
//    *
//    * @param request
//    * @param response
//    * @return
//    */
//    //@RequiresPermissions("gooddesign:design_part_product:importExcel")
//    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
//    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
//        return super.importExcel(request, response, DesignPartProduct.class);
//    }
//
//}
