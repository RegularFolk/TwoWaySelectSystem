package com.constant;

/**
 * 存储常量以解决硬编码问题
 */

public class Constants {
    public static final String LOGIN_SUCCESS = "登陆成功！";
    public static final String WRONG_PASSWORD = "密码错误，登录失败！";
    public static final String WRONG_NUMBER = "账号错误，登录失败！";
    public static final String WRONG_CHECK_CODE = "验证码错误！";
    public static final String STUDENT_SESSION_KEY = "loginStudent";
    public static final String REGISTER_SUCCESS = "注册成功！";
    public static final String STUDENT_NUMBER_EXISTS = "账号已存在";
    public static final String CHECK_CODE = "checkCode";
    public static final String TUTOR_SESSION_KEY = "LoginTutor";
    public static final String NOT_LOGGED_IN = "请先登录！ 或者点击退出登录后重新登录！";
    public static final String UPDATE_SUCCESS = "修改成功！";
    public static final String QUERY_SUCCESS = "查询成功!";
    public static final String LOGOUT = "登出成功！";
    public static final String SEND_SUCCESS="发送成功！";
    public static final String RECEIVE_SUCCESS="接收成功！";
    //三轮志愿
    public static final Integer FIRST_PREFERENCE = 1;
    public static final Integer SECOND_PREFERENCE = 2;
    public static final Integer THIRD_PREFERENCE = 3;

    public static final String WRONG_PREFERENCE = "请选择正确的志愿号";
    public static final String QUERY_FAIL = "查询失败！";
    public static final String UPDATE_PREFERENCE_SUCCESS = "更新志愿成功！";
    public static final String UPDATE_PREFERENCE_FAIL = "更新志愿失败！";
    public static final String GET_PREVIOUS_PREFERENCES = "成功获取从前志愿！";
    public static final String NO_PREVIOUS_PREFERENCES = "没有被记录的志愿！";
    public static final String START_EVENT_FAIL = "发起双选失败！";
    public static final String START_EVENT_SUCCESS = "发起双选成功！";
    public static final String LOGOUT_FAIL = "退出登录失败！";
    public static final Integer STUDENT_STATUS_NOT_CHOOSE = 0;  //学生未进行任何志愿选择
    public static final Integer STUDENT_STATUS_CHOSE = 1;       //学生已经做出志愿选择
    public static final Integer STUDENT_STATUS_CHOSEN = 2;      //学生已被选择
    public static final String GET_ALL_EVENT_SUCCESS = "获取所有双选事件成功！";
    public static final String GET_ALL_EVENT_FAIL = "获取所有双选事件失败！";

    //数据库中的event 的status
    public static final Integer EVENT_DISABLED = 0;
    public static final Integer EVENT_ENABLED = 1;
    public static final Integer EVENT_FINISHED = 2;

    //以下针对servletContext中的 EVENT_STATUS
    public static final Integer EVENT_SHUT = 0; //没有正在进行的双选事件
    public static final Integer EVENT_ONGOING = 1; //有双选事件正在进行
    public static final Integer EVENT_STUDENT_SUBMITTING = 2;//当前处于学生提交阶段
    public static final Integer EVENT_ROUND1 = 3;//当前处于导师第一轮选择阶段
    public static final Integer EVENT_ROUND2 = 4;//当前处于导师第二轮选择阶段
    public static final Integer EVENT_ROUND3 = 5;//当前处于导师第三轮选择阶段


    public static final String EVENT_CONTEXT_KEY = "contextEvent";
    public static final String EVENT_STATUS = "eventStatus";

    public static final String UPDATE_EVENT_STATUS_FAIL = "更新选课事件失败！请迅速联系管理员！";
    public static final String MORE_THAN_ONE_ONGOING_EVENT = "发生错误！正在进行的选课事件大于一个！请迅速联系管理员修正！";

    //导师最多拥有四个学生
    public static final Integer TUTOR_MAX_CAPACITY = 4;

    public static final String NO_ONGOING_EVENT = "没有正在进行的双选事件！";
    public static final String GET_ONGOING_EVENT = "获取到正在进行的双选事件！";
    public static final String EVENT_EXCEPTION_MESSAGE = "双选事件状态错误！";
    public static final String SUBMIT_MESSAGE = "当前处于学生提交阶段！";
    public static final String ROUND1_MESSAGE = "当前处于教师第一轮选择阶段！";
    public static final String ROUND2_MESSAGE = "当前处于教师第二轮选择阶段！";
    public static final String ROUND3_MESSAGE = "当前处于教师第三轮选择阶段！";
    public static final String CANCEL_EVENT_FAIL = "取消事件失败！请联系管理员修正！";
    public static final String ENABLE_EVENT_SUCCESS = "开启双选事件成功！";
    public static final String ENABLE_EVENT_FAIL = "开启双选事件失败！请迅速联系管理员修正！";
    public static final String GET_TUTOR_SUCCESS = "成功查询导师信息!";
    public static final String GET_PRE_STU_FAIL = "验证已选学生信息失败！请联系管理员！";
    public static final String GET_CANDIDATES_FAIL = "获取候选学生失败！请联系管理员！";
    public static final String TAKE_STUDENT_SUCCESS = "选择学生成功！";
    public static final String TAKE_STUDENT_FAIL = "选择学生失败！请联系管理员！";
    public static final String TOO_MANY_CHOSEN = "选择了过多的学生！";
    public static final String GET_PREVIOUS_TAKEN_FAIL = "获取选择记录失败！请联系管理员！";
    public static final String GET_NAME_FAIL = "获取姓名失败！";
    public static final String TO_WELCOME_FAIL = "进入欢迎页面失败！请联系管理员！";
}
