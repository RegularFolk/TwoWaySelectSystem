package com.filter;

import com.bean.Event;
import com.bean.ResultMessage;
import com.bean.Student;
import com.constant.Constants;
import com.service.EventService;
import com.service.ResultService;
import com.service.StudentService;
import com.service.TutorService;
import com.service.impl.EventServiceImpl;
import com.service.impl.ResultServiceImpl;
import com.service.impl.StudentServiceImpl;
import com.service.impl.TutorServiceImpl;
import com.utils.JSONUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

//这个filter是本项目的核心科技  周才邦
public class EventFilter implements Filter {
    EventService eventService = new EventServiceImpl();
    ResultService resultService = new ResultServiceImpl();
    StudentService studentService = new StudentServiceImpl();
    TutorService tutorService = new TutorServiceImpl();

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    //负责管理全局域中的event对象，更新全局状态
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        try {
            ServletContext servletContext = request.getServletContext();
            Event contextEvent = (Event) servletContext.getAttribute(Constants.EVENT_CONTEXT_KEY);
            if (contextEvent == null) { //如果全局域event为空，尝试获取
                contextEvent = eventService.getOngoingEvent();
                servletContext.setAttribute(Constants.EVENT_CONTEXT_KEY, contextEvent);
            }
            if (contextEvent == null) {//尝试获取之后依旧为空，确认当前没有进行中的event
                servletContext.setAttribute(Constants.EVENT_STATUS, Constants.EVENT_SHUT);
            } else {//获得了正在进行的双选事件，更新全局域状态
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String format = sdf.format(new Date());
                if (format.compareTo(contextEvent.getSubmitTime()) >= 0) {//当前时间晚于提交截止时间
                    if (format.compareTo(contextEvent.getRound1()) >= 0) {//当前时间晚于导师第一轮选择时间
                        if (format.compareTo(contextEvent.getRound2()) >= 0) {//当前时间晚于导师第二轮选择时间
                            if (format.compareTo(contextEvent.getRound3()) >= 0) {//当前时间晚于导师第三轮选择时间
                                //判断是否已经生成最终结果，如果已生成，便设置状态为没有选课事件，如果结果还没有生成，先进行自动分配
                                if (!resultService.hasFinalResult(contextEvent.getId())) {
                                    //启动自动分配
                                    activateRandomAllocation();
                                    //将结果全部存入result表，初始化所有学生和导师
                                    resultService.updateResult(contextEvent.getId());
                                    studentService.initialize();
                                    tutorService.initialize();
                                }//最终result都已经生成了，选课事件彻底结束了
                                eventService.setEventFinished(contextEvent.getId()); //将库中的event标记为已结束
                                servletContext.removeAttribute(Constants.EVENT_CONTEXT_KEY); //移除全局域的event对象
                                servletContext.setAttribute(Constants.EVENT_STATUS, Constants.EVENT_SHUT); //设置全局event状态为没有event
                            } else {
                                servletContext.setAttribute(Constants.EVENT_STATUS, Constants.EVENT_ROUND3);
                            }
                        } else {
                            servletContext.setAttribute(Constants.EVENT_STATUS, Constants.EVENT_ROUND2);
                        }
                    } else {
                        servletContext.setAttribute(Constants.EVENT_STATUS, Constants.EVENT_ROUND1);
                    }
                } else {
                    servletContext.setAttribute(Constants.EVENT_STATUS, Constants.EVENT_STUDENT_SUBMITTING);
                }
            }
            chain.doFilter(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult((HttpServletResponse) response,
                    new ResultMessage(false, Constants.UPDATE_EVENT_STATUS_FAIL) + "\n" + e.getMessage());
        }
    }

    //自动分配完之后student表中每个student都应该有tutorId字段
    private void activateRandomAllocation() {
        List<Student> students = studentService.getNotChosen();
        tutorService.randomAllocation(students);
    }
}
