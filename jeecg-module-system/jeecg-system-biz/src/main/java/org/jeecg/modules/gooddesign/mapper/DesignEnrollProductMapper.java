package org.jeecg.modules.gooddesign.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.gooddesign.entity.DesignEnrollProduct;

import java.util.List;

/**
 * @Description: 好设计-发现100-项目信息
 * @Author: jeecg-boot
 * @Date: 2023-08-20
 * @Version: V1.0
 */
public interface DesignEnrollProductMapper extends BaseMapper<DesignEnrollProduct> {

    void updateTopStatusByDesignNos(@Param("designNos") List<String> designNos, @Param("topStatus") int topStatus);
}
