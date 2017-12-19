package com.zbw.fame.interceptor;

import com.zbw.fame.model.Users;
import com.zbw.fame.util.ErrorCode;
import com.zbw.fame.util.FameConsts;
import com.zbw.fame.util.FameUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Fame 拦截器
 *
 * @author zbw
 * @create 2017/10/11 14:10
 */
@Component
public class FameInterceptor implements HandlerInterceptor {

    private static final String AUTH_URIS = "/admin";

    private static final String[] IGNORE_URIS = {"/admin/login"};

    private static final Logger logger = LoggerFactory.getLogger(FameInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String url = request.getRequestURI();
        String ip = FameUtil.getIp();

        logger.info("用户访问地址: {}, ip地址: {}", url, ip);

        if (url.contains(AUTH_URIS)) {
            boolean auth = true;
            //登录拦截忽略url
            for (String param : IGNORE_URIS) {
                if (StringUtils.endsWithIgnoreCase(url, param)) {
                    auth = false;
                }
            }
            //登录拦截
            if (auth) {
                Users user = (Users) request.getSession().getAttribute(FameConsts.USER_SESSION_KEY);
                if (null == user) {
                    // 要设置跨域，不然输出信息没有
                    if (request.getHeader(HttpHeaders.ORIGIN) != null) {
                        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, request.getHeader(HttpHeaders.ORIGIN));
                        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, "true");
                        response.setHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, "POST, GET, OPTIONS, DELETE");
                        response.setHeader(HttpHeaders.ACCESS_CONTROL_MAX_AGE, "3600");
                        response.setHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "x-requested-with");
                    }
                    response.setStatus(ErrorCode.NOT_LOGIN.getCode());
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception e) throws Exception {

    }
}
