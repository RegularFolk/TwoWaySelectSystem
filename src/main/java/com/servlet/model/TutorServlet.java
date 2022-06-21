package com.servlet.model;

import com.bean.*;
import com.constant.Constants;
import com.service.StudentService;
import com.service.TutorService;
import com.service.impl.StudentServiceImpl;
import com.service.impl.TutorServiceImpl;
import com.servlet.base.ModelBaseServlet;
import com.utils.JSONUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TutorServlet extends ModelBaseServlet {

    TutorService tutorService = new TutorServiceImpl();
    StudentService studentService = new StudentServiceImpl();

    //教师登录（by周才邦）
    public void doLogin(HttpServletRequest request, HttpServletResponse response) {
        String number = request.getParameter("number");
        String password = request.getParameter("password");
        Tutor tutor = new Tutor(number, password);
        //调用service层处理
        try {
            tutor = tutorService.doLogin(tutor);
            //没有报错则登录
            HttpSession session = request.getSession();
            session.setAttribute(Constants.TUTOR_SESSION_KEY, tutor);
            JSONUtils.writeResult(response, new Result(true, Constants.LOGIN_SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new Result(false, e.getMessage()));
        }
    }

    //教师注册（by周才邦）
    public void doRegister(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            //获取输入验证码
            String code = parameterMap.get("code")[0];
            HttpSession session = request.getSession();
            String checkCode = (String) session.getAttribute(Constants.CHECK_CODE);
            if (code.equalsIgnoreCase(checkCode)) {
                Tutor tutor = new Tutor();
                BeanUtils.populate(tutor, parameterMap);
                tutorService.doRegister(tutor);
                //注册成功自动登录
                session.setAttribute(Constants.TUTOR_SESSION_KEY, tutor);
                JSONUtils.writeResult(response, new Result(true, Constants.REGISTER_SUCCESS));
            } else {
                JSONUtils.writeResult(response, new Result(false, Constants.WRONG_CHECK_CODE));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new Result(false, e.getMessage()));
        }
    }

    //教师登出
    public void doLogout(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        JSONUtils.writeResult(response, new Result(true, Constants.LOGOUT));
    }

    //教师查看学生志愿（仅自己）
    public void doSelectStudent(HttpServletRequest request, HttpServletResponse response) {

    }

    //查询所有导师 （zcb）
    public void getTutors(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Tutor> tutorList = tutorService.getTutorList();
            JSONUtils.writeResult(response, new Result(true, tutorList));
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new Result(false, Constants.QUERY_FAIL));
        }
    }

    //跳转到选择导师界面  （zcb）
    public void toSelectTutor(HttpServletRequest request, HttpServletResponse response) {
        try {
            processTemplate("student/selectTutor", request, response);
        } catch (IOException e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new Result(false, e.getMessage()));
        }
    }

    //获取所有导师和导师信息  （zcb）
    public void getFullTutors(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Tutor> tutorList = tutorService.getTutorList();
            tutorList.forEach(tutor -> tutor.setTutorInfo(tutorService.getInfoByTutorId(tutor.getTutorInfoId())));
            JSONUtils.writeResult(response, new Result(true, tutorList));
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new Result(false, Constants.QUERY_FAIL));
        }

    }

    //跳转到展示导师页面 （zcb）
    public void toShowTutors(HttpServletRequest request, HttpServletResponse response) {
        try {
            processTemplate("tutor/showTutors", request, response);
        } catch (IOException e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new Result(false, e.getMessage()));
        }
    }

    //导师info表修改，by郑应啟
    public void updateStudentInfo(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            TutorInfo tutorInfo = new TutorInfo();
            BeanUtils.populate(tutorInfo, parameterMap);
            HttpSession session = request.getSession();
            Tutor tutor = (Tutor) session.getAttribute(Constants.TUTOR_SESSION_KEY);
            tutor = tutorService.updateTutorInfo(tutor, tutorInfo);
            //跟新session中的student
            session.setAttribute(Constants.TUTOR_SESSION_KEY, tutor);
            JSONUtils.writeResult(response, new Result(true, Constants.UPDATE_SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new Result(false, e.getMessage()));
        }
    }

    //学生密码修改，by郑应啟
    public void updateStudentPassword(HttpServletRequest request, HttpServletResponse response) {
        String password = request.getParameter("password");
        try {
            HttpSession session = request.getSession();
            //获取当前登入学生
            Tutor tutor = (Tutor) session.getAttribute(Constants.TUTOR_SESSION_KEY);
            //Service层改密码
            tutor = tutorService.updatePassword(password, tutor);
            //更新session信息
            session.setAttribute(Constants.TUTOR_SESSION_KEY, tutor);
            JSONUtils.writeResult(response, new Result(true, Constants.UPDATE_SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            //失败则返回失败,返回错误信息
            JSONUtils.writeResult(response, new Result(false, e.getMessage()));
        }
    }

}
