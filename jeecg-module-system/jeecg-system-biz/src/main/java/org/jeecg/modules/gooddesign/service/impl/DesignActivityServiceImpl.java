package org.jeecg.modules.gooddesign.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.gooddesign.entity.DesignActivity;
import org.jeecg.modules.gooddesign.mapper.DesignActivityMapper;
import org.jeecg.modules.gooddesign.service.IDesignActivityService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 好设计-跨年启停
 * @Author: jeecg-boot
 * @Date: 2023-08-19
 * @Version: V1.0
 */
@Service
public class DesignActivityServiceImpl extends ServiceImpl<DesignActivityMapper, DesignActivity> implements IDesignActivityService {
    @Override
    public boolean checkActivityStatus() {
        QueryWrapper<DesignActivity> queryWrapper = new QueryWrapper();
        queryWrapper.eq("activity_status", 1);
        List<DesignActivity> list = this.list(queryWrapper);
        if (list == null || list.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public List<DesignActivity> getActivityBy(Integer activityStatus, Integer scoreStatus, Integer topStatus) {
        QueryWrapper<DesignActivity> queryWrapper = new QueryWrapper();
        if (activityStatus != null) {
            queryWrapper.eq("activity_status", activityStatus);
        }
        if (scoreStatus != null) {
            queryWrapper.eq("score_status", scoreStatus);
        }
        if (topStatus != null) {
            queryWrapper.eq("top_status", topStatus);
        }
        List<DesignActivity> list = this.list(queryWrapper);
        return list;
    }

    @Override
    public DesignActivity getActivity() {
        QueryWrapper<DesignActivity> queryWrapper = new QueryWrapper();
        queryWrapper.eq("activity_status", 1);
        List<DesignActivity> list = this.list(queryWrapper);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public DesignActivity getNowActivity() {
        QueryWrapper<DesignActivity> queryWrapper = new QueryWrapper();
        queryWrapper.eq("top_status", 0);
        List<DesignActivity> list = this.list(queryWrapper);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }


    @Override
    public List<DesignActivity> getScoreActivity(int scoreStatus, int topStatus) {
        QueryWrapper<DesignActivity> queryWrapper = new QueryWrapper();
        queryWrapper.ne("score_status", scoreStatus);
        queryWrapper.eq("top_status", topStatus);
        List<DesignActivity> list = this.list(queryWrapper);
        return list;
    }
}
