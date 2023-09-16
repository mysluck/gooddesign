package org.jeecg.modules.gooddesign.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.gooddesign.entity.DesignMainStakeholder;
import org.jeecg.modules.gooddesign.entity.vo.DesignMainStakeholderVO;

import java.util.List;

/**
 * @Description: 好设计-编辑壮游-相关人员映射表
 * @Author: jeecg-boot
 * @Date: 2023-08-19
 * @Version: V1.0
 */
public interface DesignMainStakeholderMapper extends BaseMapper<DesignMainStakeholder> {

    //    @Select("select distinct(s.stakeholder_id) from design_main_stakeholder s where s.main_id =?1 and s.type = ?2")
    List<Integer> getStakeholderIds(@Param("mainId") int mainId, @Param("type") int type);

    List<DesignMainStakeholderVO> queryMainStakeholder(@Param("mainId") int mainId);

    List<DesignMainStakeholderVO> queryMainStakeholderByType(@Param("mainId") int mainId, @Param("type") int type);


}
