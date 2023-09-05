package org.jeecg.modules.gooddesign.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.gooddesign.entity.DesignExtraDict;
import org.jeecg.modules.gooddesign.entity.vo.DesignExtraDictVO;
import org.jeecg.modules.gooddesign.service.IDesignExtraDictService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description: 壮游字典（年份、城市）
 * @Author: jeecg-boot
 * @Date: 2023-08-18
 * @Version: V1.0
 */
@Api(tags = "好设计-设计壮游-编辑（年份、城市）")
@RestController
@RequestMapping("/designEdit")
@Slf4j
public class DesignEditController extends JeecgController<DesignExtraDict, IDesignExtraDictService> {
    @Autowired
    private IDesignExtraDictService designExtraDictService;

    @ApiOperation(value = "编辑壮游-年份展示", notes = "编辑壮游-年份展示")
    @GetMapping(value = "/yearList")
    public Result<List<DesignExtraDict>> yearList() {
        List<DesignExtraDict> list = designExtraDictService.list(1);
        list.sort(Comparator.comparing(DesignExtraDict::getValue).reversed());
        return Result.OK(list);
    }

    @ApiOperation(value = "编辑壮游-年份展示", notes = "编辑壮游-年份展示")
    @GetMapping(value = "/addYear")
    public Result<List<DesignExtraDict>> addYear(@Param("year") String year) {
        designExtraDictService.saveExt(1, year);
        return Result.OK("保存成功");
    }

    @ApiOperation(value = "编辑壮游-城市展示", notes = "编辑壮游-城市展示")
    @GetMapping(value = "/cityList")
    public Result<List<DesignExtraDict>> cityList() {
        List<DesignExtraDict> list = designExtraDictService.list(2);
        list.sort(Comparator.comparing(DesignExtraDict::getId).reversed());
        return Result.OK(list);
    }

    @ApiOperation(value = "编辑壮游-添加城市", notes = "编辑壮游-添加城市")
    @GetMapping(value = "/addCity")
    public Result<List<DesignExtraDict>> addCity(@Param("city") String city) {
        designExtraDictService.saveExt(2, city);
        return Result.OK("保存成功");
    }


    @ApiOperation(value = "编辑壮游-字典展示", notes = "编辑壮游-字典展示")
    @GetMapping(value = "/listTree")
    public Result<List<DesignExtraDictVO>> listTree() {
        List<DesignExtraDict> list = designExtraDictService.list(1);
        List<DesignExtraDictVO> result = new ArrayList<>();
        if (!list.isEmpty()) {
            result = list.stream().map(dict -> {
                DesignExtraDictVO designExtraDictVO = new DesignExtraDictVO();
                BeanUtils.copyProperties(dict, designExtraDictVO);
                List<DesignExtraDictVO> childs = designExtraDictService.listByParentId(dict.getId()).stream().map(child -> {
                    DesignExtraDictVO vo = new DesignExtraDictVO();
                    BeanUtils.copyProperties(child, vo);
                    return vo;
                }).collect(Collectors.toList());
                designExtraDictVO.setChild(childs);
                return designExtraDictVO;
            }).collect(Collectors.toList());
        }
        result.sort(Comparator.comparing(DesignExtraDictVO::getValue).reversed());
        return Result.OK(result);
    }

	@ApiOperation(value="壮游字典（年份、城市）-编辑", notes="壮游字典（年份、城市）-编辑")
	//@RequiresPermissions("gooddesign:design_extra_dict:edit")
	@RequestMapping(value = "/edit", method = {RequestMethod.POST})
	public Result<String> edit(@RequestBody DesignExtraDict designExtraDict) {
		designExtraDictService.updateById(designExtraDict);
		return Result.OK("编辑成功!");
	}

    //	//@AutoLog(value = "壮游字典（年份、城市）-通过id查询")
	@ApiOperation(value="壮游字典（年份、城市）-通过id查询", notes="壮游字典（年份、城市）-通过id查询")
	@GetMapping(value = "/queryById")
	public Result<DesignExtraDict> queryById(@RequestParam(name="id",required=true) String id) {
		DesignExtraDict designExtraDict = designExtraDictService.getById(id);
		if(designExtraDict==null) {
			return Result.error("未找到对应数据");
		}
		return Result.OK(designExtraDict);
	}
}
