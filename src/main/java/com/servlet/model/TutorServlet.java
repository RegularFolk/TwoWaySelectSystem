package com.servlet.model;

import com.bean.*;
import com.constant.Constants;
import com.service.MessageService;
import com.service.StudentService;
import com.service.TutorService;
import com.service.impl.MessageServiceImpl;
import com.service.impl.StudentServiceImpl;
import com.service.impl.TutorServiceImpl;
import com.servlet.base.ModelBaseServlet;
import com.utils.JSONUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletContext;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TutorServlet extends ModelBaseServlet {

    TutorService tutorService = new TutorServiceImpl();
    StudentService studentService = new StudentServiceImpl();
    MessageService messageService = new MessageServiceImpl();

    //教师查看学生志愿（仅自己） by王城梓
    public void doSelectStudent(HttpServletRequest request, HttpServletResponse response) {
        String preferenceStr = request.getParameter("preference");
        String tutorIdStr = request.getParameter("tutorId");
        Integer preference = Integer.parseInt(preferenceStr);
        Integer tutorId = Integer.parseInt(tutorIdStr);
        try {
            List<Student> studentList = tutorService.checkStudent(preference, tutorId);
            JSONUtils.writeResult(response, new ResultMessage(true, studentList));
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }

    }

    //查询所有导师 （zcb）
    public void getTutors(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Tutor> tutorList = tutorService.getTutorList();
            JSONUtils.writeResult(response, new ResultMessage(true, tutorList));
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false, Constants.QUERY_FAIL));
        }
    }

    //获取所有导师和导师信息  （zcb）
    public void getFullTutors(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Tutor> tutorList = tutorService.getTutorList();
            tutorList.forEach(tutor -> tutor.setTutorInfo(tutorService.getInfoByTutorId(tutor.getTutorInfoId())));
            JSONUtils.writeResult(response, new ResultMessage(true, tutorList));
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false, Constants.QUERY_FAIL));
        }

    }

    //跳转到展示导师页面 （zcb）
    public void toShowTutors(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html;charset=utf-8");
            processTemplate("tutor/showTutors", request, response);
        } catch (IOException e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }

    //跳转到教师个人信息
    public void toSelfInfo(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html;charset=utf-8");
            processTemplate("tutor/selfInfo", request, response);
        } catch (IOException e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }


    //查询导师详细信息，个人信息  郑应啟
    public void selectTutorFullInfo(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            Tutor tutor = (Tutor) session.getAttribute(Constants.TUTOR_SESSION_KEY);
            Tutor tutorFull=new Tutor();
            tutorFull = tutorService.getTutorById(tutor.getId());
            JSONUtils.writeResult(response, new ResultMessage(true, Constants.QUERY_SUCCESS, tutorFull));
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }

    //导师info表修改，by郑应啟
    public void updateTutorInfo(HttpServletRequest request, HttpServletResponse response) {
        try {
            TutorInfo tutorInfo = (TutorInfo) JSONUtils.parseJsonToBean(request, TutorInfo.class);
            HttpSession session = request.getSession();
            Tutor tutor = (Tutor) session.getAttribute(Constants.TUTOR_SESSION_KEY);
            tutor = tutorService.updateTutorInfo(tutor, tutorInfo);
            //跟新session中的student
            session.setAttribute(Constants.TUTOR_SESSION_KEY, tutor);
            JSONUtils.writeResult(response, new ResultMessage(true, Constants.UPDATE_SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }

    //修改导师 郑应啟
    public void updateTutor(HttpServletRequest request, HttpServletResponse response) {
        try {
            Tutor updateTutor = (Tutor) JSONUtils.parseJsonToBean(request, Tutor.class);
            HttpSession session = request.getSession();
            Tutor tutor = (Tutor) session.getAttribute(Constants.TUTOR_SESSION_KEY);
            Tutor tutor1 = tutorService.updateTutor(updateTutor, tutor);
            session.setAttribute(Constants.TUTOR_SESSION_KEY, tutor1);
            JSONUtils.writeResult(response, new ResultMessage(true, Constants.UPDATE_SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }

    //导师密码修改，by郑应啟
    public void updateTutorPassword(HttpServletRequest request, HttpServletResponse response) {
        StringBean stringBean = (StringBean) JSONUtils.parseJsonToBean(request, StringBean.class);
        String password=stringBean.getString();
        System.out.println(password);
        try {
            HttpSession session = request.getSession();
            //获取当前登入导师
            Tutor tutor = (Tutor) session.getAttribute(Constants.TUTOR_SESSION_KEY);
            //Service层改密码
            tutor = tutorService.updatePassword(password, tutor);
            //更新session信息
            session.setAttribute(Constants.TUTOR_SESSION_KEY, tutor);
            JSONUtils.writeResult(response, new ResultMessage(true, Constants.UPDATE_SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            //失败则返回失败,返回错误信息
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }


    //跳转到教师主页面 （周才邦）
    public void toMain(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html;charset=utf-8");
            processTemplate("tutor/main", request, response);
        } catch (IOException e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }

    //跳转到导师私信页面
    public void toMessage(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html;charset=utf-8");
            processTemplate("tutor/message", request, response);
        } catch (IOException e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }

    //导师跳转到选择学生页面
    public void toRound(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html;charset=utf-8");
            int round = Integer.parseInt(request.getParameter("round"));
            processTemplate("tutor/round" + round, request, response);
        } catch (IOException e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }

    //跳转到最终结果页面
    public void toResult(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            processTemplate("tutor/showResults", request, response);
        } catch (IOException e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }

    //导师端的获取正在进行的双选事件
    public void getOngoingEvent(HttpServletRequest request, HttpServletResponse response) {
        try {
            ServletContext servletContext = request.getServletContext();
            Integer eventStatus = (Integer) servletContext.getAttribute(Constants.EVENT_STATUS);
            if (eventStatus.equals(Constants.EVENT_SHUT)) {
                JSONUtils.writeResult(response, new ResultMessage(true, Constants.NO_ONGOING_EVENT, eventStatus));
            } else {
                String message = Constants.EVENT_EXCEPTION_MESSAGE;
                if (eventStatus.equals(Constants.EVENT_STUDENT_SUBMITTING)) {
                    message = Constants.SUBMIT_MESSAGE;
                } else if (eventStatus.equals(Constants.EVENT_ROUND1)) {
                    message = Constants.ROUND1_MESSAGE;
                } else if (eventStatus.equals(Constants.EVENT_ROUND2)) {
                    message = Constants.ROUND2_MESSAGE;
                } else if (eventStatus.equals(Constants.EVENT_ROUND3)) {
                    message = Constants.ROUND3_MESSAGE;
                }
                JSONUtils.writeResult(response, new ResultMessage(true, message, eventStatus));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false,
                    Constants.UPDATE_EVENT_STATUS_FAIL + "\n" + e.getMessage()));
        }
    }

    //获得当前登录的tutor对象
    public void getTutorSelf(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            Tutor tutor = (Tutor) session.getAttribute(Constants.TUTOR_SESSION_KEY);
            if (tutor == null) {
                throw new RuntimeException(Constants.NOT_LOGGED_IN);
            }
            JSONUtils.writeResult(response, new ResultMessage(true, Constants.GET_TUTOR_SUCCESS, tutor));
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false,
                    Constants.GET_PRE_STU_FAIL + "\n" + e.getMessage()));
        }
    }

    //获得指定志愿候选学生,返回 List<Student>
    public void getAvailableStudents(HttpServletRequest request, HttpServletResponse response) {
        try {
            int round = Integer.parseInt(request.getParameter("round"));
            HttpSession session = request.getSession();
            Tutor tutor = (Tutor) session.getAttribute(Constants.TUTOR_SESSION_KEY);
            List<Student> availableStudents = studentService.getAvailableStudents(round, tutor.getId());
            JSONUtils.writeResult(response, new ResultMessage(true, availableStudents));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false,
                    Constants.GET_CANDIDATES_FAIL + "\n" + e.getMessage()));
        }
    }

    //选择学生，提交到数据库
    public void takeStudents(HttpServletRequest request, HttpServletResponse response) {
        try {
            Integers integers = (Integers) JSONUtils.parseJsonToBean(request, Integers.class);
            List<Integer> chosenIds = integers.getChosenIds();
            HttpSession session = request.getSession();
            Tutor tutor = (Tutor) session.getAttribute(Constants.TUTOR_SESSION_KEY);
            if (tutor.getLeft() < chosenIds.size()) {
                throw new RuntimeException(Constants.TOO_MANY_CHOSEN);
            }
            tutorService.takeStudents(tutor, chosenIds, integers.getRound());
            JSONUtils.writeResult(response, new ResultMessage(true, Constants.TAKE_STUDENT_SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false,
                    Constants.TAKE_STUDENT_FAIL + "\n" + e.getMessage()));
        }
    }

    //获得当前批次已经选择过的学生
    public void getPreviousTaken(HttpServletRequest request, HttpServletResponse response) {
        try {
            int round = Integer.parseInt(request.getParameter("round"));
            HttpSession session = request.getSession();
            Tutor tutor = (Tutor) session.getAttribute(Constants.TUTOR_SESSION_KEY);
            List<Integer> takenStudentIds = studentService.getTakenStudentIds(tutor, round);
            JSONUtils.writeResult(response, new ResultMessage(true, takenStudentIds));
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false, Constants.GET_PREVIOUS_TAKEN_FAIL));
        }
    }

    //获取当前登录导师名
    public void getUsername(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            Tutor tutor = (Tutor) session.getAttribute(Constants.TUTOR_SESSION_KEY);
            System.out.println(tutor.getName());
            JSONUtils.writeResult(response, new ResultMessage(true, "", tutor.getName()));
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false, Constants.GET_NAME_FAIL));
        }
    }

    //跳转到登录欢迎页面
    public void toWelcome(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html;charset=utf-8");
            processTemplate("tutor/welcome", request, response);
        } catch (IOException e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, Constants.TO_WELCOME_FAIL);
        }
    }

    //获取学生，（私信对象） 郑应啟
    public void getMyStudents(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            Tutor tutor = (Tutor) session.getAttribute(Constants.TUTOR_SESSION_KEY);
            List<Student> students = studentService.getStudentList();
            JSONUtils.writeResult(response, new ResultMessage(true, Constants.QUERY_SUCCESS, students));
        } catch (Exception e) {
            e.printStackTrace();
            //失败则返回失败,返回错误信息
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }

    //导师给学生 发送私信 郑应啟
    public void sendMessageToStudent(HttpServletRequest request, HttpServletResponse response) {
        Message message = (Message) JSONUtils.parseJsonToBean(request, Message.class);

        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = sdf.format(new Date());
            HttpSession session = request.getSession();
            Tutor tutor = (Tutor) session.getAttribute(Constants.TUTOR_SESSION_KEY);
            //学生id 负数，导师id 正数
            messageService.sendMessageById(+tutor.getId(), -message.getReceiverId(), message.getText(), format);
            JSONUtils.writeResult(response, new ResultMessage(true, Constants.SEND_SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            //失败则返回失败,返回错误信息
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }

    //导师接受私信 郑应啟
    public void getMessageFromStudent(HttpServletRequest request, HttpServletResponse response) {
        String s = request.getParameter("studentId");
        int studentId = Integer.parseInt(s);
        try {
            HttpSession session = request.getSession();
            Tutor tutor = (Tutor) session.getAttribute(Constants.TUTOR_SESSION_KEY);
            List<Message> messages = messageService.getMessage(-studentId, +tutor.getId());
            JSONUtils.writeResult(response, new ResultMessage(true, Constants.RECEIVE_SUCCESS, messages));
        } catch (Exception e) {
            e.printStackTrace();
            //失败则返回失败,返回错误信息
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }

    //查询所有私信 郑应啟
    public void getMessageList(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            Tutor tutor = (Tutor) session.getAttribute(Constants.TUTOR_SESSION_KEY);
            List<Message> messages = messageService.getMessageList(+tutor.getId());
            JSONUtils.writeResult(response, new ResultMessage(true, Constants.QUERY_SUCCESS, messages));
        } catch (Exception e) {
            e.printStackTrace();
            //失败则返回失败,返回错误信息
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }

}
