package org.jeecg.modules.gooddesign.service;

import org.jeecg.modules.gooddesign.entity.DesignMainImage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.gooddesign.entity.vo.DesignMainImageVO;
import org.jeecg.modules.gooddesign.entity.vo.DesignMainMovieVO;

import java.util.List;

/**
 * @Description: 现场照片
 * @Author: jeecg-boot
 * @Date:   2023-08-18
 * @Version: V1.0
 */
public interface IDesignMainImageService extends IService<DesignMainImage> {
    List<DesignMainImageVO> queryByMainId(int mainId);

    List<DesignMainImageVO> queryByMainIds(List<Integer> mainIds);

}
