package org.jeecg.modules.gooddesign.controller;

import java.util.*;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jeecg.weibo.exception.BusinessException;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.modules.gooddesign.entity.DesignActivity;
import org.jeecg.modules.gooddesign.entity.DesignEnrollJudges;
import org.jeecg.modules.gooddesign.entity.DesignEnrollParticipants;
import org.jeecg.modules.gooddesign.entity.DesignEnrollParticipantsScoreVO;
import org.jeecg.modules.gooddesign.entity.vo.DesignEnrollParticipantsSaveEditVO;
import org.jeecg.modules.gooddesign.entity.vo.DesignEnrollScoreVO;
import org.jeecg.modules.gooddesign.entity.vo.DesignJudgesParticipantsVO;
import org.jeecg.modules.gooddesign.service.IDesignActivityService;
import org.jeecg.modules.gooddesign.service.IDesignEnrollJudgesService;
import org.jeecg.modules.gooddesign.service.IDesignEnrollParticipantsScoreService;
import org.jeecg.modules.gooddesign.service.IDesignTopJudgesParticipantsService;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;


import org.jeecg.common.system.base.controller.JeecgController;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jeecg.common.aspect.annotation.AutoLog;

/**
 * @Description: 评委通过表，保存评委评分数据
 * @Author: jeecg-boot
 * @Date: 2023-08-17
 * @Version: V1.0
 */
@Api(tags = "好设计-报名-评委打分")
@RestController
@RequestMapping("/designJudgesParticipants")
@Slf4j
public class DesignTopJudgesParticipantsController extends JeecgController<DesignEnrollParticipants, IDesignTopJudgesParticipantsService> {
    @Autowired
    IDesignActivityService designActivityService;
    @Autowired
    IDesignEnrollJudgesService designEnrollJudgesService;
    @Autowired
    IDesignEnrollParticipantsScoreService designJudgesParticipants;
    @Autowired
    private IDesignTopJudgesParticipantsService designJudgesParticipantsService;

    /**
     * 分页列表查询
     *
     * @param designJudgesParticipants
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */
    //@AutoLog(value = "评委通过表，保存评委评分数据-分页列表查询")
    @ApiOperation(value = "评委打分-分页列表查询", notes = "评委打分-分页列表查询")
    @GetMapping(value = "/list")
    public Result<IPage<DesignEnrollParticipants>> queryPageList(DesignEnrollParticipants designJudgesParticipants,
                                                                 @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                 @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                                 HttpServletRequest req) {
        QueryWrapper<DesignEnrollParticipants> queryWrapper = QueryGenerator.initQueryWrapper(designJudgesParticipants, req.getParameterMap());
        Page<DesignEnrollParticipants> page = new Page<DesignEnrollParticipants>(pageNo, pageSize);
        IPage<DesignEnrollParticipants> pageList = designJudgesParticipantsService.page(page, queryWrapper);
        return Result.OK(pageList);
    }

    @ApiOperation(value = "推荐委员评选-根据姓名、评分状态分页查询", notes = "推荐委员评选-根据姓名、评分状态分页查询，只获取评委自己名下数据")
    @GetMapping(value = "/pageByNameAndScoreStatus")
    public Result<IPage<DesignEnrollParticipantsScoreVO>> pageByNameAndScoreStatus(@RequestParam(value = "realName", required = false) String realName,
                                                                                   @RequestParam(value = "designNo", required = false) @ApiParam("报奖编号模糊查询") String designNo,
                                                                                   @RequestParam(value = "scoreStatus", required = false) @ApiParam("打分状态 0待定  1推荐 2不推荐 3 未打分,查询待定和未打分，传0和3") Integer scoreStatus,
                                                                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                                                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                                                   HttpServletRequest req) {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (sysUser == null || sysUser.getId() == null) {
            throw new BusinessException("未获取到用户信息，请重新登录！");
        }
        List<Integer> list = null;
        if (scoreStatus != null) {
            list = new ArrayList<>();
            list.add(scoreStatus);
        }
        Page<DesignEnrollParticipantsScoreVO> page = new Page<DesignEnrollParticipantsScoreVO>(pageNo, pageSize);
        return Result.OK(designJudgesParticipants.pageByNameAndScoreStatus(page, realName, list, sysUser.getId(),designNo));
    }


    @ApiOperation(value = "推荐委员评选-开始打分", notes = "推荐委员评选-开始打分,，获取第一个待定或未打分的数据")
    @GetMapping(value = "/doStartScore")
    public Result<DesignEnrollParticipantsScoreVO> doStartScore() {
        LoginUser sysUser = (LoginUser) SecurityUtils.getSubject().getPrincipal();
        if (sysUser == null || sysUser.getId() == null) {
            throw new BusinessException("未获取到用户信息，请重新登录！");
        }
        return Result.OK(designJudgesParticipants.doStartScore(sysUser.getId()));
    }


    /**
     * 添加
     *
     * @param
     * @return
     */
    @AutoLog(value = "评委通过表，保存评委评分数据-添加")
    @ApiOperation(value = "评委通过表，保存评委评分数据-添加", notes = "评委通过表，保存评委评分数据-添加")
    //@RequiresPermissions("gooddesign:design_judges_participants:add")
    @PostMapping(value = "/add")
    public Result<String> add(@RequestBody DesignJudgesParticipantsVO designJudgesParticipantsVO) {

        designJudgesParticipantsService.add(designJudgesParticipantsVO);
        return Result.OK("添加成功！");
    }

    @AutoLog(value = "评委通过表，保存评委评分数据-批量添加")
    @ApiOperation(value = "评委评分数据-批量添加", notes = "评委评分数据-批量添加")
    //@RequiresPermissions("gooddesign:design_judges_participants:add")
    @PostMapping(value = "/batchAdd")
    public Result<String> batchAdd(@RequestBody List<DesignJudgesParticipantsVO> designJudgesParticipantsVOs) {
        designJudgesParticipantsService.batchAdd(designJudgesParticipantsVOs);
        return Result.OK("添加成功！");
    }


    /**
     * 编辑
     *
     * @param
     * @return
     */
    @AutoLog(value = "评委通过表，保存评委评分数据-编辑")
    @ApiOperation(value = "评委通过表，保存评委评分数据-编辑", notes = "评委通过表，保存评委评分数据-编辑")
    //@RequiresPermissions("gooddesign:design_judges_participants:edit")
    @RequestMapping(value = "/edit", method = {RequestMethod.POST})
    public Result<String> edit(@RequestBody DesignJudgesParticipantsVO designJudgesParticipantsVO) {

        designJudgesParticipantsService.edit(designJudgesParticipantsVO);
        return Result.OK("编辑成功!");
    }

    @AutoLog(value = "评委通过表，保存评委评分数据-批量添加保存编辑")
    @ApiOperation(value = "评委通过表，保存评委评分数据-批量添加保存编辑", notes = "评委通过表，保存评委评分数据-批量添加保存编辑")
    //@RequiresPermissions("gooddesign:design_judges_participants:edit")
    @RequestMapping(value = "/batchEdit", method = {RequestMethod.POST})
    public Result<String> batchEdit(@RequestBody List<DesignEnrollParticipantsSaveEditVO> designJudgesParticipantsVOs) {
        designJudgesParticipantsService.batchEdit(designJudgesParticipantsVOs);
        return Result.OK("编辑成功!");
    }


    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    @AutoLog(value = "评委通过表，保存评委评分数据-通过id删除")
    @ApiOperation(value = "评委通过表，保存评委评分数据-通过id删除", notes = "评委通过表，保存评委评分数据-通过id删除")
    //@RequiresPermissions("gooddesign:design_judges_participants:delete")
    @DeleteMapping(value = "/delete")
    public Result<String> delete(@RequestParam(name = "id", required = true) String id) {
        designJudgesParticipantsService.removeById(id);
        return Result.OK("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @AutoLog(value = "评委通过表，保存评委评分数据-批量删除")
    @ApiOperation(value = "评委通过表，保存评委评分数据-批量删除", notes = "评委通过表，保存评委评分数据-批量删除")
    //@RequiresPermissions("gooddesign:design_judges_participants:deleteBatch")
    @DeleteMapping(value = "/deleteBatch")
    public Result<String> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.designJudgesParticipantsService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.OK("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */
    //@AutoLog(value = "评委通过表，保存评委评分数据-通过id查询")
    @ApiOperation(value = "评委通过表，保存评委评分数据-通过id查询", notes = "评委通过表，保存评委评分数据-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<DesignJudgesParticipantsVO> queryById(@RequestParam(name = "id", required = true) String id) {
        DesignEnrollParticipants designJudgesParticipants = designJudgesParticipantsService.getById(id);
        if (designJudgesParticipants == null) {
            return Result.error("未找到对应数据");
        }
        DesignJudgesParticipantsVO designJudgesParticipantsVO = new DesignJudgesParticipantsVO();
        BeanUtils.copyProperties(designJudgesParticipants, designJudgesParticipantsVO);
        return Result.OK(designJudgesParticipantsVO);
    }

    /**
     * 导出excel
     *
     * @param request
     * @param designJudgesParticipants
     */
    //@RequiresPermissions("gooddesign:design_judges_participants:exportXls")
    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, DesignEnrollParticipants designJudgesParticipants) {
        return super.exportXls(request, designJudgesParticipants, DesignEnrollParticipants.class, "评委通过表，保存评委评分数据");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */
    //@RequiresPermissions("gooddesign:design_judges_participants:importExcel")
    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, DesignEnrollParticipants.class);
    }

    @ApiOperation(value = "好设计-报名-查询所有初审通过的设计师集合列表", notes = "好设计-报名-查询所有初审通过的设计师集合")
    @GetMapping(value = "/listScreenEnroll")
    public Result<List<DesignEnrollScoreVO>> listEnroll(@RequestParam(name = "userId") @ApiParam("评委ID") String juedgsId,
                                                        @RequestParam(name = "screenStatus", defaultValue = "1") @ApiParam("筛选状态 通过1 不通过0") Integer screenStatus,
//                                                        @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
//                                                        @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                                        HttpServletRequest req) {

        DesignActivity activity = designActivityService.getActivity();
        if (activity == null || activity.getId() == null) {
            return Result.error("未获取到当前活动ID");
        }
        Integer activityId = activity.getId();
        //初筛通过数据
        QueryWrapper<DesignEnrollJudges> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("activity_id", activityId);
        queryWrapper.eq("screen_status", screenStatus);
        queryWrapper.orderByAsc("id");


//        Page<DesignEnrollJudges> page = new Page<DesignEnrollJudges>(pageNo, pageSize);
//        IPage<DesignEnrollJudges> pageList = designEnrollJudgesService.page(page, queryWrapper);
//        List<DesignEnrollJudges> records = pageList.getRecords();
        List<DesignEnrollJudges> records = designEnrollJudgesService.list(queryWrapper);

        //评分数据
        QueryWrapper<DesignEnrollParticipants> participantsQueryWrapper = new QueryWrapper<>();
        //评委已评分的数据
        participantsQueryWrapper.eq("judge_id", juedgsId);
        participantsQueryWrapper.eq("activity_id", activityId);
        List<DesignEnrollParticipants> designEnrollParticipants = designJudgesParticipantsService.list(participantsQueryWrapper);

        if (CollectionUtils.isEmpty(designEnrollParticipants)) {
            designEnrollParticipants = new ArrayList<>();
        }
        Map<Integer, Integer> scoreStatusMap = designEnrollParticipants.stream().collect(Collectors.toMap(DesignEnrollParticipants::getParticipantId, DesignEnrollParticipants::getScoreStatus));

        if (CollectionUtils.isEmpty(records)) {
            return Result.error("未查询到通过初筛的数据！");
        }
        List<DesignEnrollScoreVO> result = records.stream().map(data -> {
            DesignEnrollScoreVO designEnrollScoreVO = new DesignEnrollScoreVO();
            BeanUtils.copyProperties(data, designEnrollScoreVO);
            designEnrollScoreVO.setScoreStatus(scoreStatusMap.getOrDefault(data.getId(), 3));
            return designEnrollScoreVO;
        }).collect(Collectors.toList());

        return Result.OK(result);
    }


}
