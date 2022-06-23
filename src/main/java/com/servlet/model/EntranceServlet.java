package com.servlet.model;

import com.bean.ResultMessage;
import com.bean.Student;
import com.bean.Tutor;
import com.constant.Constants;
import com.service.StudentService;
import com.service.TutorService;
import com.service.impl.StudentServiceImpl;
import com.service.impl.TutorServiceImpl;
import com.servlet.base.ModelBaseServlet;
import com.utils.JSONUtils;

import javax.servlet.http.*;
import java.io.IOException;

public class EntranceServlet extends ModelBaseServlet {
    TutorService tutorService = new TutorServiceImpl();
    StudentService studentService = new StudentServiceImpl();

    //登录前处理，清空原本session中的记录
    private void preHandle(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.removeAttribute(Constants.STUDENT_SESSION_KEY);
        session.removeAttribute(Constants.TUTOR_SESSION_KEY);
    }

    //教师登录（by周才邦）
    public void doTutorLogin(HttpServletRequest request, HttpServletResponse response) {
        //调用service层处理
        try {
            preHandle(request, response);
            Tutor tutor = (Tutor) JSONUtils.parseJsonToBean(request, Tutor.class);
            tutor = tutorService.doLogin(tutor);
            //没有报错则登录
            HttpSession session = request.getSession();
            session.setAttribute(Constants.TUTOR_SESSION_KEY, tutor);
            JSONUtils.writeResult(response, new ResultMessage(true, Constants.LOGIN_SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }

    //学生登录（by周才邦）
    public void doStudentLogin(HttpServletRequest request, HttpServletResponse response) {
        //调用service层处理登录
        try {
            preHandle(request, response);
            Student student = (Student) JSONUtils.parseJsonToBean(request, Student.class);
            student = studentService.doLogin(student);
            //如果没有报错，则登陆成功,将student存入session
            HttpSession session = request.getSession();
            session.setAttribute(Constants.STUDENT_SESSION_KEY, student);
            //登陆成功返回登陆成功的JSON格式result
            JSONUtils.writeResult(response, new ResultMessage(true, Constants.LOGIN_SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            //失败则返回失败,返回错误信息
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }

    //教师注册（by周才邦）
    public void doTutorRegister(HttpServletRequest request, HttpServletResponse response) {
        try {
            //获取输入验证码
            Tutor tutor = (Tutor) JSONUtils.parseJsonToBean(request, Tutor.class);
            HttpSession session = request.getSession();
            String checkCode = (String) session.getAttribute(Constants.CHECK_CODE);
            if (checkCode.equalsIgnoreCase(tutor.getCode())) {
                tutorService.doRegister(tutor);
                //注册成功自动登录
                session.setAttribute(Constants.TUTOR_SESSION_KEY, tutor);
                JSONUtils.writeResult(response, new ResultMessage(true, Constants.REGISTER_SUCCESS));
            } else {
                JSONUtils.writeResult(response, new ResultMessage(false, Constants.WRONG_CHECK_CODE));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }

    //学生注册（by周才邦）
    public void doStudentRegister(HttpServletRequest request, HttpServletResponse response) {
        try {
            Student student = (Student) JSONUtils.parseJsonToBean(request, Student.class);
            //获取用户输入的验证码
            String checkCode = (String) request.getSession().getAttribute(Constants.CHECK_CODE);
            //验证密码，忽略大小写
            if (checkCode.equalsIgnoreCase(student.getCode())) {
                studentService.doRegister(student);
                //注册成功自动登录
                request.getSession().setAttribute(Constants.STUDENT_SESSION_KEY, student);
                JSONUtils.writeResult(response, new ResultMessage(true, Constants.REGISTER_SUCCESS));
            } else {
                JSONUtils.writeResult(response, new ResultMessage(false, Constants.WRONG_CHECK_CODE));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }

    //用户登出 by王城梓
    public void doLogout(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getSession().invalidate();
            processTemplate("index", request, response);
        } catch (IOException e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false, Constants.LOGOUT_FAIL));
        }
    }
}
