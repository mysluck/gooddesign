package org.jeecg.modules.gooddesign.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.jeecg.modules.gooddesign.entity.DesignExtraDict;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * @Description: 壮游字典（年份、城市）
 * @Author: jeecg-boot
 * @Date: 2023-08-18
 * @Version: V1.0
 */
public interface DesignExtraDictMapper extends BaseMapper<DesignExtraDict> {
    @Update("update design_extra_dict set value =#{value} where id =#{id}")
    void updateValue(@Param("id") int id, @Param("value") String value);
}
