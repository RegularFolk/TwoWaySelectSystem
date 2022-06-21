package com.servlet.model;

import com.bean.Result;
import com.bean.Student;
import com.bean.StudentInfo;
import com.constant.Constants;
import com.service.StudentService;
import com.service.impl.StudentServiceImpl;
import com.servlet.base.ModelBaseServlet;
import com.utils.JSONUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.*;
import java.util.List;
import java.util.Map;

public class StudentServlet extends ModelBaseServlet {

    StudentService studentService = new StudentServiceImpl();


    //学生登录（by周才邦）
    public void doLogin(HttpServletRequest request, HttpServletResponse response) {
        String studentNumber = request.getParameter("studentNumber");
        String password = request.getParameter("password");
        Student student = new Student(studentNumber, password);
        //调用service层处理登录
        try {
            student = studentService.doLogin(student);
            //如果没有报错，则登陆成功,将student存入session
            HttpSession session = request.getSession();
            session.setAttribute(Constants.STUDENT_SESSION_KEY, student);
            //登陆成功返回登陆成功的JSON格式result
            JSONUtils.writeResult(response, new Result(true, Constants.LOGIN_SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            //失败则返回失败,返回错误信息
            JSONUtils.writeResult(response, new Result(false, e.getMessage()));
        }
    }

    //学生注册（by周才邦）
    public void doRegister(HttpServletRequest request, HttpServletResponse response) {
        try {
            //获取所有请求参数
            Map<String, String[]> parameterMap = request.getParameterMap();

            //获取用户输入的验证码
            String code = parameterMap.get("code")[0];
            String checkCode = (String) request.getSession().getAttribute(Constants.CHECK_CODE);
            //验证密码，忽略大小写
            if (checkCode.equalsIgnoreCase(code)) {
                Student student = new Student();
                BeanUtils.populate(student, parameterMap);
                studentService.doRegister(student);
                //注册成功自动登录
                request.getSession().setAttribute(Constants.STUDENT_SESSION_KEY, student);
                JSONUtils.writeResult(response, new Result(true, Constants.REGISTER_SUCCESS));
            } else {
                JSONUtils.writeResult(response, new Result(false, Constants.WRONG_CHECK_CODE));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new Result(false, e.getMessage()));
        }
    }

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
    public void findAllStudent(HttpServletRequest request, HttpServletResponse response){
        try {
            List<Student> students = studentService.getStudentList();
            JSONUtils.writeResult(response, new Result(true,Constants.QUERY_SUCCESS ,students));
        } catch (Exception e) {
            e.printStackTrace();
            //失败则返回失败,返回错误信息
            JSONUtils.writeResult(response, new Result(false, e.getMessage()));
        }
    }

    //通过tutorId查学生,  zyq
    public void findStudentListByTutorId(HttpServletRequest request, HttpServletResponse response){
        String tutorId = request.getParameter("tutorId");
        int id= Integer.parseInt(tutorId);
        try {
            List<Student> students = studentService.getStudentListByTutorId(id);
            JSONUtils.writeResult(response, new Result(true,Constants.QUERY_SUCCESS ,students));
        } catch (Exception e) {
            e.printStackTrace();
            //失败则返回失败,返回错误信息
            JSONUtils.writeResult(response, new Result(false, e.getMessage()));
        }
    }

    //根据学生Id查询详细信息，by郑应啟
    public void findStudentById(HttpServletRequest request, HttpServletResponse response){
        String studentId = request.getParameter("studentId");
        int id= Integer.parseInt(studentId);
        try {
            Student student = studentService.getStudentById(id);
            JSONUtils.writeResult(response, new Result(true,Constants.QUERY_SUCCESS ,student));
        } catch (Exception e) {
            e.printStackTrace();
            //失败则返回失败,返回错误信息
            JSONUtils.writeResult(response, new Result(false, e.getMessage()));
        }
    }

    //学生登出
    public void doLogout(HttpServletRequest request,HttpServletResponse response){
        request.getSession().invalidate();
        JSONUtils.writeResult(response,new Result(true,Constants.LOGOUT));
    }

}
