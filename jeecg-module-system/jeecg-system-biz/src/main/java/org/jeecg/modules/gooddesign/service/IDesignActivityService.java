package org.jeecg.modules.gooddesign.service;

import org.jeecg.modules.gooddesign.entity.DesignActivity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @Description: 好设计-跨年启停
 * @Author: jeecg-boot
 * @Date: 2023-08-19
 * @Version: V1.0
 */
public interface IDesignActivityService extends IService<DesignActivity> {

    /**
     * @return 进行中 返回true
     */
    boolean checkActivityStatus();

    /**
     * 获取正在报名的活动
     *
     * @return
     */
    List<DesignActivity> getActivityBy(Integer activityStatus, Integer scoreStatus, Integer topStatus);

    DesignActivity getActivity();

    /**
     * 获取当前活动 top100未生成的活动 top100已生成，则活动结束
     *
     * @return
     */
    DesignActivity getNowActivity();


    /**
     * 打分状态为结束时，进到评委账号，不显示数据，直接提示：评委暂时无法打分，请联系管理员
     * <p>
     * * 获取评委打分数据
     * 获取未启动top100 且打分状态不等于2的，如果有，说明有可以评委打分的活动，可以进行打分
     *
     * @param scoreStatus 打分状态 0：未开始 1：打分开始 2打分结束
     * @param topStatus   未启动top100 0
     * @return
     */
    List<DesignActivity> getScoreActivity(int scoreStatus, int topStatus);

}
