package com.jspSchedule;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import com.utilClass.*;

public class ExeInsertSchedule extends HttpServlet {

    /**
     * デフォルトシリアルバージョンID
     */
    private static final long serialVersionUID = 1L;

    protected Connection conn = null;

    public void init() throws ServletException {

        conn = DBConnection.createConnection();

        if (conn == null) {
            log("DB接続に失敗しました。ログを確認して下さい。");
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

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        // 日本語の文字化けを回避
        req.setCharacterEncoding("Shift_JIS");

        int year;
        int month;
        int day;
        int shour;
        int sminute;
        int ehour;
        int eminute;
        String plan;
        String memo;

        String param = req.getParameter("YEAR");
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

        param = req.getParameter("DAY");
        if (param == null || param.length() == 0) {
            day = -999;
        } else {
            try {
                day = Integer.parseInt(param);
            } catch (NumberFormatException e) {
                day = -999;
            }
        }

        param = req.getParameter("SHOUR");
        if (param == null || param.length() == 0) {
            shour = -999;
        } else {
            try {
                shour = Integer.parseInt(param);
            } catch (NumberFormatException e) {
                shour = -999;
            }
        }

        param = req.getParameter("SMINUTE");
        if (param == null || param.length() == 0) {
            sminute = -999;
        } else {
            try {
                sminute = Integer.parseInt(param);
            } catch (NumberFormatException e) {
                sminute = -999;
            }
        }

        param = req.getParameter("EHOUR");
        if (param == null || param.length() == 0) {
            ehour = -999;
        } else {
            try {
                ehour = Integer.parseInt(param);
            } catch (NumberFormatException e) {
                ehour = -999;
            }
        }

        param = req.getParameter("EMINUTE");
        if (param == null || param.length() == 0) {
            eminute = -999;
        } else {
            try {
                eminute = Integer.parseInt(param);
            } catch (NumberFormatException e) {
                eminute = -999;
            }
        }

        param = req.getParameter("PLAN");
        if (param == null || param.length() == 0) {
            plan = "";
        } else {
            try {
                plan = param;
            } catch (NumberFormatException e) {
                plan = "";
            }
        }

        param = req.getParameter("MEMO");
        if (param == null || param.length() == 0) {
            memo = "";
        } else {
            try {
                memo = param;
            } catch (NumberFormatException e) {
                memo = "";
            }
        }

        /* 日付が不正な値で来た場合はパラメータ無しで「MonthView」へリダイレクトする */
        if (year == -999 || month == -999 || day == -999) {
            res.sendRedirect("/jspScheduleManager/schedule/MonthView");
        }

        String dateStr = year + "-" + month + "-" + day;

        String startTimeStr = shour + ":" + sminute + ":00";
        String endTimeStr = ehour + ":" + eminute + ":00";
        /* 日付が指定されていない場合は、開始及び終了時刻をNULLとして登録する */
        if (shour == -999 || sminute == -999 || ehour == -999 || eminute == -999) {
            startTimeStr = null;
            endTimeStr = null;
        }

        /* ユーザー情報を取り出す */
        HttpSession session = req.getSession(false);
        String tmpuserid = (String) session.getAttribute("userid");
        int userid = 0;
        if (tmpuserid != null) {
            userid = Integer.parseInt(tmpuserid);
        }

        try {
            String sql = "insert into schedule " + "(userid, scheduledate, starttime, endtime, schedule, schedulememo) "
                    + "values (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setInt(1, userid);
            pstmt.setString(2, dateStr);
            pstmt.setString(3, startTimeStr);
            pstmt.setString(4, endTimeStr);
            pstmt.setString(5, plan);
            pstmt.setString(6, memo);

            pstmt.executeUpdate();

            pstmt.close();

        } catch (SQLException e) {
            System.out.println("SQLException:" + e.getMessage());
        }

        StringBuffer sb = new StringBuffer();
        sb.append("/jspScheduleManager/schedule/MonthView");
        sb.append("?YEAR=");
        sb.append(year);
        sb.append("&MONTH=");
        sb.append(month - 1);
        res.sendRedirect(new String(sb));
    }
}
