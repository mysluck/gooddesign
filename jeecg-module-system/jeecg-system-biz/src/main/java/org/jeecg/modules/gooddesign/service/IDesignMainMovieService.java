package org.jeecg.modules.gooddesign.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.gooddesign.entity.DesignMainMovie;
import org.jeecg.modules.gooddesign.entity.vo.DesignMainMovieVO;

import java.util.List;

/**
 * @Description: 现场照片
 * @Author: jeecg-boot
 * @Date: 2023-08-18
 * @Version: V1.0
 */
public interface IDesignMainMovieService extends IService<DesignMainMovie> {
    List<DesignMainMovieVO> queryByMainId(int mainId);

    List<DesignMainMovieVO> queryByMainIds(List<Integer> mainIds);

}
