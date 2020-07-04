package com.jspSchedule;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import com.utilClass.*;

public class ExeInsertUser extends HttpServlet {

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

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {

        // 入力パラメータを取得
        String user = req.getParameter("user");
        String roleStr = req.getParameter("role");
        String pass = req.getParameter("pass");

        int role;
        if (roleStr == null || roleStr.length() == 0) {
            role = -1;
        } else {
            role = Integer.parseInt(roleStr);
        }

        HttpSession session = req.getSession(true);

        boolean check = createUser(user, pass, role);
        if (check) {
            session.setAttribute("CreateUser", "Success");
            res.sendRedirect("/jspScheduleManager/schedule/NewUser.jsp");
        } else {
            session.setAttribute("CreateUser", "Fail");
            res.sendRedirect("/jspScheduleManager/schedule/NewUser.jsp");
        }
    }

    /**
     * ユーザーデータの登録
     */
    protected boolean createUser(String user, String pass, int role) {

        if (user == null || user.length() == 0 || pass == null || pass.length() == 0 || role == -1) {
            return false;
        }

        try {

            String sql = "insert into usertable " + "(user, pass, role) " + "values (?, ?, ?)";

            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, user);
            pstmt.setString(2, EncryptionPass.encryptPass(user, pass)); // パスワードの暗号化
            pstmt.setInt(3, role);

            pstmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            log("SQLException:" + e.getMessage());
            return false;
        }
    }
}
