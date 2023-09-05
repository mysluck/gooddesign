package org.jeecg.modules.gooddesign.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.jeecg.modules.gooddesign.entity.DesignActivity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 好设计-跨年启停
 * @Author: jeecg-boot
 * @Date:   2023-08-19
 * @Version: V1.0
 */
public interface DesignActivityMapper extends BaseMapper<DesignActivity> {

//    @Select("select distinct(year)  FROM design_find_activity order by year desc")
    @Select("SELECT id, activity_name, LEFT(publish_time, 4) AS publish_time, activity_status FROM design_activity a WHERE id IN (SELECT distinct(activity_id) FROM design_top_judges ) ORDER BY publish_time DESC ")
    List<DesignActivity> queryActivityList();
}
