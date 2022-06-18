package com.nanyan.dishorder.filter;

import com.alibaba.fastjson.JSON;
import com.nanyan.dishorder.common.BaseContext;
import com.nanyan.dishorder.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "LoginCheckFilter",urlPatterns = "/*")
public class LoginCheckFilter implements Filter
{
    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER=new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //获取URI
        String requestURI = request.getRequestURI();
        log.info("拦截到请求{}",request.getRequestURI());

        //设置无需处理的请求
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**"
        };

        //如果不需要处理，直接放行
        if(check(urls,requestURI))
        {
            log.info("本次请求{}不需要拦截",requestURI);
            filterChain.doFilter(request,response);
            return;
        }

        //判断登录状态，如果已登录，则直接放行
        if(request.getSession().getAttribute("employee") != null)
        {
            log.info("用户已登录，id为{}。",request.getSession().getAttribute("employee"));
            BaseContext.setCurrentId((Long) request.getSession().getAttribute("employee"));
            filterChain.doFilter(request,response);
            return;
        }

        //用户未登录
        log.info("用户未登录！");
        //如果未登录则返回未登录结果,通过输出流向客户端页面响应数据
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));

        return;
    }

    public boolean check(String[] URIS, String RequestURI)
    {
        for (String url : URIS)
        {
            boolean match = PATH_MATCHER.match(url, RequestURI);
            if(match) return true;
        }
        return false;
    }
}
