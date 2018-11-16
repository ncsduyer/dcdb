/**
 * Copyright 2018-2020 stylefeng & fengshuonan (https://gitee.com/stylefeng)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.stylefeng.guns.modular.api;

import cn.stylefeng.guns.core.common.constant.JwtConstants;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.log.LogManager;
import cn.stylefeng.guns.core.log.factory.LogTaskFactory;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.shiro.ShiroUser;
import cn.stylefeng.guns.core.util.CacheUtil;
import cn.stylefeng.guns.core.util.JwtTokenUtil;
import cn.stylefeng.guns.modular.VersionUpgrade.service.IVersionUpgradeService;
import cn.stylefeng.guns.modular.system.dao.UserMapper;
import cn.stylefeng.guns.modular.system.model.Menu;
import cn.stylefeng.guns.modular.system.model.User;
import cn.stylefeng.guns.modular.system.service.IMenuService;
import cn.stylefeng.guns.modular.system.service.IUserService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.reqres.response.ResponseData;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.mapper.Condition;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.*;

import static cn.stylefeng.roses.core.util.HttpContext.getIp;

/**
 * 接口控制器提供
 *
 * @author stylefeng
 * @Date 2018/7/20 23:39
 */
@RestController
@RequestMapping("/api")
@Api(value = "app api集合", tags = "app 通用外层api集合")
public class ApiController extends BaseController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IUserService userService;
    @Autowired
    private IMenuService menuService;
    @Autowired
    private IVersionUpgradeService versionUpgradeService;
    @Autowired
    private HttpServletRequest request;
    /**
     * api登录接口，通过账号密码获取token
     */
    @ApiOperation(value = "api登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, dataType = "String"),
    })
    @RequestMapping(value = "/login", method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseData auth(@RequestParam("username") String username,
                       @RequestParam("password") String password) {

        //封装请求账号密码为shiro可验证的token
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password.toCharArray());

        //获取数据库中的账号密码，准备比对
        User user = userMapper.getByAccount(username);
        if (ToolUtil.isEmpty(user)) {
            return new ErrorResponseData(5001, "账号密码错误！");
        }
        String credentials = user.getPassword();
        String salt = user.getSalt();
        ByteSource credentialsSalt = new Md5Hash(salt);
        ShiroUser shiroUser = new ShiroUser();
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                shiroUser, credentials, credentialsSalt, "");

        //校验用户账号密码
        HashedCredentialsMatcher md5CredentialsMatcher = new HashedCredentialsMatcher();
        md5CredentialsMatcher.setHashAlgorithmName(ShiroKit.hashAlgorithmName);
        md5CredentialsMatcher.setHashIterations(ShiroKit.hashIterations);
        boolean passwordTrueFlag = md5CredentialsMatcher.doCredentialsMatch(
                usernamePasswordToken, simpleAuthenticationInfo);

        if (passwordTrueFlag) {
            String token = JwtTokenUtil.generateToken(String.valueOf(user.getId()));
            CacheUtil.put("userInfo", token, usernamePasswordToken);
            Map<String, Object> result = new HashMap<>();
            result.put("token", token);
            return ResponseData.success(result);
        } else {
            return new ErrorResponseData(5001, "账号密码错误！");
        }
    }

    /**
     * 退出登录
     */
    @ApiOperation(value = "退出登录")
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseData logOut(HttpServletRequest request) {
        LogManager.me().executeLog(LogTaskFactory.exitLog(ShiroKit.getUser().getId(), getIp()));
        ShiroKit.getSubject().logout();
        //deleteAllCookie();
        final String requestHeader = request.getHeader(JwtConstants.AUTH_HEADER);
        String authToken = requestHeader.substring(7);
        CacheUtil.remove("userInfo", authToken);
        return ResponseData.success(200, "退出登录成功", null);
    }

    /**
     * 修改当前用户的密码
     */
    @ApiOperation(value = "修改密码接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "oldPwd", value = "旧密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "newPwd", value = "新密码", required = true, dataType = "String"),
            @ApiImplicitParam(name = "rePwd", value = "确认新密码", required = true, dataType = "String"),
    })
    @RequestMapping(value = "/repwd", method = RequestMethod.POST)
    @ResponseBody
    public ResponseData repwd(@RequestParam("oldPwd") String oldPwd, @RequestParam("newPwd") String newPwd, @RequestParam("rePwd") String rePwd) {
        if (!newPwd.equals(rePwd)) {
            throw new ServiceException(BizExceptionEnum.TWO_PWD_NOT_MATCH);
        }
        Integer userId = ShiroKit.getUser().getId();
        User user = userService.selectById(userId);
        String oldMd5 = ShiroKit.md5(oldPwd, user.getSalt());
        if (user.getPassword().equals(oldMd5)) {
            String newMd5 = ShiroKit.md5(newPwd, user.getSalt());
            user.setPassword(newMd5);
            user.updateById();
            return SUCCESS_TIP;
        } else {
            return ResponseData.error(BizExceptionEnum.OLD_PWD_NOT_RIGHT.getCode(), BizExceptionEnum.OLD_PWD_NOT_RIGHT.getMessage());
        }
    }

    /**
     * 获取当前登录用户信息
     */
    @ApiOperation(value = "获取当前登录用户信息")
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData user() {
        ShiroUser user = ShiroKit.getUser();
        if (ToolUtil.isEmpty(user)) {
            throw new ServiceException(700, "用户信息异常");
        }
        return ResponseData.success(user);
    }

    /**
     * 获取当前登录用户权限列表
     */
    @ApiOperation(value = "获取当前登录用户信息")
    @RequestMapping(value = "/permissions", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData permissions() {
        ShiroUser user = ShiroKit.getUser();
        if (ToolUtil.isEmpty(user)) {
            return ResponseData.error(700, "用户信息异常");
        }
        List<Integer> roleList = user.getRoleList();
        Set<Menu> permissionSet = new HashSet<>();
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
        for (Integer roleId : roleList) {
            List<Menu> permissions = menuService.getMenuByRoleId(roleId, null);
            if (permissions != null) {
                for (Menu menu : permissions) {
                    if (ToolUtil.isNotEmpty(menu)) {
                        menu.setIcon(url + "/static/img/" + menu.getIcon());
                        menu.setUrl(url + menu.getUrl());
                        permissionSet.add(menu);
                    }
                }
            }
        }
        return ResponseData.success(permissionSet);
    }

    /**
     * 获取最新app版本
     */
    @ApiOperation(value = "获取最新app版本")
    @RequestMapping(value = "/version", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData version() {
//        app版本信息

        return ResponseData.success(versionUpgradeService.selectOne(Condition.create().eq("status", 1)
                .orderBy("id", false)));
    }

    /**
     * 获取app启动页面图片
     */
    @ApiOperation(value = "获取app启动页面图片")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "图片分组id", required = true, dataType = "Long"),
    })
    @RequestMapping(value = "/start/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData startImg(@PathVariable("id") Integer id) {
//        app启动页面图片
        return ResponseData.success();
    }

    /**
     * 获取页面列表
     */
    @ApiOperation(value = "获取页面列表")
    @RequestMapping(value = "/menu", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData menu() {
        ShiroUser user = ShiroKit.getUser();
        if (ToolUtil.isEmpty(user)) {
            return ResponseData.error(700, "用户信息异常");
        }
        List<Integer> roleList = user.getRoleList();
        Set<Menu> permissionSet = new HashSet<>();
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
        for (Integer roleId : roleList) {
            List<Menu> permissions = menuService.getMenuByRoleId(roleId, 1);
            if (permissions != null) {
                for (Menu menu : permissions) {
                    if (ToolUtil.isNotEmpty(menu)) {
                        menu.setIcon(url + "/static/img/" + menu.getIcon());
                        menu.setUrl(url + menu.getUrl());
                        permissionSet.add(menu);
                    }
                }
            }
        }
        return ResponseData.success(permissionSet);
    }

    /**
     * 获取页面信息
     */
    @ApiOperation(value = "获取页面信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "id", required = true, dataType = "Long"),
    })
    @RequestMapping(value = "/menu/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData menu(@PathVariable("id") BigInteger id) {
        if (ToolUtil.isEmpty(id)) {
            return ResponseData.error(BizExceptionEnum.REQUEST_NULL.getCode(), BizExceptionEnum.REQUEST_NULL.getMessage());
        }
        Menu menu = menuService.selectById(id);
        String url = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
        menu.setIcon(url + "/static/img/" + menu.getIcon());
        menu.setUrl(url + menu.getUrl());
        return ResponseData.success(menu);
    }

    /**
     * 获取消息列表
     */
    @ApiOperation(value = "获取消息列表")
    @RequestMapping(value = "/notice", method = RequestMethod.GET)
    @ResponseBody
    public ResponseData notice() {
//        消息列表
        return ResponseData.success();
    }

    /**
     * 获取消息详情
     */
    @ApiOperation(value = "获取消息详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "新闻id", required = true, dataType = "Long"),
    })
    @RequestMapping(value = "/notice/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseData notice(@PathVariable("id") Integer id) {
//        消息列表
        return ResponseData.success();
    }
}

