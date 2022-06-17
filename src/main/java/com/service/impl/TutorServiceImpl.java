package com.service.impl;

import com.bean.Tutor;
import com.constant.Constants;
import com.dao.TutorDao;
import com.dao.impl.TutorDaoImpl;
import com.service.TutorService;
import com.utils.MD5Util;

public class TutorServiceImpl implements TutorService {

    TutorDao tutorDao = new TutorDaoImpl();

    @Override
    public Tutor doLogin(Tutor tutor) {
        Tutor findByNumber = tutorDao.findByNumber(tutor.getNumber());
        if (findByNumber != null) {
            String encode = MD5Util.encode(findByNumber.getPassword());
            if (encode.equals(tutor.getPassword())) {
                return findByNumber;
            } else {
                throw new RuntimeException(Constants.WRONG_PASSWORD);
            }
        }
        throw new RuntimeException(Constants.WRONG_NUMBER);
    }

    @Override
    public void doRegister(Tutor tutor) {
        //判断账号是否存在
        Tutor byTutorNumber = tutorDao.findByNumber(tutor.getNumber());
        if (byTutorNumber != null) {
            throw new RuntimeException(Constants.STUDENT_NUMBER_EXISTS);
        }
        //加密明文密码
        String encode = MD5Util.encode(tutor.getPassword());
        tutor.setPassword(encode);
        tutorDao.add(tutor);
    }
}
