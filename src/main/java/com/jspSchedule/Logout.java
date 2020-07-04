package com.jspSchedule;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Logout extends HttpServlet {

    /**
     * �f�t�H���g�V���A���o�[�W����ID
     */
    private static final long serialVersionUID = 1L;
    
    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws IOException, ServletException{

        HttpSession session = req.getSession(true);
        session.invalidate();

        res.sendRedirect("/jspScheduleManager/schedule/Login.jsp");
    }
}
