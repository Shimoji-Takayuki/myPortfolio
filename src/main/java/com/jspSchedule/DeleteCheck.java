package com.jspSchedule;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import com.utilClass.*;

public class DeleteCheck extends HttpServlet {

    /**
     * �f�t�H���g�V���A���o�[�W����ID
     */
    private static final long serialVersionUID = 1L;

    protected Connection conn = null;

    public void init() throws ServletException {

        conn = DBConnection.createConnection();
        
        if (conn == null) {
            log("DB�ڑ��Ɏ��s���܂����B���O���m�F���ĉ������B");
        }
    }

    public void destory() {
        try {
            if (conn != null){
                conn.close();
            }
        } catch (SQLException e){
            log("SQLException:" + e.getMessage());
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

        int year = -1;
        int month = -1;
        int day = -1;
        int currentscheduleid;
        String currentStartTime = "";
        String currentEndTime = "";
        String currentSchedule = "";
        String currentMemo = "";

        String param = req.getParameter("ID");
        if (param == null || param.length() == 0) {
            currentscheduleid = -1;
        } else {
            try{
                currentscheduleid = Integer.parseInt(param);
            } catch (NumberFormatException e) {
                currentscheduleid = -1;
            }
        }

        /* �p�����[�^���s���ȏꍇ�̓g�b�v�y�[�W�փ��_�C���N�g */
        if (currentscheduleid == -1) {
            res.sendRedirect("/jspScheduleManager/schedule/index.jsp");
        }

        /* ���[�U�[�������o�� */
        HttpSession session = req.getSession(false);
        String userName = (String)session.getAttribute("userName");

        try {
            String sql = "SELECT scheduledate, " +
                         "IFNULL(time_format(starttime, '%H:%i'), '') starttime, " +
                         "IFNULL(time_format(endtime, '%H:%i'), '') endtime, " +
                         "schedule, " +
                         "schedulememo " +
                         "FROM schedule " +
                         "WHERE id = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, currentscheduleid);
            ResultSet rs = pstmt.executeQuery();

            rs.next();
            String scheduledate = rs.getString("scheduledate");
            String yearStr = scheduledate.substring(0, 4);
            String monthStr = scheduledate.substring(5, 7);
            String dayStr = scheduledate.substring(8, 10);

            year = Integer.parseInt(yearStr);
            month = Integer.parseInt(monthStr) - 1;
            day = Integer.parseInt(dayStr);

            currentStartTime = rs.getString("starttime");
            currentEndTime = rs.getString("endtime");
            currentSchedule = rs.getString("schedule");
            currentMemo = rs.getString("schedulememo");

            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            log("SQLException:" + e.getMessage());
        }

        // JSP�֓n���Z�b�V�����p�����[�^���Z�b�g
        session.setAttribute("ID", currentscheduleid);
        session.setAttribute("userName", userName);
        session.setAttribute("year", year);
        session.setAttribute("month", month);
        session.setAttribute("date", year + "�N" + (month + 1) + "��" + day + "��");
        session.setAttribute("dateTime", currentStartTime + " - " + currentEndTime);
        session.setAttribute("schedule", currentSchedule);
        session.setAttribute("memo", currentMemo);

        res.sendRedirect("/jspScheduleManager/schedule/DeleteCheck.jsp");
    }

}
