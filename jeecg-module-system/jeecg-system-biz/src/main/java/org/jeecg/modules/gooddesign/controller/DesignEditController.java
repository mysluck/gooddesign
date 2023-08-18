package org.jeecg.modules.gooddesign.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.modules.gooddesign.entity.DesignExtraDict;
import org.jeecg.modules.gooddesign.service.IDesignExtraDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

/**
 * @Description: 壮游字典（年份、城市）
 * @Author: jeecg-boot
 * @Date: 2023-08-18
 * @Version: V1.0
 */
@Api(tags = "好设计-编辑壮游")
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




}
