package org.jeecg.modules.gooddesign.service;

import org.jeecg.modules.gooddesign.entity.DesignExtraDict;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 壮游字典（年份、城市）
 * @Author: jeecg-boot
 * @Date: 2023-08-18
 * @Version: V1.0
 */
public interface IDesignExtraDictService extends IService<DesignExtraDict> {
    boolean saveExt(int type, String value);

    boolean saveExtAndPid(int type, String value, int parentId);

    List<DesignExtraDict> list(int type);

    List<DesignExtraDict> list(int id, int type);


}
