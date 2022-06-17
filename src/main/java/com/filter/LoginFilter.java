package com.filter;

import com.bean.Student;
import com.bean.Tutor;
import com.bean.Result;
import com.constant.Constants;
import com.utils.JSONUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter {
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    //登录过滤器（by周才邦）
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        HttpSession session = httpServletRequest.getSession();
        Student student = (Student) session.getAttribute(Constants.STUDENT_SESSION_KEY);
        Tutor tutor = (Tutor) session.getAttribute(Constants.TUTOR_SESSION_KEY);
        if (student == null && tutor == null) {
            //未登录
            JSONUtils.writeResult(httpServletResponse, new Result(false, Constants.NOT_LOGGED_IN));
            return;
        }
        chain.doFilter(request, response);
    }
}
