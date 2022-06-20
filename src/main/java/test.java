import com.bean.*;
import com.bean.Message;
import com.bean.Result;
import com.bean.Student;
import com.bean.Tutor;
import com.constant.Constants;
import com.dao.impl.MessageDaoImpl;
import com.service.StudentService;
import com.service.TutorService;
import com.service.impl.StudentServiceImpl;
import com.service.impl.TutorServiceImpl;
import com.utils.JDBCUtil;
import com.utils.JSONUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class test {
    public static void main(String[] args) {
        Connection connection = JDBCUtil.getConnection();
        System.out.println(connection);
    }

    TutorService tutorService = new TutorServiceImpl();
    StudentService studentService = new StudentServiceImpl();

    @Test  //测试教师登录
    public void testTutorDoLogin() {
        String number = "123456";
        String password = "123456";
        Tutor tutor = new Tutor(number, password);
        //调用service层处理
        try {
            tutor = tutorService.doLogin(tutor);
            //没有报错则登录
            System.out.println(new Result(true, Constants.LOGIN_SUCCESS));
            System.out.println(tutor);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(new Result(false, e.getMessage()));
        }
    }


    @Test //测试教师注册
    public void testTutorRegister() {
        try {
            Map<String, String[]> parameterMap = new HashMap<>();
            parameterMap.put("code", new String[]{"aaaa"});
            parameterMap.put("number", new String[]{"888888"});
            parameterMap.put("password", new String[]{"123456"});
            parameterMap.put("name", new String[]{"kevin"});
            //获取输入验证码
            String code = parameterMap.get("code")[0];
            String checkCode = "aaaa";
            if (code.equalsIgnoreCase(checkCode)) {
                Tutor tutor = new Tutor();
                BeanUtils.populate(tutor, parameterMap);
                tutorService.doRegister(tutor);
                //注册成功自动登录
                System.out.println(new Result(true, Constants.REGISTER_SUCCESS));
            } else {
                System.out.println(new Result(false, Constants.WRONG_CHECK_CODE));
            }
        } catch (Exception e) {
            System.out.println(new Result(false, e.getMessage()));
        }
    }

    @Test //测试学生注册
    public void testStudentRegister() {
        try {
            //获取所有请求参数
            Map<String, String[]> parameterMap = new HashMap<>();
            parameterMap.put("studentNumber", new String[]{"123456789"});
            parameterMap.put("password", new String[]{"123456"});
            parameterMap.put("studentName", new String[]{"轰轰"});
            //获取用户输入的验证码
            String code = "code";
            String checkCode = "code";
            //验证密码，忽略大小写
            if (checkCode.equalsIgnoreCase(code)) {
                Student student = new Student();
                BeanUtils.populate(student, parameterMap);
                studentService.doRegister(student);
                //注册成功自动登录
                System.out.println(student);
                System.out.println(new Result(true, Constants.REGISTER_SUCCESS));
            } else {
                System.out.println(new Result(false, Constants.WRONG_CHECK_CODE));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(new Result(false, e.getMessage()));
        }
    }

    @Test //测试学生登录
    public void testStudentDoLogin() {
        String studentNumber = "123456789";
        String password = "123456";
        Student student = new Student(studentNumber, password);
        //调用service层处理登录
        try {
            student = studentService.doLogin(student);
            //如果没有报错，则登陆成功,将student存入session
            //登陆成功返回登陆成功的JSON格式result
            System.out.println(student);
            System.out.println(new Result(true, Constants.LOGIN_SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            //失败则返回失败,返回错误信息
            System.out.println(new Result(false, e.getMessage()));
        }
    }

    @Test //测试学生信息增删改查
    public void testStudent() {
        List<Student> list;
        List<StudentInfo> list1;
        //list = studentService.getStudentList();
        //list=studentService.getStudentListByStatus(0);
        //list1=studentService.getInfoList();
        //System.out.println(list1);

        Student studentById = studentService.getStudentById(4);
        StudentInfo infoByStudentId = studentService.getInfoByStudentId(4);
        StudentInfo infoByInfoId = studentService.getInfoByInfoId(1);

//        StudentInfo studentInfo=new StudentInfo();
//        studentInfo.setBirthday("1800");
//        Student student = studentService.updateStudentInfo(studentById, studentInfo);
////        System.out.println(student);
        System.out.println(studentById);

//        studentService.getStudentListByTutorId();
//        List<Student> studentListByMajorId = studentService.getStudentListByMajorId(1);
//        System.out.println(studentListByMajorId);

//        studentService.updatePassword("0",studentById);

//        System.out.println(studentService.getStudentList());

    }

    @Test //tutor信息增删改查
    public void testTutor(){
        List<Tutor> list;
        List<TutorInfo> list1;

        Tutor tutorById = tutorService.getTutorById(1);
        List<Tutor> tutorList = tutorService.getTutorList();

        TutorInfo infoByTutorId = tutorService.getInfoByTutorId(1);

        TutorInfo infoByInfoId = tutorService.getInfoByInfoId(1);
        List<TutorInfo> infoList = tutorService.getInfoList();

        TutorInfo tutorInfo=new TutorInfo();
        tutorInfo.setGender(11);

        Tutor tutor = tutorService.updateTutorInfo(tutorById, tutorInfo);

        System.out.println(tutor);

        List<Tutor> tutorByMajorId = tutorService.getTutorByMajorId(1);
        System.out.println(tutorByMajorId);

    }

    @Test //测试发送私信
    public void testSendById() {
        MessageDaoImpl messageDao = new MessageDaoImpl();
        Integer receiverId = 1;
        Integer senderId = 1;
        String text = "aaa";
        String time = "6.20";
        messageDao.sandById(receiverId, senderId, text, time);
    }

    @Test //测试私信查询ByReceiverId
    public void testFindByReceiverId(){
        MessageDaoImpl messageDao = new MessageDaoImpl();
        Integer receiverId = 1;
        List<Message> messageList = messageDao.findByReceiverId(receiverId);
        System.out.println(messageList);
    }

    @Test //测试私信查询BySenderId
    public void testFindBySenderId(){
        MessageDaoImpl messageDao = new MessageDaoImpl();
        Integer senderId = 1;
        List<Message> messageList = messageDao.findBySenderId(senderId);
        System.out.println(messageList);
    }

}
