package com.servlet.model;

import com.bean.*;
import com.constant.Constants;
import com.service.StudentService;
import com.service.impl.StudentServiceImpl;
import com.servlet.base.ModelBaseServlet;
import com.utils.JSONUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class StudentServlet extends ModelBaseServlet {

    StudentService studentService = new StudentServiceImpl();

    //学生info表修改，by郑应啟
    public void updateStudentInfo(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            StudentInfo studentInfo = new StudentInfo();
            BeanUtils.populate(studentInfo, parameterMap);
            HttpSession session = request.getSession();
            Student student = (Student) session.getAttribute(Constants.STUDENT_SESSION_KEY);
            student = studentService.updateStudentInfo(student, studentInfo);
            //跟新session中的student
            session.setAttribute(Constants.STUDENT_SESSION_KEY, student);
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
            Student student = (Student) session.getAttribute(Constants.STUDENT_SESSION_KEY);
            //Service层改密码
            student = studentService.updatePassword(password, student);
            //更新session信息
            session.setAttribute(Constants.STUDENT_SESSION_KEY, student);
            JSONUtils.writeResult(response, new Result(true, Constants.UPDATE_SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            //失败则返回失败,返回错误信息
            JSONUtils.writeResult(response, new Result(false, e.getMessage()));
        }
    }

    //查询所有学生，by郑应啟
    public void findAllStudent(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Student> students = studentService.getStudentList();
            students.forEach(student -> student.setStudentInfo(studentService.getInfoByStudentId(student.getSelfInfoId())));
            JSONUtils.writeResult(response, new Result(true, Constants.QUERY_SUCCESS, students));
        } catch (Exception e) {
            e.printStackTrace();
            //失败则返回失败,返回错误信息
            JSONUtils.writeResult(response, new Result(false, e.getMessage()));
        }
    }

    //通过tutorId查学生,  郑应啟
    public void findStudentListByTutorId(HttpServletRequest request, HttpServletResponse response) {
        String tutorId = request.getParameter("tutorId");
        int id = Integer.parseInt(tutorId);
        try {
            List<Student> students = studentService.getStudentListByTutorId(id);
            students.forEach(student -> student.setStudentInfo(studentService.getInfoByStudentId(student.getSelfInfoId())));
            JSONUtils.writeResult(response, new Result(true, Constants.QUERY_SUCCESS, students));
        } catch (Exception e) {
            e.printStackTrace();
            //失败则返回失败,返回错误信息
            JSONUtils.writeResult(response, new Result(false, e.getMessage()));
        }
    }

    //根据学生Id查询详细信息，by郑应啟
    public void findStudentById(HttpServletRequest request, HttpServletResponse response) {
        String studentId = request.getParameter("studentId");
        int id = Integer.parseInt(studentId);
        try {
            Student student = studentService.getStudentById(id);
            JSONUtils.writeResult(response, new Result(true, Constants.QUERY_SUCCESS, student));
        } catch (Exception e) {
            e.printStackTrace();
            //失败则返回失败,返回错误信息
            JSONUtils.writeResult(response, new Result(false, e.getMessage()));
        }
    }

    //学生登出 by王城梓
    public void doLogout(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getSession().invalidate();
            processTemplate("index", request, response);
        } catch (IOException e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new Result(false, Constants.LOGOUT_FAIL));
        }
    }

    //学生查看志愿对应的三个导师 by王城梓
    public void checkPreference(HttpServletRequest request, HttpServletResponse response) {
        String studentIdStr = request.getParameter("studentId");
        int studentId = Integer.parseInt(studentIdStr);
        try {
            Student student = studentService.getStudentById(studentId);
            List<TutorInfo> tutorInfos = studentService.getTutorInfoListByStudent(student);
            JSONUtils.writeResult(response, new Result(true, tutorInfos));
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new Result(false, e.getMessage()));
        }
    }

    //跳转到学生主页面  (周才邦)
    public void toMain(HttpServletRequest request, HttpServletResponse response) {
        try {
            processTemplate("student/main", request, response);
        } catch (IOException e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new Result(false, e.getMessage()));
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

    //更新学生志愿  (周才邦)
    public void submitPreferences(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            Student student = (Student) session.getAttribute(Constants.STUDENT_SESSION_KEY);
            //更新完志愿后同时更新session中student的属性
            Preference preference = (Preference) JSONUtils.parseJsonToBean(request, Preference.class);
            //根据studentId和preference更新学生志愿，返回志愿表更新后的主键
            if (studentService.hasPreference(student.getId())) {
                //如果已经填写过志愿，就更新,根据原本id更新id
                studentService.updatePreference(preference, student.getPreferencesId());
            } else {
                //否则添加新志愿
                int preferenceId = studentService.addPreference(preference, student.getId());
                student.setPreferencesId(preferenceId);
            }
            student.setPreference(preference);
            session.setAttribute(Constants.STUDENT_SESSION_KEY, student);
            JSONUtils.writeResult(response, new Result(true, Constants.UPDATE_PREFERENCE_SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new Result(false, Constants.UPDATE_PREFERENCE_FAIL));
        }
    }

    //获得学生填写的志愿记录 ,先判断当前student有没有preference对象，再判断有没有preferenceId,都没有说明没有填写过  （周才邦）
    public void getPreference(HttpServletRequest request, HttpServletResponse response) {
        try {
            HttpSession session = request.getSession();
            Student student = (Student) session.getAttribute(Constants.STUDENT_SESSION_KEY);
            Preference preference = student.getPreference();
            if (preference != null) {
                JSONUtils.writeResult(response, new Result(true, Constants.GET_PREVIOUS_PREFERENCES, preference.getList()));
            } else {
                int preferenceId = student.getPreferencesId();
                if (preferenceId != 0) {
                    Preference preferenceIds = studentService.getPreferenceByPreferenceId(preferenceId);
                    if (preferenceIds != null) { //查询到了Preference,更新session中的对象，并转换成数组返回
                        student.setPreference(preferenceIds);
                        session.setAttribute(Constants.STUDENT_SESSION_KEY, student);
                        JSONUtils.writeResult(response, new Result(true, Constants.GET_PREVIOUS_PREFERENCES, preferenceIds.getList()));
                    } else { //有id但是却没有查询到id，是一个异常情况
                        throw new RuntimeException("查询异常");
                    }
                } else { //没有id
                    JSONUtils.writeResult(response, new Result(true, Constants.NO_PREVIOUS_PREFERENCES));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new Result(false, e.getMessage()));
        }
    }


}
