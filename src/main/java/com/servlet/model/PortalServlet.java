package com.servlet.model;

import com.servlet.base.ViewBaseServlet;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class PortalServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processTemplate("index", request, response);
    }
}
