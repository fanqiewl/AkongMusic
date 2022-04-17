package com.akong.base.filter;

import com.akong.base.util.StringUtil;

import javax.servlet.*;
import java.io.IOException;

/**
 *
 * 编码过滤器
 *
 * @author Akong
 * @create 2021/11/25 19:36
 */
public class EncodingFilter implements Filter {
    //编码格式 => 默认为UTF-8
    private String encoding = "UTF-8";

    @Override
    @SuppressWarnings("all")
    public void init(FilterConfig filterConfig) throws ServletException {
        //获取初始属性
        String param = filterConfig.getInitParameter("encoding");
        //判断属性不为空 => 设置编码
        if(StringUtil.isNotBlank(param))
            encoding = param;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //设置请求编码
        servletRequest.setCharacterEncoding(encoding);
        //设置响应编码
        servletResponse.setCharacterEncoding(encoding);
        servletResponse.setContentType("text/html;charset="+encoding);

        //放行
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
