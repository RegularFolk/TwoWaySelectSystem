package com.servlet.model;

import com.bean.Result;
import com.bean.Student;
import com.constant.Constants;
import com.service.StudentService;
import com.service.impl.StudentServiceImpl;
import com.servlet.base.ModelBaseServlet;
import com.utils.JSONUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.*;
import java.util.Map;

public class StudentServlet extends ModelBaseServlet {

    StudentService studentService = new StudentServiceImpl();

    //学生登录（by周才邦）
    public void doLogin(HttpServletRequest request, HttpServletResponse response) {
        String studentNumber = request.getParameter("studentNumber");
        String password = request.getParameter("password");
        Student student = new Student(studentNumber, password);
        //调用service层处理登录
        try {
            student = studentService.doLogin(student);
            //如果没有报错，则登陆成功,将student存入session
            HttpSession session = request.getSession();
            session.setAttribute(Constants.STUDENT_SESSION_KEY, student);
            //登陆成功返回登陆成功的JSON格式result
            JSONUtils.writeResult(response, new Result(true, Constants.LOGIN_SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            //失败则返回失败,返回错误信息
            JSONUtils.writeResult(response, new Result(false, e.getMessage()));
        }
    }

    //学生注册（by周才邦）
    public void doRegister(HttpServletRequest request, HttpServletResponse response) {
        try {
            //获取所有请求参数
            Map<String, String[]> parameterMap = request.getParameterMap();

            //获取用户输入的验证码
            String code = parameterMap.get("code")[0];
            String checkCode = (String) request.getSession().getAttribute(Constants.CHECK_CODE);
            //验证密码，忽略大小写
            if (checkCode.equalsIgnoreCase(code)) {
                Student student = new Student();
                BeanUtils.populate(student, parameterMap);
                studentService.doRegister(student);
                //注册成功自动登录
                request.getSession().setAttribute(Constants.STUDENT_SESSION_KEY, student);
                JSONUtils.writeResult(response, new Result(true, Constants.REGISTER_SUCCESS));
            } else {
                JSONUtils.writeResult(response, new Result(false, Constants.WRONG_CHECK_CODE));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new Result(false, e.getMessage()));
        }
    }

    //学生登出
    public void doLogout(HttpServletRequest request,HttpServletResponse response){
        request.getSession().invalidate();
        JSONUtils.writeResult(response,new Result(true,Constants.LOGOUT));
    }

}
