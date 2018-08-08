package com.shfound.showproduct.controller.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component
//@WebFilter(urlPatterns = {"/user/root/*", "/prod/root/*", "/vote/root/*"}, filterName = "rootAuthorFilter")
public class RootAuthorFilter implements Filter {

    Logger logger = LoggerFactory.getLogger(RootAuthorFilter.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        servletResponse.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("application/json; charset=utf-8");
        String url = ((HttpServletRequest) servletRequest).getRequestURL().toString();
        if (url.endsWith("index.html")) {
            Boolean isLogin = (Boolean) req.getSession().getAttribute("isLogin");
            if (isLogin != null && isLogin) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                ((HttpServletResponse)servletResponse).sendRedirect("login.html");
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
//
//        if (null == token || token.isEmpty() || value.isEmpty()) {
//            res.setCode(2000);
//            res.setMessage("用户身份验证失败，请重新登录");
//
//            PrintWriter writer = null;
//            OutputStreamWriter osw = null;
//            try {
//                osw = new OutputStreamWriter(servletResponse.getOutputStream(), "UTF-8");
//                writer = new PrintWriter(osw, true);
//                String jsonStr = new ObjectMapper().writeValueAsString(res);
//                writer.write(jsonStr);
//                writer.flush();
//                writer.close();
//                osw.close();
//            } catch (UnsupportedEncodingException e) {
//                logger.error("转换错误 " + e.getMessage());
//            } catch (IOException e) {
//                logger.error("io异常 " + e.getMessage());
//            } finally {
//                if (null != writer) {
//                    writer.close();
//                }
//                if (null != osw) {
//                    osw.close();
//                }
//            }
//            return;
//
//        }

    }

    @Override
    public void destroy() {

    }
}
