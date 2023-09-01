package org.jeecg.modules.gooddesign.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.util.oConvertUtils;
import org.jeecg.modules.gooddesign.entity.DesignPartProductDetail;
import org.jeecg.modules.gooddesign.service.IDesignPartProductDetailService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;

import org.jeecgframework.poi.excel.ExcelImportUtil;
import org.jeecgframework.poi.excel.def.NormalExcelConstants;
import org.jeecgframework.poi.excel.entity.ExportParams;
import org.jeecgframework.poi.excel.entity.ImportParams;
import org.jeecgframework.poi.excel.view.JeecgEntityExcelView;
import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.apache.shiro.authz.annotation.RequiresPermissions;

 /**
 * @Description: 参赛者设计师实景图
 * @Author: jeecg-boot
 * @Date:   2023-08-17
 * @Version: V1.0
 */
@Api(tags="好设计-参赛者报名-设计师实景图")
@RestController
@RequestMapping("/designPartProductDetail")
@Slf4j
public class DesignPartProductDetailController extends JeecgController<DesignPartProductDetail, IDesignPartProductDetailService> {
	@Autowired
	private IDesignPartProductDetailService designPartProductDetailService;
	
	/**
	 * 分页列表查询
	 *
	 * @param designPartProductDetail
	 * @param pageNo
	 * @param pageSize
	 * @param req
	 * @return
	 */
	//@AutoLog(value = "参赛者设计师实景图-分页列表查询")
	@ApiOperation(value="参赛者设计师实景图-分页列表查询", notes="参赛者设计师实景图-分页列表查询")
	@GetMapping(value = "/list")
	public Result<IPage<DesignPartProductDetail>> queryPageList(DesignPartProductDetail designPartProductDetail,
								   @RequestParam(name="pageNo", defaultValue="1") Integer pageNo,
								   @RequestParam(name="pageSize", defaultValue="10") Integer pageSize,
								   HttpServletRequest req) {
		QueryWrapper<DesignPartProductDetail> queryWrapper = QueryGenerator.initQueryWrapper(designPartProductDetail, req.getParameterMap());
		Page<DesignPartProductDetail> page = new Page<DesignPartProductDetail>(pageNo, pageSize);
		IPage<DesignPartProductDetail> pageList = designPartProductDetailService.page(page, queryWrapper);
		return Result.OK(pageList);
	}
	
	/**
	 *   添加
	 *
	 * @param designPartProductDetail
	 * @return
	 */
	@AutoLog(value = "参赛者设计师实景图-添加")
	@ApiOperation(value="参赛者设计师实景图-添加", notes="参赛者设计师实景图-添加")
	//@RequiresPermissions("gooddesign:design_part_product_detail:add")
	@PostMapping(value = "/add")
	public Result<String> add(@RequestBody DesignPartProductDetail designPartProductDetail) {
		designPartProductDetailService.save(designPartProductDetail);
		return Result.OK("添加成功！");
	}
	
	/**
	 *  编辑
	 *
	 * @param designPartProductDetail
	 * @return
	 */
	@AutoLog(value = "参赛者设计师实景图-编辑")
	@ApiOperation(value="参赛者设计师实景图-编辑", notes="参赛者设计师实景图-编辑")
	//@RequiresPermissions("gooddesign:design_part_product_detail:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.POST})
	public Result<String> edit(@RequestBody DesignPartProductDetail designPartProductDetail) {
		designPartProductDetailService.updateById(designPartProductDetail);
		return Result.OK("编辑成功!");
	}
	
	/**
	 *   通过id删除
	 *
	 * @param id
	 * @return
	 */
	@AutoLog(value = "参赛者设计师实景图-通过id删除")
	@ApiOperation(value="参赛者设计师实景图-通过id删除", notes="参赛者设计师实景图-通过id删除")
	//@RequiresPermissions("gooddesign:design_part_product_detail:delete")
	@DeleteMapping(value = "/delete")
	public Result<String> delete(@RequestParam(name="id",required=true) String id) {
		designPartProductDetailService.removeById(id);
		return Result.OK("删除成功!");
	}
	
	/**
	 *  批量删除
	 *
	 * @param ids
	 * @return
	 */
	@AutoLog(value = "参赛者设计师实景图-批量删除")
	@ApiOperation(value="参赛者设计师实景图-批量删除", notes="参赛者设计师实景图-批量删除")
	//@RequiresPermissions("gooddesign:design_part_product_detail:deleteBatch")
	@DeleteMapping(value = "/deleteBatch")
	public Result<String> deleteBatch(@RequestParam(name="ids",required=true) String ids) {
		this.designPartProductDetailService.removeByIds(Arrays.asList(ids.split(",")));
		return Result.OK("批量删除成功!");
	}
	
	/**
	 * 通过id查询
	 *
	 * @param id
	 * @return
	 */
	//@AutoLog(value = "参赛者设计师实景图-通过id查询")
	@ApiOperation(value="参赛者设计师实景图-通过id查询", notes="参赛者设计师实景图-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<DesignPartProductDetail> queryById(@RequestParam(name="id",required=true) String id) {
		DesignPartProductDetail designPartProductDetail = designPartProductDetailService.getById(id);
		if(designPartProductDetail==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(designPartProductDetail);
	}

    /**
    * 导出excel
    *
    * @param request
    * @param designPartProductDetail
    */
    //@RequiresPermissions("gooddesign:design_part_product_detail:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DesignPartProductDetail designPartProductDetail) {
        return super.exportXls(request, designPartProductDetail, DesignPartProductDetail.class, "参赛者设计师实景图");
    }

    /**
      * 通过excel导入数据
    *
    * @param request
    * @param response
    * @return
    */
    //@RequiresPermissions("gooddesign:design_part_product_detail:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, DesignPartProductDetail.class);
    }

}
