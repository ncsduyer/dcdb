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
package cn.stylefeng.guns.core.interceptor;

import cn.stylefeng.guns.core.common.constant.JwtConstants;
import cn.stylefeng.guns.core.common.exception.BizExceptionEnum;
import cn.stylefeng.guns.core.log.LogManager;
import cn.stylefeng.guns.core.log.factory.LogTaskFactory;
import cn.stylefeng.guns.core.shiro.ShiroKit;
import cn.stylefeng.guns.core.shiro.ShiroUser;
import cn.stylefeng.guns.core.util.CacheUtil;
import cn.stylefeng.guns.core.util.JwtTokenUtil;
import cn.stylefeng.roses.core.reqres.response.ErrorResponseData;
import cn.stylefeng.roses.core.util.RenderUtil;
import cn.stylefeng.roses.core.util.ToolUtil;
import io.jsonwebtoken.JwtException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static cn.stylefeng.roses.core.util.HttpContext.getIp;


/**
 * Rest Api接口鉴权
 *
 * @author stylefeng
 * @Date 2018/7/20 23:11
 */
public class RestApiInteceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof org.springframework.web.servlet.resource.ResourceHttpRequestHandler) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        return check(request, response, handlerMethod);
    }

    private boolean check(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) {

        //获取开始时间
        long start=System.currentTimeMillis();
//        String url=request.getServletPath();
        if (request.getServletPath().equals(JwtConstants.AUTH_PATH)) {
            return true;
        }
//        System.out.println(url.substring(0, ValidateUtils.getFromIndex(url,"/",3)));

//        if (Arrays.asList(JwtConstants.ANON_PATH).contains(url.substring(0,ValidateUtils.getFromIndex(url,"/",3)))) {
//            return true;
//        }
        final String requestHeader = request.getHeader(JwtConstants.AUTH_HEADER);
        String authToken;
        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
            authToken = requestHeader.substring(7);

            //验证token是否过期,包含了验证jwt是否正确
            try {
                boolean flag = JwtTokenUtil.isTokenExpired(authToken);
                if (flag) {
                    RenderUtil.renderJson(response, new ErrorResponseData(BizExceptionEnum.TOKEN_EXPIRED.getCode(), BizExceptionEnum.TOKEN_EXPIRED.getMessage()));
                    return false;
                }
            } catch (JwtException e) {
                //有异常就是token解析失败
                RenderUtil.renderJson(response, new ErrorResponseData(BizExceptionEnum.TOKEN_ERROR.getCode(), BizExceptionEnum.TOKEN_ERROR.getMessage()));
                return false;
            }
        } else {
            //header没有带Bearer字段
            RenderUtil.renderJson(response, new ErrorResponseData(BizExceptionEnum.TOKEN_ERROR.getCode(), BizExceptionEnum.TOKEN_ERROR.getMessage()));
            return false;
        }
        UsernamePasswordToken usernamePasswordToken = CacheUtil.get("userInfo", authToken);
        if (!ToolUtil.isEmpty(usernamePasswordToken)) {
            Subject currentUser = ShiroKit.getSubject();
            currentUser.login(usernamePasswordToken);
            if (currentUser.isAuthenticated()){
                CacheUtil.put("userInfo", authToken, usernamePasswordToken);
            }
            ShiroUser shiroUser = ShiroKit.getUser();
            ShiroKit.getSession().setAttribute("shiroUser", shiroUser);
            ShiroKit.getSession().setAttribute("username", shiroUser.getAccount());

            LogManager.me().executeLog(LogTaskFactory.loginLog(shiroUser.getId(), getIp()));

            ShiroKit.getSession().setAttribute("sessionFlag", true);
        } else {
            RenderUtil.renderJson(response, new ErrorResponseData(700, "token错误！请重新获取token"));
            return false;
        }

        //获取结束时间
        long end=System.currentTimeMillis();

        System.out.println("token认证程序运行时间： "+(end-start)+"ms");

        return true;
    }

}
