package com.jspSchedule;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Calendar;
import java.sql.*;
import com.utilClass.*;
import org.json.JSONObject;
import org.json.JSONArray;

public class NewSchedule extends HttpServlet{

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
        try{
            if (conn != null){
                conn.close();
            }
        }catch (SQLException e){
            log("SQLException:" + e.getMessage());
        }
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

        int year;
        int month;
        int day;
        JSONArray scheduleInfoList = new JSONArray();

        String param = req.getParameter("YEAR");
        if (param == null || param.length() == 0){
            year = -999;
        }else{
            try{
                year = Integer.parseInt(param);
            }catch (NumberFormatException e){
                year = -999;
            }
        }

        param = req.getParameter("MONTH");
        if (param == null || param.length() == 0) {
            month = -999;
        } else {
            try {
                month = Integer.parseInt(param);
            } catch (NumberFormatException e){
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

        /* パラメータが指定されていない場合は本日の日付を設定 */
        if (year == -999 || month == -999 || day == -999) {
            Calendar calendar = Calendar.getInstance();
            year = calendar.get(Calendar.YEAR);
            month = calendar.get(Calendar.MONTH);
            day = calendar.get(Calendar.DATE);
        }

        /* ユーザー情報を取り出す */
        HttpSession session = req.getSession(false);
        String userName = (String)session.getAttribute("userName");
        String tmpuserid = (String)session.getAttribute("userid");
        int userid = 0;
        if (tmpuserid != null) {
            userid = Integer.parseInt(tmpuserid);
        }

        try {
            String sql = "SELECT id, " +
                         "IFNULL(time_format(starttime, '%H:%i'), '') starttime, " +
                         "IFNULL(time_format(endtime, '%H:%i'), '') endtime, " +
                         "schedule " +
                         "FROM schedule " +
                         "WHERE userid = ? " +
                         "and scheduledate = ? " +
                         "ORDER BY starttime";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            String startDateStr = year + "-" + (month + 1) + "-" + day;
            pstmt.setInt(1, userid);
            pstmt.setString(2, startDateStr);

            ResultSet rs = pstmt.executeQuery();

            while(rs.next()) {
                int id = rs.getInt("id");
                String startTime = rs.getString("starttime");
                String endTime = rs.getString("endtime");
                String schedule = rs.getString("schedule");

                JSONObject jsonSchedule = new JSONObject();

                if (startTime.isEmpty() || endTime.isEmpty()) {
                    jsonSchedule.put("id", id);
                    jsonSchedule.put("time", "");
                    jsonSchedule.put("schedule", schedule);
                } else {
                    String time = startTime + "-" + endTime;
                    jsonSchedule.put("id", id);
                    jsonSchedule.put("time", time);
                    jsonSchedule.put("schedule", schedule);
                    jsonSchedule.put("startTime", startTime);
                    jsonSchedule.put("endTime", endTime);
                }
                scheduleInfoList.put(jsonSchedule);
            }

            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            log("SQLException:" + e.getMessage());
        }

        // JSPへ渡すセッションパラメータをセット
        session.setAttribute("userName", userName);
        session.setAttribute("year", year);
        session.setAttribute("month", month);
        session.setAttribute("day", day);
        session.setAttribute("allSchedule", scheduleInfoList);
        session.setAttribute("lastDay", getMonthLastDay(year, month, day));
        res.sendRedirect("/jspScheduleManager/schedule/NewSchedule.jsp");
    }

    private int getMonthLastDay(int year, int month, int day) {

        Calendar calendar = Calendar.getInstance();

        /* 今月が何日までかを確認する */
        calendar.set(year, month + 1, 0);
        int thisMonthlastDay = calendar.get(Calendar.DATE);

        return thisMonthlastDay;
    }

}
