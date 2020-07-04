package com.jspSchedule;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Calendar;
import java.sql.*;

import com.utilClass.*;
import org.json.JSONObject;
import org.json.JSONArray;

public class MonthView extends HttpServlet {

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

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        int year;
        int month;

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

        /* パラメータが指定されていない場合は本日の日付を設定 */
        if (year == -999 || month == -999) {
            Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
        } else {
            if (month == 12) {
                month = 0;
                year++;
            }

            if (month == -1) {
                month = 11;
                year--;
            }
        }

        /* ユーザー情報を取り出す */
        HttpSession session = req.getSession(false);
        Object tmp = session.getAttribute("userName");

        String userName;
        if (tmp == null) {
            userName = "";
        } else {
            userName = (String) tmp;
        }

        tmp = session.getAttribute("userid");
        int userid;
        if (tmp == null) {
            userid = 0;
        } else {
            userid = Integer.parseInt((String) tmp);
        }

        tmp = session.getAttribute("role");
        String role;
        if (tmp == null) {
            role = "";
        } else {
            role = (String) tmp;
        }

        // JSPへ渡すセッションパラメータをセット
        session.setAttribute("userName", userName);
        session.setAttribute("role", role);
        session.setAttribute("year", year);
        session.setAttribute("month", month);

        JSONArray jsoDateArray = setJsonDateArray(year, month);
        session.setAttribute("dateArray", jsoDateArray);
        session.setAttribute("dateLength", jsoDateArray.length());
        session.setAttribute("scheduleArray", setJsonSchedulerray(year, month, userid));

        // AuthFilterのログインページへのRidirectとの二重Redirectを防ぐ
        if (session.getAttribute("login") != null) {
            res.sendRedirect("/jspScheduleManager/schedule/MonthView.jsp");
        }
    }

    /* スケジュール登録へのリンクを設定する */
    private JSONArray setJsonSchedulerray(int year, int month, int userid) {

        JSONArray scheduleDateArray = new JSONArray();

        try {
            String sql = "SELECT id, " + "scheduledate, " + "IFNULL(time_format(starttime, '%H:%i'), '') starttime, "
                    + "IFNULL(time_format(endtime, '%H:%i'), '') endtime, " + "schedule " + "FROM schedule "
                    + "WHERE userid = ? " + "and DATE_FORMAT(scheduledate, '%y%m') = DATE_FORMAT(?, '%y%m') "
                    + "ORDER BY scheduledate, starttime";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            String startDateStr = year + "-" + (month + 1) + "-01";
            pstmt.setInt(1, userid);
            pstmt.setString(2, startDateStr + "%");

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                JSONObject scheduleObject = new JSONObject();
                scheduleObject.put("ID", rs.getString("id"));
                scheduleObject.put("date", Integer.parseInt(rs.getString("scheduledate").substring(8)));
                if (rs.getString("starttime").isEmpty() || rs.getString("endtime").isEmpty()) {
                    scheduleObject.put("time", "*");
                } else {
                    scheduleObject.put("time", rs.getString("starttime") + "-" + rs.getString("endtime"));
                }
                scheduleObject.put("schedule", rs.getString("schedule"));

                scheduleDateArray.put(scheduleObject);
            }

            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            log("SQLException:" + e.getMessage());
        }

        return scheduleDateArray;
    }

    private JSONArray setJsonDateArray(int year, int month) {

        JSONArray calendarDateArray = new JSONArray();
        Calendar calendar = Calendar.getInstance();

        /* 今月が何曜日から開始されているか確認する */
        calendar.set(year, month, 1);
        int startWeek = calendar.get(Calendar.DAY_OF_WEEK);

        /* 先月が何日までだったかを確認する */
        calendar.set(year, month, 0);
        int beforeMonthlastDay = calendar.get(Calendar.DATE);

        /* 今月が何日までかを確認する */
        calendar.set(year, month + 1, 0);
        int thisMonthlastDay = calendar.get(Calendar.DATE);

        // 先月の日付をJson配列へ格納
        if (startWeek != Calendar.SUNDAY) {
            for (int i = beforeMonthlastDay - startWeek + 2; i <= beforeMonthlastDay; i++) {
                JSONObject jsonDateElement = new JSONObject();

                jsonDateElement.put("month", month);
                jsonDateElement.put("date", i);
                calendarDateArray.put(jsonDateElement);
            }
        }

        // 今月の日付をJson配列へ格納
        for (int i = 1; i <= thisMonthlastDay; i++) {
            JSONObject jsonDateElement = new JSONObject();
            jsonDateElement.put("month", month + 1);
            jsonDateElement.put("date", i);

            calendarDateArray.put(jsonDateElement);
        }

        // 来月の日付をJson配列へ格納
        for (int i = 1; calendarDateArray.length() % 7 != 0; i++) {
            JSONObject jsonDateElement = new JSONObject();
            jsonDateElement.put("month", month + 2);
            jsonDateElement.put("date", i);

            calendarDateArray.put(jsonDateElement);
        }

        return calendarDateArray;
    }
}
