package com.servlet.model;

import com.bean.*;
import com.constant.Constants;
import com.service.EventService;
import com.service.StudentService;
import com.service.TutorService;
import com.service.impl.EventServiceImpl;
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
    EventService eventService = new EventServiceImpl();

    //教师登出
    public void doLogout(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getSession().invalidate();
            processTemplate("index",request,response);
        } catch (IOException e) {
            e.printStackTrace();
            JSONUtils.writeResult(response,new Result(false,Constants.LOGOUT_FAIL));
        }
    }

    //教师查看学生志愿（仅自己） by王城梓
    public void doSelectStudent(HttpServletRequest request, HttpServletResponse response) {
        String preferenceStr = request.getParameter("preference");
        String tutorIdStr = request.getParameter("tutorId");
        Integer preference = Integer.parseInt(preferenceStr);
        Integer tutorId = Integer.parseInt(tutorIdStr);
        try {
            List<Student> studentList = tutorService.checkStudent(preference, tutorId);
            JSONUtils.writeResult(response, new Result(true, studentList));
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new Result(false, e.getMessage()));
        }

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

    //导师密码修改，by郑应啟
    public void updateTutorPassword(HttpServletRequest request, HttpServletResponse response) {
        String password = request.getParameter("password");
        try {
            HttpSession session = request.getSession();
            //获取当前登入导师
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

    //跳转到教师主页面 （周才邦）
    public void toMain(HttpServletRequest request, HttpServletResponse response) {
        try {
            processTemplate("tutor/main", request, response);
        } catch (IOException e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new Result(false, e.getMessage()));
        }
    }

    //跳转到发起双选页面 （周才邦）
    public void toStartEvent(HttpServletRequest request, HttpServletResponse response) {
        try {
            processTemplate("tutor/startEvent", request, response);
        } catch (IOException e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new Result(false, e.getMessage()));
        }
    }

    //新增双选事件  （周才邦）
    public void addEvent(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            Tutor tutor = (Tutor) session.getAttribute(Constants.TUTOR_SESSION_KEY);
            Event event = (Event) JSONUtils.parseJsonToBean(request, Event.class);
            eventService.addEventWithTutorId(event, tutor);
            JSONUtils.writeResult(response, new Result(true, Constants.START_EVENT_SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new Result(false, Constants.START_EVENT_FAIL));
        }
    }

}
