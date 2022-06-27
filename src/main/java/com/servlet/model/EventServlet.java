package com.servlet.model;

import com.bean.*;
import com.constant.Constants;
import com.service.EventService;
import com.service.ResultService;
import com.service.StudentService;
import com.service.TutorService;
import com.service.impl.EventServiceImpl;
import com.service.impl.ResultServiceImpl;
import com.service.impl.StudentServiceImpl;
import com.service.impl.TutorServiceImpl;
import com.servlet.base.ModelBaseServlet;
import com.utils.JSONUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class EventServlet extends ModelBaseServlet {
    EventService eventService = new EventServiceImpl();
    StudentService studentService = new StudentServiceImpl();
    TutorService tutorService = new TutorServiceImpl();
    ResultService resultService=new ResultServiceImpl();

    //跳转到新建双选页面 （周才邦）
    public void toSetEvent(HttpServletRequest request, HttpServletResponse response) {
        try {
            processTemplate("event/setEvent", request, response);
        } catch (IOException e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }

    //跳转到发起双选页面 （周才邦）
    public void toStartEvent(HttpServletRequest request, HttpServletResponse response) {
        try {
            processTemplate("event/startEvent", request, response);
        } catch (IOException e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }

    //新增双选事件  （周才邦）
    public void addEvent(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            Tutor tutor = (Tutor) session.getAttribute(Constants.TUTOR_SESSION_KEY);
            Event event = (Event) JSONUtils.parseJsonToBean(request, Event.class);
            eventService.addEventWithTutorId(event, tutor);
            JSONUtils.writeResult(response, new ResultMessage(true, Constants.START_EVENT_SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false, Constants.START_EVENT_FAIL));
        }
    }

    //获取所有双选事件 （周才邦）
    public void getFullEvents(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Event> event = eventService.getFullAllEvents();
            JSONUtils.writeResult(response, new ResultMessage(true, Constants.GET_ALL_EVENT_SUCCESS, event));
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false, Constants.GET_ALL_EVENT_FAIL));
        }
    }

    //强制结束当前正在进行的双选事件
    public void shutCurrentEvent(HttpServletRequest request, HttpServletResponse response) {
        try {
            ServletContext servletContext = request.getServletContext();
            Event event = (Event) servletContext.getAttribute(Constants.EVENT_CONTEXT_KEY);
            eventService.setEventDisabled(event.getId());
            studentService.initialize();
            tutorService.initialize();
            servletContext.removeAttribute(Constants.EVENT_CONTEXT_KEY);
            processTemplate("tutor/main", request, response);
        } catch (IOException e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false, Constants.CANCEL_EVENT_FAIL));
        }
    }

    //开启双选事件
    public void enableEvent(HttpServletRequest request, HttpServletResponse response) {
        try {
            Integer chosenEventId = Integer.parseInt(request.getParameter("id"));
            Event event = eventService.enableEvent(chosenEventId);
            ServletContext servletContext = request.getServletContext();
            servletContext.setAttribute(Constants.EVENT_CONTEXT_KEY, event);
            servletContext.setAttribute(Constants.EVENT_STATUS, Constants.EVENT_STUDENT_SUBMITTING);
            JSONUtils.writeResult(response, new ResultMessage(true, Constants.ENABLE_EVENT_SUCCESS));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false,
                    Constants.ENABLE_EVENT_FAIL + "\n" + e.getMessage()));
        }
    }


    //返回双选结果，郑应啟
    public void getResult(HttpServletRequest request, HttpServletResponse response){
        try {
            IntBean intBean= (IntBean) JSONUtils.parseJsonToBean(request, IntBean.class);
            Integer id = intBean.getId();
            Result result = resultService.getResultByEventId(id);
            JSONUtils.writeResult(response, new ResultMessage(true, Constants.QUERY_SUCCESS,result.getTutors()));
        } catch (Exception e) {
            e.printStackTrace();
            //失败则返回失败,返回错误信息
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }

    //跳转ShowResultDetail页面，郑应啟
    public void toShowResultDetail(HttpServletRequest request, HttpServletResponse response){
        try {
            processTemplate("tutor/showResultDetail", request, response);
        } catch (IOException e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }
}

