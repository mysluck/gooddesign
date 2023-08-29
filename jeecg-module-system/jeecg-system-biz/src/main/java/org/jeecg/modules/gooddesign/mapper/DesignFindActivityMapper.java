package org.jeecg.modules.gooddesign.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.gooddesign.entity.DesignFindActivity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 好设计-发现设计-活动
 * @Author: jeecg-boot
 * @Date: 2023-08-24
 * @Version: V1.0
 */
public interface DesignFindActivityMapper extends BaseMapper<DesignFindActivity> {

    @Select("select distinct(year)  FROM design_find_activity order by year desc")
    List<String> getYears();


    @Select("select distinct(city)  FROM design_find_activity where order by id desc")
    List<String> getCity();


}
