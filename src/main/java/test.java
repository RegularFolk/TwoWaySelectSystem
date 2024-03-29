import com.bean.*;
import com.bean.Message;
import com.bean.ResultMessage;
import com.bean.Student;
import com.bean.Tutor;
import com.constant.Constants;
import com.dao.ResultDao;
import com.dao.StudentDao;
import com.dao.TutorDao;
import com.dao.TutorInfoDao;
import com.dao.impl.*;
import com.service.*;
import com.service.impl.*;
import com.utils.JDBCUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

public class test {
    public static void main(String[] args) {
        Connection connection = JDBCUtil.getConnection();
        System.out.println(connection);
    }

    TutorService tutorService = new TutorServiceImpl();
    StudentService studentService = new StudentServiceImpl();
    MessageService messageService = new MessageServiceImpl();
    ResultService resultService = new ResultServiceImpl();
    ResultDao resultDao = new ResultDaoImpl();
    TutorDao tutorDao = new TutorDaoImpl();
    TutorInfoDao tutorInfoDao = new TutorInfoDaoImpl();
    StudentDao studentDao = new StudentDaoImpl();
    EventService eventService=new EventServiceImpl();

    @Test  //测试教师登录
    public void testTutorDoLogin() {
        String number = "123456";
        String password = "123456";
        Tutor tutor = new Tutor(number, password);
        //调用service层处理
        try {
            tutor = tutorService.doLogin(tutor);
            //没有报错则登录
            System.out.println(new ResultMessage(true, Constants.LOGIN_SUCCESS));
            System.out.println(tutor);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(new ResultMessage(false, e.getMessage()));
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
                System.out.println(new ResultMessage(true, Constants.REGISTER_SUCCESS));
            } else {
                System.out.println(new ResultMessage(false, Constants.WRONG_CHECK_CODE));
            }
        } catch (Exception e) {
            System.out.println(new ResultMessage(false, e.getMessage()));
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
                System.out.println(new ResultMessage(true, Constants.REGISTER_SUCCESS));
            } else {
                System.out.println(new ResultMessage(false, Constants.WRONG_CHECK_CODE));
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(new ResultMessage(false, e.getMessage()));
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
            System.out.println(new ResultMessage(true, Constants.LOGIN_SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            //失败则返回失败,返回错误信息
            System.out.println(new ResultMessage(false, e.getMessage()));
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

//        Student studentById = studentService.getStudentById(4);
//        StudentInfo infoByStudentId = studentService.getInfoByStudentId(4);
//        StudentInfo infoByInfoId = studentService.getInfoByInfoId(1);
//
////        StudentInfo studentInfo=new StudentInfo();
////        studentInfo.setBirthday("1800");
////        Student student = studentService.updateStudentInfo(studentById, studentInfo);
//////        System.out.println(student);
//        System.out.println(studentById);

//        studentService.getStudentListByTutorId();
//        List<Student> studentListByMajorId = studentService.getStudentListByMajorId(1);
//        System.out.println(studentListByMajorId);

//        studentService.updatePassword("0",studentById);

//        System.out.println(studentService.getStudentList());

        List<Tutor> tutorList = tutorService.getTutorList();
        tutorList.forEach(tutor -> tutor.setTutorInfo(tutorService.getInfoByTutorId(tutor.getTutorInfoId())));

//        Student byId = studentService.getStudentById(1);
        System.out.println(tutorList);
    }

    @Test //tutor信息增删改查
    public void testTutor() {
//        List<Tutor> list;
//        List<TutorInfo> list1;
//
        Tutor tutorById = tutorService.getTutorById(20);
//        List<Tutor> tutorList = tutorService.getTutorList();
//
//        TutorInfo infoByTutorId = tutorService.getInfoByTutorId(1);
//
//        TutorInfo infoByInfoId = tutorService.getInfoByInfoId(1);
//        List<TutorInfo> infoList = tutorService.getInfoList();
//
//        TutorInfo tutorInfo = new TutorInfo();
//        tutorInfo.setGender(11);
//
//        Tutor tutor = tutorService.updateTutorInfo(tutorById, tutorInfo);
//
//        System.out.println(tutor);
//
//        List<Tutor> tutorByMajorId = tutorService.getTutorByMajorId(1);
//        System.out.println(tutorByMajorId);

        tutorService.updatePassword("123456",tutorById);

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
    public void testFindByReceiverId() {
        MessageDaoImpl messageDao = new MessageDaoImpl();
        Integer receiverId = 1;
        List<Message> messageList = messageDao.findByReceiverId(receiverId);
        System.out.println(messageList);
    }

    @Test //测试私信查询BySenderId
    public void testFindBySenderId() {
        MessageDaoImpl messageDao = new MessageDaoImpl();
        Integer senderId = 1;
        List<Message> messageList = messageDao.findBySenderId(senderId);
        System.out.println(messageList);
    }

    @Test
    public void testDateFormat() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(new Date());
        System.out.println(format);
    }

    @Test
    public void testMessage() {
//        Date date=new Date();
//        Timestamp timestamp=new Timestamp(date.getTime());
//        String time= String.valueOf(timestamp);
//        messageService.sendMessageById(-1,+1,"text",time);
//        List<Message> messages=messageService.getMessage(+1,-1);
//        List<Message> list = messageService.getMessageList(4);
        List<Student> students = studentService.getStudentList();
        System.out.println(students);
    }

    @Test
    public void testResult() {

        List<IntBean> tutorList = resultDao.findTutorByEventId(8);
//
////        for (IntBean intBean:tutorList){
////            s = s +","+ String.valueOf(intBean.getId());
////        }
//
//        List<Tutor> tutors = tutorDao.findTutorResult(tutorList);
//
//        Result r = resultService.getResultByEventId(8);
//
//        System.out.println(tutorList.size());

//        resultDao.findStudentByEventIdTutor(8,4);
//        List<IntBean> studentList = resultDao.findStudentByEventIdTutor(8, 4);
//        List<Student> students = studentDao.findStudentResult(studentList);
        Result resultByEventId = resultService.getResultByEventId(8);
        Result result = resultService.getResultByEventId(8);
        List<Tutor> tutors = result.getTutors();
        System.out.println(tutors);

    }

    @Test
    public void testDAo() {

        TutorInfo tutorInfo=new TutorInfo();
        Tutor tutor=new Tutor();
        tutor=tutorDao.findById(1);
        tutorInfo.setGender(1);
        tutorInfo.setLessons("123");
        tutorInfoDao.updateInfo(1, tutorInfo);
        Tutor id = tutorService.getTutorById(1);
        System.out.println(id);
        tutorService.updateTutorInfo(tutor,tutorInfo);

    }

    @Test
    public void testEvent1(){
        List<Event> events = eventService.getFullAllEventsByStudentId(1);
        for (Event event : events) {
            Result result = resultService.getResultByEventIdStudentId(event.getId(),1);
            if (event.getStatus() == 2) {
                event.setResult(result);
            }
        }
        System.out.println(events);
    }
}
