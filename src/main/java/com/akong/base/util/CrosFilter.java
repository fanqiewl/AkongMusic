package com.akong.base.util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Akong
 * @create 2021/12/19 16:31
 */
public class CrosFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT, GET");
        httpResponse.setHeader("Access-Control-Allow-Headers", " Origin, X-Requested-With, Content-Type, Accept, jwt");
        httpResponse.setHeader("Access-Control-Expose-Headers", "jwt,Content-Disposition");

        if ("OPTIONS".equals(((HttpServletRequest) request).getMethod())) {
            // axios的ajax会发两次请求，第一次提交方式为：option，直接返回即可
            return;
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

}
