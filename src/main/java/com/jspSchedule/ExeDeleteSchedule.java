package com.jspSchedule;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import com.utilClass.*;

public class ExeDeleteSchedule extends HttpServlet {

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
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            log("SQLException:" + e.getMessage());
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        int id;
        int year;
        int month;

        String param = req.getParameter("ID");
        if (param == null || param.length() == 0) {
            id = -999;
        } else {
            try {
                id = Integer.parseInt(param);
            } catch (NumberFormatException e) {
                id = -999;
            }
        }

        param = req.getParameter("YEAR");
        if (param == null || param.length() == 0) {
            year = -999;
        } else {
            try {
                year = Integer.parseInt(param);
            } catch (NumberFormatException e) {
                year = -999;
            }
        }

        param = req.getParameter("MONTH");
        if (param == null || param.length() == 0) {
            month = -999;
        } else {
            try {
                month = Integer.parseInt(param);
            } catch (NumberFormatException e) {
                month = -999;
            }
        }

        /* ID���s���Ȓl�ŗ����ꍇ�̓p�����[�^�����ŁuMonthView�v�փ��_�C���N�g���� */
        if (id == -999) {
            res.sendRedirect("/jspScheduleManager/schedule/MonthView");
        }

        try {
            String sql = "delete from schedule where id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);

            pstmt.executeUpdate();

            pstmt.close();

        } catch (SQLException e) {
            System.out.println("SQLException:" + e.getMessage());
        }

        StringBuffer sb = new StringBuffer();
        sb.append("/jspScheduleManager/schedule/MonthView");
        sb.append("?YEAR=");
        sb.append(year);
        sb.append("?MONTH=");
        sb.append(month);
        res.sendRedirect(new String(sb));

    }
}
