package com.jspSchedule;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.Calendar;
import java.sql.*;
import com.utilClass.*;
import org.json.JSONObject;
import org.json.JSONArray;

public class EditSchedule extends HttpServlet{

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

    public void destory(){
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
            
        int year = -1;
        int month = -1;
        int day = -1;
        int currentscheduleid;
        String currentStartTime = "";
        String currentEndTime = "";
        String currentSchedule = "";
        String currentMemo = "";
        JSONArray scheduleInfoList = new JSONArray();

        String param = req.getParameter("ID");
        if (param == null || param.length() == 0){
            currentscheduleid = -1;
        }else{
            try{
                currentscheduleid = Integer.parseInt(param);
            }catch (NumberFormatException e){
                currentscheduleid = -1;
            }
        }

        /* パラメータが不正な場合はトップページへリダイレクト */
        if (currentscheduleid == -1){
            res.sendRedirect("/jspScheduleManager/schedule/top.html");
        }

        /* ユーザー情報を取り出す */
        HttpSession session = req.getSession(false);
        String userName = (String)session.getAttribute("userName");
        String tmpuserid = (String)session.getAttribute("userid");
        int userid = 0;
        if (tmpuserid != null){
            userid = Integer.parseInt(tmpuserid);
        }

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

        }catch (SQLException e){
            log("SQLException:" + e.getMessage());
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
        session.setAttribute("ID", currentscheduleid);
        session.setAttribute("userName", userName);
        session.setAttribute("year", year);
        session.setAttribute("month", month);
        session.setAttribute("day", day);
        session.setAttribute("sTime", currentStartTime);
        session.setAttribute("eTime", currentEndTime);
        session.setAttribute("schedule", currentSchedule);
        session.setAttribute("memo", currentMemo);
        session.setAttribute("lastDay", getMonthLastDay(year, month, day));
        session.setAttribute("allSchedule", scheduleInfoList);
        res.sendRedirect("/jspScheduleManager/schedule/EditSchedule.jsp");
    }

    private int getMonthLastDay(int year, int month, int day) {

        Calendar calendar = Calendar.getInstance();

        /* 今月が何日までかを確認する */
        calendar.set(year, month + 1, 0);
        int thisMonthlastDay = calendar.get(Calendar.DATE);

        return thisMonthlastDay;
    }

}
