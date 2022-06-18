package com.service;

import com.bean.Tutor;

public interface TutorService {
    Tutor doLogin(Tutor tutor);

    void doRegister(Tutor tutor);
}
