package com.servlet.model;

import com.bean.Result;
import com.bean.Tutor;
import com.constant.Constants;
import com.service.TutorService;
import com.service.impl.TutorServiceImpl;
import com.servlet.base.ModelBaseServlet;
import com.utils.JSONUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.*;
import java.util.Map;

public class TutorServlet extends ModelBaseServlet {

    TutorService tutorService = new TutorServiceImpl();

    //教师登录（by周才邦）
    public void doLogin(HttpServletRequest request, HttpServletResponse response) {
        String number = request.getParameter("number");
        String password = request.getParameter("password");
        Tutor tutor = new Tutor(number, password);
        //调用service层处理
        try {
            tutor = tutorService.doLogin(tutor);
            //没有报错则登录
            HttpSession session = request.getSession();
            session.setAttribute(Constants.TUTOR_SESSION_KEY, tutor);
            JSONUtils.writeResult(response, new Result(true, Constants.LOGIN_SUCCESS));
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new Result(false, e.getMessage()));
        }
    }

    //教师注册（by周才邦）
    public void doRegister(HttpServletRequest request, HttpServletResponse response) {
        try {
            Map<String, String[]> parameterMap = request.getParameterMap();
            //获取输入验证码
            String code = parameterMap.get("code")[0];
            HttpSession session = request.getSession();
            String checkCode = (String) session.getAttribute(Constants.CHECK_CODE);
            if (code.equalsIgnoreCase(checkCode)) {
                Tutor tutor = new Tutor();
                BeanUtils.populate(tutor, parameterMap);
                tutorService.doRegister(tutor);
                //注册成功自动登录
                session.setAttribute(Constants.TUTOR_SESSION_KEY, tutor);
                JSONUtils.writeResult(response, new Result(true, Constants.REGISTER_SUCCESS));
            } else {
                JSONUtils.writeResult(response, new Result(false, Constants.WRONG_CHECK_CODE));
            }
        } catch (Exception e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new Result(false, e.getMessage()));
        }
    }

}
