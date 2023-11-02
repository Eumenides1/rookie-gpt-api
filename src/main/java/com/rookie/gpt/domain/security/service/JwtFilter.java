package com.rookie.gpt.domain.security.service;

import com.rookie.gpt.domain.security.model.vo.JwtToken;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Jaguarliu
 * @description 自定义Filter，用于拦截携带Token的请求
 */
public class JwtFilter extends AccessControlFilter {

    private Logger logger = LoggerFactory.getLogger(JwtFilter.class);

    /**
     * isAccessAllowed 判断是否携带有效的 JwtToken
     * 所以这里直接返回一个 false，让它走 onAccessDenied 方法流程
     */

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    /**
     * 返回结果为true表明登录通过
     */

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        // 如果你设定的 token 放到 param 中，则可以这样获取；request.getParameter("token")
        JwtToken jwtToken = new JwtToken(request.getHeader("Authorization"));

        try {
            getSubject(servletRequest, servletResponse).login(jwtToken);
            return true;
        } catch (Exception e) {
            logger.error("鉴权认证失败", e);
            onLoginFail(servletResponse);
            return false;
        }
    }

    private void onLoginFail(ServletResponse response) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.getWriter().write("Auth Err!");
    }


}
