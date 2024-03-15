package com.itheima.filter;

import com.alibaba.fastjson.JSONObject;
import com.itheima.pojo.Result;
import com.itheima.utils.JwtUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Slf4j
@WebFilter(urlPatterns = "/*")
public class LoginCheckFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req= (HttpServletRequest) request;
        HttpServletResponse resp= (HttpServletResponse) response;

        //1.获取url,判断是否为登入请求
        String url = req.getRequestURL().toString();

        if(url.contains("login")){
            log.info("登入请求,放行!");
            chain.doFilter(request,response);
            return;
        }

        if(url.contains("register")){
            log.info("登入请求,放行!");
            chain.doFilter(request,response);
            return;
        }
        //2.获取jwt,看jwt是否存在
        String jwt = req.getHeader("token");
        if(!StringUtils.hasLength(jwt)){
            log.info("jwt不存在,返回错误提示");
            Result error = Result.error("NOT_LOGIN");
            //手动将对象装换成json格式的字符串
            String result = JSONObject.toJSONString(error);
            resp.getWriter().write(result);
            return;
        }

        //3.检查jwt的合法性
        try{
        JwtUtils.parseJWT(jwt);
        }catch (Exception e){
            e.printStackTrace();
            log.info("jwt令牌非法");
            Result error = Result.error("NOT_LOGIN");
            //手动将对象装换成json格式的字符串
            String result = JSONObject.toJSONString(error);
            resp.getWriter().write(result);
            return;
        }

        //4.令牌合法,放行
        log.info("放行");
        chain.doFilter(request,response);
    }
}
