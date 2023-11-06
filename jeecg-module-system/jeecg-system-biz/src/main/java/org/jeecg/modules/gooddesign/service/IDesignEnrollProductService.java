package org.jeecg.modules.gooddesign.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.gooddesign.entity.DesignEnrollProduct;
import org.jeecg.modules.gooddesign.entity.vo.DesignTopProductVO;

import java.util.List;

/**
 * @Description: 好设计-发现100-项目信息
 * @Author: jeecg-boot
 * @Date: 2023-08-20
 * @Version: V1.0
 */
public interface IDesignEnrollProductService extends IService<DesignEnrollProduct> {
    void saveProduct(DesignTopProductVO designTopProductVO);

    void editProduct(DesignTopProductVO designTopProductVO);

    void editProducsts(List<DesignTopProductVO> designTopProductVOs, int judgesId);

    DesignTopProductVO queryByProductId(Integer id);

    List<DesignTopProductVO> queryByTopJudgesId(Integer id);

    List<DesignTopProductVO> queryDetailByJudgesId(Integer id);

    void updateTopStatusByDesignNos(List<String>designNos);
}
