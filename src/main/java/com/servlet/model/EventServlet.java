package com.servlet.model;

import com.bean.Event;
import com.bean.ResultMessage;
import com.bean.Tutor;
import com.constant.Constants;
import com.service.EventService;
import com.service.impl.EventServiceImpl;
import com.servlet.base.ModelBaseServlet;
import com.utils.JSONUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class EventServlet extends ModelBaseServlet {
    EventService eventService = new EventServiceImpl();

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
}
