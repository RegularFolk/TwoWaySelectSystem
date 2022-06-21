package com.servlet.model;

import com.bean.Result;
import com.servlet.base.ModelBaseServlet;
import com.utils.JSONUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class CommonPortalServlet extends ModelBaseServlet {

    public void toRegisterPage(HttpServletRequest request, HttpServletResponse response) {
        try {
            processTemplate("register", request, response);
        } catch (IOException e) {
            e.printStackTrace();
            JSONUtils.writeResult(response, new Result(false, e.getMessage()));
        }
    }
}
