package org.jeecg.modules.gooddesign.controller;

import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.exceptions.ClientException;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.constant.CacheConstant;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.constant.SymbolConstant;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.system.vo.LoginUser;
import org.jeecg.common.util.*;
import org.jeecg.common.util.encryption.EncryptedString;
import org.jeecg.config.JeecgBaseConfig;
import org.jeecg.modules.base.service.BaseCommonService;
import org.jeecg.modules.system.entity.SysDepart;
import org.jeecg.modules.system.entity.SysRoleIndex;
import org.jeecg.modules.system.entity.SysUser;
import org.jeecg.modules.system.model.SysLoginModel;
import org.jeecg.modules.system.service.*;
import org.jeecg.modules.system.service.impl.SysBaseApiImpl;
import org.jeecg.modules.system.util.RandImageUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Author scott
 * @since 2018-12-17
 */
@RestController
@RequestMapping("/design/sys")
@Api(tags = "好设计-用户登录")
@Slf4j
public class DesignLoginController {
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysPermissionService sysPermissionService;
    @Autowired
    private SysBaseApiImpl sysBaseApi;
    @Autowired
    private ISysLogService logService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private ISysDepartService sysDepartService;
    @Autowired
    private ISysTenantService sysTenantService;
    @Autowired
    private ISysDictService sysDictService;
    @Resource
    private BaseCommonService baseCommonService;

    @Autowired
    private JeecgBaseConfig jeecgBaseConfig;

    private final String BASE_CHECK_CODES = "qwertyuiplkjhgfdsazxcvbnmQWERTYUPLKJHGFDSAZXCVBNM1234567890";

    @ApiOperation("登录接口")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result<JSONObject> login(@RequestBody SysLoginModel sysLoginModel) {
        Result<JSONObject> result = new Result<JSONObject>();
        String username = sysLoginModel.getUsername();
        String password = sysLoginModel.getPassword();
        //update-begin-author:taoyan date:2022-11-7 for: issues/4109 平台用户登录失败锁定用户
        if (isLoginFailOvertimes(username)) {
            return result.error500("该用户登录失败次数过多，请于10分钟后再次登录！");
        }
        //update-end-author:taoyan date:2022-11-7 for: issues/4109 平台用户登录失败锁定用户
        //update-begin--Author:scott  Date:20190805 for：暂时注释掉密码加密逻辑，有点问题
        //前端密码加密，后端进行密码解密
        //password = AesEncryptUtil.desEncrypt(sysLoginModel.getPassword().replaceAll("%2B", "\\+")).trim();//密码解密
        //update-begin--Author:scott  Date:20190805 for：暂时注释掉密码加密逻辑，有点问题

        //update-begin-author:taoyan date:20190828 for:校验验证码
//        String captcha = sysLoginModel.getCaptcha();
//        if(captcha==null){
//            result.error500("验证码无效");
//            return result;
//        }
//        String lowerCaseCaptcha = captcha.toLowerCase();
        //update-begin-author:taoyan date:2022-9-13 for: VUEN-2245 【漏洞】发现新漏洞待处理20220906
        // 加入密钥作为混淆，避免简单的拼接，被外部利用，用户自定义该密钥即可
//        String origin = lowerCaseCaptcha+sysLoginModel.getCheckKey()+jeecgBaseConfig.getSignatureSecret();
//		String realKey = Md5Util.md5Encode(origin, "utf-8");
//		//update-end-author:taoyan date:2022-9-13 for: VUEN-2245 【漏洞】发现新漏洞待处理20220906
//		Object checkCode = redisUtil.get(realKey);
//		//当进入登录页时，有一定几率出现验证码错误 #1714
//		if(checkCode==null || !checkCode.toString().equals(lowerCaseCaptcha)) {
//            log.warn("验证码错误，key= {} , Ui checkCode= {}, Redis checkCode = {}", sysLoginModel.getCheckKey(), lowerCaseCaptcha, checkCode);
//			result.error500("验证码错误");
//			// 改成特殊的code 便于前端判断
//			result.setCode(HttpStatus.PRECONDITION_FAILED.value());
//			return result;
//		}
        //update-end-author:taoyan date:20190828 for:校验验证码

        //1. 校验用户是否有效
        //update-begin-author:wangshuai date:20200601 for: 登录代码验证用户是否注销bug，if条件永远为false
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getUsername, username);
        SysUser sysUser = sysUserService.getOne(queryWrapper);
        //update-end-author:wangshuai date:20200601 for: 登录代码验证用户是否注销bug，if条件永远为false
        result = sysUserService.checkUserIsEffective(sysUser);
        if (!result.isSuccess()) {
            return result;
        }

        //2. 校验用户名或密码是否正确
        String userpassword = PasswordUtil.encrypt(username, password, sysUser.getSalt());
        String syspassword = sysUser.getPassword();
        if (!syspassword.equals(userpassword)) {
            //update-begin-author:taoyan date:2022-11-7 for: issues/4109 平台用户登录失败锁定用户
            addLoginFailOvertimes(username);
            //update-end-author:taoyan date:2022-11-7 for: issues/4109 平台用户登录失败锁定用户
            result.error500("用户名或密码错误");
            return result;
        }

        //用户登录信息
        userInfo(sysUser, result);
        //update-begin--Author:liusq  Date:20210126  for：登录成功，删除redis中的验证码
//		redisUtil.del(realKey);
        //update-begin--Author:liusq  Date:20210126  for：登录成功，删除redis中的验证码
        redisUtil.del(CommonConstant.LOGIN_FAIL + username);
        LoginUser loginUser = new LoginUser();
        BeanUtils.copyProperties(sysUser, loginUser);
        baseCommonService.addLog("用户名: " + username + ",登录成功！", CommonConstant.LOG_TYPE_1, null, loginUser);
        //update-end--Author:wangshuai  Date:20200714  for：登录日志没有记录人员
        return result;
    }
    /**
     * 用户信息
     *
     * @param sysUser
     * @param result
     * @return
     */
    private Result<JSONObject> userInfo(SysUser sysUser, Result<JSONObject> result) {
        String username = sysUser.getUsername();
        String syspassword = sysUser.getPassword();
        // 获取用户部门信息
        JSONObject obj = new JSONObject(new LinkedHashMap<>());

        //1.生成token
        String token = JwtUtil.sign(username, syspassword);
        // 设置token缓存有效时间
        redisUtil.set(CommonConstant.PREFIX_USER_TOKEN + token, token);
        redisUtil.expire(CommonConstant.PREFIX_USER_TOKEN + token, JwtUtil.EXPIRE_TIME * 2 / 1000);
        obj.put("token", token);

        //2.设置登录租户
        Result<JSONObject> loginTenantError = sysUserService.setLoginTenant(sysUser, obj, username,result);
        if (loginTenantError != null) {
            return loginTenantError;
        }

        //3.设置登录用户信息
        obj.put("userInfo", sysUser);

        //4.设置登录部门
        List<SysDepart> departs = sysDepartService.queryUserDeparts(sysUser.getId());
        obj.put("departs", departs);
        if (departs == null || departs.size() == 0) {
            obj.put("multi_depart", 0);
        } else if (departs.size() == 1) {
            sysUserService.updateUserDepart(username, departs.get(0).getOrgCode(),null);
            obj.put("multi_depart", 1);
        } else {
            //查询当前是否有登录部门
            // update-begin--Author:wangshuai Date:20200805 for：如果用戶为选择部门，数据库为存在上一次登录部门，则取一条存进去
            SysUser sysUserById = sysUserService.getById(sysUser.getId());
            if(oConvertUtils.isEmpty(sysUserById.getOrgCode())){
                sysUserService.updateUserDepart(username, departs.get(0).getOrgCode(),null);
            }
            // update-end--Author:wangshuai Date:20200805 for：如果用戶为选择部门，数据库为存在上一次登录部门，则取一条存进去
            obj.put("multi_depart", 2);
        }
        obj.put("sysAllDictItems", sysDictService.queryAllDictItems());
        result.setResult(obj);
        result.success("登录成功");
        return result;
    }



    /**
     * 退出登录
     *
     * @param request
     * @param response
     * @return
     */
    @ApiOperation("登出接口")
    @RequestMapping(value = "/logout")
    public Result<Object> logout(HttpServletRequest request, HttpServletResponse response) {
        //用户退出逻辑
        String token = request.getHeader(CommonConstant.X_ACCESS_TOKEN);
        if (oConvertUtils.isEmpty(token)) {
            return Result.error("退出登录失败！");
        }
        String username = JwtUtil.getUsername(token);
        LoginUser sysUser = sysBaseApi.getUserByName(username);
        if (sysUser != null) {
            //update-begin--Author:wangshuai  Date:20200714  for：登出日志没有记录人员
            baseCommonService.addLog("用户名: " + sysUser.getRealname() + ",退出成功！", CommonConstant.LOG_TYPE_1, null, sysUser);
            //update-end--Author:wangshuai  Date:20200714  for：登出日志没有记录人员
            log.info(" 用户名:  " + sysUser.getRealname() + ",退出成功！ ");
            //清空用户登录Token缓存
            redisUtil.del(CommonConstant.PREFIX_USER_TOKEN + token);
            //清空用户登录Shiro权限缓存
            redisUtil.del(CommonConstant.PREFIX_USER_SHIRO_CACHE + sysUser.getId());
            //清空用户的缓存信息（包括部门信息），例如sys:cache:user::<username>
            redisUtil.del(String.format("%s::%s", CacheConstant.SYS_USERS_CACHE, sysUser.getUsername()));
            //调用shiro的logout
            SecurityUtils.getSubject().logout();
            return Result.ok("退出登录成功！");
        } else {
            return Result.error("Token无效!");
        }
    }



    @ApiOperation("获取验证码")
    @GetMapping(value = "/sms")
    public Result<String> smsLogin(@RequestParam @ApiParam("手机号") String mobile, @RequestParam @ApiParam("模板 1注册 2登录") String smsmode) {
        Result<String> result = new Result<String>();
        //手机号模式 登录模式: "2"  注册模式: "1"
        log.info(mobile);
        if (oConvertUtils.isEmpty(mobile)) {
            result.setMessage("手机号不允许为空！");
            result.setSuccess(false);
            return result;
        }

        //update-begin-author:taoyan date:2022-9-13 for: VUEN-2245 【漏洞】发现新漏洞待处理20220906
        String redisKey = CommonConstant.PHONE_REDIS_KEY_PRE + mobile;
        Object object = redisUtil.get(redisKey);
        //update-end-author:taoyan date:2022-9-13 for: VUEN-2245 【漏洞】发现新漏洞待处理20220906

        if (object != null) {
            result.setMessage("验证码10分钟内，仍然有效！");
            result.setSuccess(false);
            return result;
        }

        //随机数
        String captcha = RandomUtil.randomNumbers(6);
        JSONObject obj = new JSONObject();
        obj.put("code", captcha);
        try {
            boolean b = DySmsHelper.sendSms(mobile, obj, DySmsEnum.DESIGN_LOGIN);
//            boolean b = DySmsHelper.sendSms(mobile, obj, DySmsEnum.DESIGN_LOGIN);
            if (b == false) {
                result.setMessage("短信验证码发送失败,请稍后重试");
                result.setSuccess(false);
                return result;
            }
            //update-begin-author:taoyan date:2022-9-13 for: VUEN-2245 【漏洞】发现新漏洞待处理20220906
            //验证码10分钟内有效
            redisUtil.set(redisKey, captcha, 600);
            //update-end-author:taoyan date:2022-9-13 for: VUEN-2245 【漏洞】发现新漏洞待处理20220906

            //update-begin--Author:scott  Date:20190812 for：issues#391
            //result.setResult(captcha);
            //update-end--Author:scott  Date:20190812 for：issues#391
            result.setSuccess(true);
        } catch (ClientException e) {
            e.printStackTrace();
            result.error500(" 短信接口未配置，请联系管理员！");
            return result;
        }
        return result;
    }


    /**
     * 手机号登录接口
     *
     * @return
     */
    @ApiOperation("手机号登录接口")
    @GetMapping("/phoneLogin")
    public Result<JSONObject> phoneLogin(@RequestParam @ApiParam("手机号") String phone, @RequestParam @ApiParam("验证码") String captcha) {
        Result<JSONObject> result = new Result<JSONObject>();
        //update-begin-author:taoyan date:2022-11-7 for: issues/4109 平台用户登录失败锁定用户
        if (isLoginFailOvertimes(phone)) {
            return result.error500("该用户登录失败次数过多，请于10分钟后再次登录！");
        }
        //update-end-author:taoyan date:2022-11-7 for: issues/4109 平台用户登录失败锁定用户


        //update-begin-author:taoyan date:2022-9-13 for: VUEN-2245 【漏洞】发现新漏洞待处理20220906
        String redisKey = CommonConstant.PHONE_REDIS_KEY_PRE + phone;
        Object code = redisUtil.get(redisKey);
        //update-end-author:taoyan date:2022-9-13 for: VUEN-2245 【漏洞】发现新漏洞待处理20220906

        if (!captcha.equals(code)) {
            //update-begin-author:taoyan date:2022-11-7 for: issues/4109 平台用户登录失败锁定用户
            addLoginFailOvertimes(phone);
            //update-end-author:taoyan date:2022-11-7 for: issues/4109 平台用户登录失败锁定用户
            return Result.error(0,"手机验证码错误");
        }

        //添加日志
        baseCommonService.addLog("用户名: " + phone + ",手机号登录成功！", CommonConstant.LOG_TYPE_1, null);

        return Result.OK();
    }

    /**
     * 登录失败超出次数5 返回true
     * @param username
     * @return
     */
    private boolean isLoginFailOvertimes(String username){
        String key = CommonConstant.LOGIN_FAIL + username;
        Object failTime = redisUtil.get(key);
        if(failTime!=null){
            Integer val = Integer.parseInt(failTime.toString());
            if(val>5){
                return true;
            }
        }
        return false;
    }
    /**
     * 记录登录失败次数
     * @param username
     */
    private void addLoginFailOvertimes(String username){
        String key = CommonConstant.LOGIN_FAIL + username;
        Object failTime = redisUtil.get(key);
        Integer val = 0;
        if(failTime!=null){
            val = Integer.parseInt(failTime.toString());
        }
        // 1小时
        redisUtil.set(key, ++val, 3600);
    }

}