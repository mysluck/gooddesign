package org.jeecg.modules.gooddesign.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.modules.gooddesign.entity.DesignExtraDict;
import org.jeecg.modules.gooddesign.entity.DesignJudges;
import org.jeecg.modules.gooddesign.mapper.DesignExtraDictMapper;
import org.jeecg.modules.gooddesign.service.IDesignExtraDictService;
import org.jeecg.modules.gooddesign.service.IDesignFindActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;

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
    DesignExtraDictMapper designExtraDictMapper;
    @Autowired
    private IDesignExtraDictService designExtraDictService;
    @Autowired
    IDesignFindActivityService designFindActivityService;

    @ApiOperation(value = "编辑壮游-年份展示", notes = "编辑壮游-年份展示")
    @GetMapping(value = "/yearList")
    public Result<List<DesignExtraDict>> yearList() {
        List<DesignExtraDict> list = designExtraDictService.list(1);
        list.sort(Comparator.comparing(DesignExtraDict::getValue).reversed());
        return Result.OK(list);
    }

    @ApiOperation(value = "编辑壮游-添加年份", notes = "编辑壮游-年份展示")
    @GetMapping(value = "/addYear")
    public Result<List<DesignExtraDict>> addYear(@Param("year") String year) {
        designExtraDictService.saveExt(1, year);
        return Result.OK("保存成功");
    }


    @ApiOperation(value = "编辑壮游-修改年份/城市", notes = "编辑壮游-年份展示")
    @GetMapping(value = "/edit")
    public Result<List<DesignExtraDict>> editYear(@Param("value") String value, @Param("年份ID") @ApiParam("年份ID") int id) {
        designExtraDictMapper.updateValue(id, value);
        return Result.OK("修改成功");
    }


    @ApiOperation(value = "编辑壮游-城市展示", notes = "编辑壮游-城市展示")
    @GetMapping(value = "/cityList")
    public Result<List<DesignExtraDict>> cityList(@Param("年份ID") @ApiParam("年份ID") Integer id) {
        if (id == null) {
            return Result.error("请输入年份ID！");
        }
        List<DesignExtraDict> list = designExtraDictService.list(id, 2);
        list.sort(Comparator.comparing(DesignExtraDict::getId).reversed());
        return Result.OK(list);
    }

    @ApiOperation(value = "编辑壮游-添加城市", notes = "编辑壮游-添加城市")
    @GetMapping(value = "/addCity")
    public Result<List<DesignExtraDict>> addCity(@Param("city") String city, @Param("年份ID") @ApiParam("年份ID,必传") Integer id) {
        if (id == null) {
            return Result.error("请输入年份ID！");
        }
        designExtraDictService.saveExtAndPid(2, city, id);
        return Result.OK("保存成功");
    }

    @AutoLog(value = "编辑壮游-通过id删除字典（年份/城市）")
    @ApiOperation(value = "编辑壮游-通过id删除字典（年份/城市", notes = "编辑壮游-通过id删除字典（年份/城市")
    //@RequiresPermissions("user_designer:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) Integer id) {
        designExtraDictService.removeById(id);
        return Result.OK("删除成功!");
    }

}
