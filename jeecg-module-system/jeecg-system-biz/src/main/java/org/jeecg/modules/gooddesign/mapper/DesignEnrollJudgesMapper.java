package org.jeecg.modules.gooddesign.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.jeecg.modules.gooddesign.entity.DesignEnrollJudges;
import org.jeecg.modules.gooddesign.entity.DesignEnrollParticipantsScoreVO;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopJudgesScoreVO;

import java.util.List;

/**
 * @Description: 好设计-发现100-设计师信息
 * @Author: jeecg-boot
 * @Date: 2023-08-20
 * @Version: V1.0
 */
public interface DesignEnrollJudgesMapper extends BaseMapper<DesignEnrollJudges> {

    void batchEditScreenStatus(@Param("enrollIds") List<Integer> enrollIds, @Param("screenStatus") int screenStatus);


    List<DesignTopJudgesScoreVO> pageByNameAndTopStatus(Page<DesignTopJudgesScoreVO> page, String realName, Integer topStatus, Integer sortStatus);


}
