package com.jspSchedule;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import com.utilClass.*;

public class LoginCheck extends HttpServlet {

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

    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException, ServletException {

        // 入力パラメータを取得
        String user = request.getParameter("user");
        String pass = request.getParameter("pass");

        HttpSession session = request.getSession(true);

        boolean check = authUser(user, pass, session);
        if (check) {
            /* 認証済みにセット */
            session.setAttribute("login", "OK");

            /* 認証成功後は必ずMonthViewサーブレットを呼びだす */
            response.sendRedirect("/jspScheduleManager/schedule/MonthView");
        } else {
            /* 認証に失敗したら、ログイン画面に戻す */
            session.setAttribute("status", "Not Auth");
            response.sendRedirect("/jspScheduleManager/schedule/Login.jsp");
        }
    }

    private boolean authUser(String user, String pass, HttpSession session) {

        if (user == null || user.length() == 0 || pass == null || pass.length() == 0){
            return false;
        }

        try {
            String sql = "SELECT id, " +
                         "role, " +
                         "user " +
                         "FROM usertable " +
                         "WHERE user = ? " +
                         "and pass = ?";

            PreparedStatement pstmt = conn.prepareStatement(sql);
            
            pstmt.setString(1, user);
            pstmt.setString(2, EncryptionPass.encryptPass(user, pass));
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()){
                int userid = rs.getInt("id");
                int role = rs.getInt("role");
                String userName = rs.getString("user");

                session.setAttribute("userid", Integer.toString(userid));
                session.setAttribute("role", Integer.toString(role));
                session.setAttribute("userName", userName);

                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            log("SQLException:" + e.getMessage());
            return false;
        }
    }
}
