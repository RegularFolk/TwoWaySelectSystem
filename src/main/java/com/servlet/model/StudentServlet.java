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

public class StudentServlet extends ModelBaseServlet {

    StudentService studentService = new StudentServiceImpl();
    TutorService tutorService = new TutorServiceImpl();
    MessageService messageService = new MessageServiceImpl();

    //学生info表修改，by郑应啟
    public void updateStudentInfo(HttpServletRequest request, HttpServletResponse response) {
        try {
            StudentInfo studentInfo = (StudentInfo) JSONUtils.parseJsonToBean(request, StudentInfo.class);
            HttpSession session = request.getSession();
            Student student = (Student) session.getAttribute(Constants.STUDENT_SESSION_KEY);
            student = studentService.updateStudentInfo(student, studentInfo);
            //跟新session中的student
            session.setAttribute(Constants.STUDENT_SESSION_KEY, student);
            JSONUtils.writeResult(response, new ResultMessage(true, Constants.UPDATE_SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }

    //学生密码修改，by郑应啟
    public void updateStudentPassword(HttpServletRequest request, HttpServletResponse response) {
        StringBean stringBean = (StringBean) JSONUtils.parseJsonToBean(request, StringBean.class);
        String password=stringBean.getString();
        System.out.println(password);
        try {
            HttpSession session = request.getSession();
            //获取当前登入学生
            Student student = (Student) session.getAttribute(Constants.STUDENT_SESSION_KEY);
            //Service层改密码
            student = studentService.updatePassword(password, student);
            //更新session信息
            session.setAttribute(Constants.STUDENT_SESSION_KEY, student);
            JSONUtils.writeResult(response, new ResultMessage(true, Constants.UPDATE_SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            //失败则返回失败,返回错误信息
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }

    //查询所有学生，by郑应啟
    public void findAllStudent(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Student> students = studentService.getStudentList();
            students.forEach(student -> student.setStudentInfo(studentService.getInfoByStudentId(student.getSelfInfoId())));
            JSONUtils.writeResult(response, new ResultMessage(true, Constants.QUERY_SUCCESS, students));
        } catch (Exception e) {
            e.printStackTrace();
            //失败则返回失败,返回错误信息
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }

    //通过tutorId查学生,  郑应啟
    public void findStudentListByTutorId(HttpServletRequest request, HttpServletResponse response) {
        String tutorId = request.getParameter("tutorId");
        int id = Integer.parseInt(tutorId);
        try {
            List<Student> students = studentService.getStudentListByTutorId(id);
            students.forEach(student -> student.setStudentInfo(studentService.getInfoByStudentId(student.getSelfInfoId())));
            JSONUtils.writeResult(response, new ResultMessage(true, Constants.QUERY_SUCCESS, students));
        } catch (Exception e) {
            e.printStackTrace();
            //失败则返回失败,返回错误信息
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }

    //跳转到个人信息
    public void toSelfInfo(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html;charset=utf-8");
            processTemplate("student/selfInfo", request, response);
        } catch (IOException e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }

    //查询详细信息，by郑应啟
    public void selectStudentFullInfo(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            Student student = (Student) session.getAttribute(Constants.STUDENT_SESSION_KEY);
            Student studentFull = new Student();
            studentFull = studentService.getStudentById(student.getId());
            JSONUtils.writeResult(response, new ResultMessage(true, Constants.QUERY_SUCCESS, studentFull));
        } catch (Exception e) {
            e.printStackTrace();
            //失败则返回失败,返回错误信息
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }


    //学生查看志愿对应的三个导师 by王城梓
    public void checkPreference(HttpServletRequest request, HttpServletResponse response) {
        String studentIdStr = request.getParameter("studentId");
        int studentId = Integer.parseInt(studentIdStr);
        try {
            Student student = studentService.getStudentById(studentId);
            List<TutorInfo> tutorInfos = studentService.getTutorInfoListByStudent(student);
            JSONUtils.writeResult(response, new ResultMessage(true, tutorInfos));
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }

    //跳转到学生主页面  (周才邦)
    public void toMain(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html;charset=utf-8");
            processTemplate("student/main", request, response);
        } catch (IOException e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }

    //跳转到选择导师界面  （zcb）
    public void toSelectTutor(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html;charset=utf-8");
            processTemplate("student/selectTutor", request, response);
        } catch (IOException e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }

    //更新学生志愿  (周才邦)
    public void submitPreferences(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            Student student = (Student) session.getAttribute(Constants.STUDENT_SESSION_KEY);
            //更新完志愿后同时更新session中student的属性
            Preference preference = (Preference) JSONUtils.parseJsonToBean(request, Preference.class);
            //根据studentId和preference更新学生志愿，返回志愿表更新后的主键
            int studentId = student.getId();
            if (studentService.hasPreference(studentId)) {
                //如果已经填写过志愿，就更新,根据原本id更新id
                studentService.updatePreference(preference, student.getPreferencesId());
            } else {
                //否则添加新志愿
                int preferenceId = studentService.addPreference(preference, studentId);
                student.setPreferencesId(preferenceId);
            }
            studentService.setStatusToChose(studentId);//将学生的status设置为已做出选择
            student.setStatus(Constants.STUDENT_STATUS_CHOSE);
            student.setPreference(preference);
            session.setAttribute(Constants.STUDENT_SESSION_KEY, student);
            JSONUtils.writeResult(response, new ResultMessage(true, Constants.UPDATE_PREFERENCE_SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false, Constants.UPDATE_PREFERENCE_FAIL));
        }
    }

    //获得学生填写的志愿记录 ,先判断当前student有没有preference对象，再判断有没有preferenceId,都没有说明没有填写过  （周才邦）
    public void getPreference(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            Student student = (Student) session.getAttribute(Constants.STUDENT_SESSION_KEY);
            Preference preference = student.getPreference();
            if (preference != null) {
                JSONUtils.writeResult(response, new ResultMessage(true, Constants.GET_PREVIOUS_PREFERENCES, preference.getList()));
            } else {
                int preferenceId = student.getPreferencesId();
                if (preferenceId != 0) {
                    Preference preferenceIds = studentService.getPreferenceByPreferenceId(preferenceId);
                    if (preferenceIds != null) { //查询到了Preference,更新session中的对象，并转换成数组返回
                        student.setPreference(preferenceIds);
                        session.setAttribute(Constants.STUDENT_SESSION_KEY, student);
                        JSONUtils.writeResult(response, new ResultMessage(true, Constants.GET_PREVIOUS_PREFERENCES, preferenceIds.getList()));
                    } else { //有id但是却没有查询到id，是一个异常情况
                        throw new RuntimeException("查询异常");
                    }
                } else { //没有id
                    JSONUtils.writeResult(response, new ResultMessage(true, Constants.NO_PREVIOUS_PREFERENCES));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }

    //跳转到学生私信页面
    public void toMessage(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html;charset=utf-8");
            processTemplate("student/message", request, response);
        } catch (IOException e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }

    //学生端的获取正在进行的双选事件
    public void getOngoingEvent(HttpServletRequest request, HttpServletResponse response) {
        try {
            ServletContext servletContext = request.getServletContext();
            Event event = (Event) servletContext.getAttribute(Constants.EVENT_CONTEXT_KEY);
            if (event == null) {
                JSONUtils.writeResult(response, new ResultMessage(false, Constants.NO_ONGOING_EVENT));
            } else {
                JSONUtils.writeResult(response, new ResultMessage(true, Constants.GET_ONGOING_EVENT, event));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false,
                    Constants.UPDATE_EVENT_STATUS_FAIL + "\n" + e.getMessage()));
        }
    }

    //获取当前登录学生姓名
    public void getUsername(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            Student student = (Student) session.getAttribute(Constants.STUDENT_SESSION_KEY);
            JSONUtils.writeResult(response, new ResultMessage(true, "", student.getStudentName()));
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false, Constants.GET_NAME_FAIL));
        }
    }

    //跳转到登录欢迎页面
    public void toWelcome(HttpServletRequest request, HttpServletResponse response) {
        try {
            processTemplate("student/welcome", request, response);
        } catch (IOException e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, Constants.TO_WELCOME_FAIL);
        }
    }

    //获取导师，（私信对象） 郑应啟
    public void getMyTutor(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            Student student = (Student) session.getAttribute(Constants.STUDENT_SESSION_KEY);
            List<Tutor> tutorList = tutorService.getTutorList();
            JSONUtils.writeResult(response, new ResultMessage(true, Constants.QUERY_SUCCESS, tutorList));
        } catch (Exception e) {
            e.printStackTrace();
            //失败则返回失败,返回错误信息
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }

    //学生给导师 发送私信 郑应啟
    public void sendMessageToTutor(HttpServletRequest request, HttpServletResponse response) {


        Message message = (Message) JSONUtils.parseJsonToBean(request, Message.class);
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = sdf.format(new Date());
            HttpSession session = request.getSession();
            Student student = (Student) session.getAttribute(Constants.STUDENT_SESSION_KEY);
            //学生id 负数，导师id 正数
            messageService.sendMessageById(-student.getId(), +message.getReceiverId(), message.getText(), format);
            JSONUtils.writeResult(response, new ResultMessage(true, Constants.SEND_SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            //失败则返回失败,返回错误信息
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }

    //学生接受私信 郑应啟
    public void getMessageFromTutor(HttpServletRequest request, HttpServletResponse response) {
        String t = request.getParameter("tutorId");
        int tutorId = Integer.parseInt(t);
        try {
            HttpSession session = request.getSession();
            Student student = (Student) session.getAttribute(Constants.STUDENT_SESSION_KEY);
            List<Message> messages = messageService.getMessage(+tutorId, -student.getId());
            JSONUtils.writeResult(response, new ResultMessage(true, Constants.RECEIVE_SUCCESS, messages));
        } catch (Exception e) {
            e.printStackTrace();
            //失败则返回失败,返回错误信息
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }

    //查询所有私信
    public void getMessageList(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            Student student = (Student) session.getAttribute(Constants.STUDENT_SESSION_KEY);
            List<Message> messages = messageService.getMessageList(-student.getId());
            JSONUtils.writeResult(response, new ResultMessage(true, Constants.QUERY_SUCCESS, messages));
        } catch (Exception e) {
            e.printStackTrace();
            //失败则返回失败,返回错误信息
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }

    //跳转到最终结果页面
    public void toResult(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("utf-8");
            response.setContentType("text/html;charset=utf-8");
            processTemplate("student/showResults", request, response);
        } catch (IOException e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }
    //跳转ShowResultDetail页面，郑应啟
    public void toShowResultDetail(HttpServletRequest request, HttpServletResponse response) {
        try {
            processTemplate("student/showResultDetail", request, response);
        } catch (IOException e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new ResultMessage(false, e.getMessage()));
        }
    }

}
