package com.shfound.showproduct.controller.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shfound.showproduct.controller.result.SuccessResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.GsonJsonParser;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;


@Component
@WebFilter(urlPatterns = {""}, filterName = "tokenAuthorFilter")
public class TokenAuthorFilter implements Filter {

    Logger logger = LoggerFactory.getLogger(TokenAuthorFilter.class);

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
        String token = req.getHeader("token");
        String value = "";
        if (token != null && !token.isEmpty()) {
            value = stringRedisTemplate.opsForValue().get(token);
        }

        SuccessResult res = new SuccessResult();
        if (null == token || token.isEmpty() || value.isEmpty()) {
            res.setCode(2000);
            res.setMessage("用户身份验证失败，请重新登录");

            PrintWriter writer = null;
            OutputStreamWriter osw = null;
            try {
                osw = new OutputStreamWriter(servletResponse.getOutputStream(), "UTF-8");
                writer = new PrintWriter(osw, true);
                String jsonStr = new ObjectMapper().writeValueAsString(res);
                writer.write(jsonStr);
                writer.flush();
                writer.close();
                osw.close();
            } catch (UnsupportedEncodingException e) {
                logger.error("转换错误 " + e.getMessage());
            } catch (IOException e) {
                logger.error("io异常 " + e.getMessage());
            } finally {
                if (null != writer) {
                    writer.close();
                }
                if (null != osw) {
                    osw.close();
                }
            }
            return;

        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
