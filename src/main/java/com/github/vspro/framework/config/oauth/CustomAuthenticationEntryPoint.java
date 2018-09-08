package com.github.vspro.framework.config.oauth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created  on 2018/9/8.
 * 如果直接访问API地址，可以实现AuthenticationEntryPoint接口
 * 重定向或者返回401 unAuthorized
 */

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);
    private static final String DENY_PAGE = "/accessDeny";


    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        LOGGER.info("Pre-authenticated entry point called. Rejecting access");
//        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied or Something wrong with the api");
        response.sendRedirect(DENY_PAGE);
    }
}
