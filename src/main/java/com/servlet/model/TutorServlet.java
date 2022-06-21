package com.servlet.model;

import com.bean.Result;
import com.bean.Tutor;
import com.constant.Constants;
import com.service.TutorService;
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

    //教师登录（by周才邦）
    public void doLogin(HttpServletRequest request, HttpServletResponse response) {
        //调用service层处理
        try {
            Tutor tutor = (Tutor) JSONUtils.parseJsonToBean(request, Tutor.class);
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
            //获取输入验证码
            Tutor tutor = (Tutor) JSONUtils.parseJsonToBean(request, Tutor.class);
            HttpSession session = request.getSession();
            String checkCode = (String) session.getAttribute(Constants.CHECK_CODE);
            if (checkCode.equalsIgnoreCase(tutor.getCode())) {
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

}
