package org.jeecg.modules.gooddesign.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.gooddesign.entity.DesignMain;
import org.jeecg.modules.gooddesign.entity.vo.DesignMainVO;

import java.util.List;

/**
 * @Description: 设计壮游
 * @Author: jeecg-boot
 * @Date: 2023-08-18
 * @Version: V1.0
 */
public interface DesignMainMapper extends BaseMapper<DesignMain> {

    List<DesignMainVO> pageDesignMain(Page<DesignMainVO> page, @Param("designMain") DesignMain designMain);

}
