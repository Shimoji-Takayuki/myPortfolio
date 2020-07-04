package com.jspSchedule;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.Filter;
import javax.servlet.FilterChain;

public class AuthFilter implements Filter {

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) {

        try {
            HttpSession session = ((HttpServletRequest) request).getSession();

            if (session == null) {
                session = ((HttpServletRequest) request).getSession(true);

                ((HttpServletResponse) response).sendRedirect("/jspScheduleManager/schedule/Login.jsp");
            } else {
                Object loginCheck = session.getAttribute("login");
                if (loginCheck == null) {
                    System.out.println("loginPageへリダイレクトされます");
                    session.setAttribute("status", null);
                    ((HttpServletResponse) response).sendRedirect("/jspScheduleManager/schedule/Login.jsp");
                }
            }

            chain.doFilter(request, response);

        } catch (ServletException se) {
        } catch (IOException e) {
        }

    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    public void destroy() {
    }
}
