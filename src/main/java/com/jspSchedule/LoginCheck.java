package com.jspSchedule;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;
import com.utilClass.*;

public class LoginCheck extends HttpServlet {

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

        // ���̓p�����[�^���擾
        String user = request.getParameter("user");
        String pass = request.getParameter("pass");

        HttpSession session = request.getSession(true);

        boolean check = authUser(user, pass, session);
        if (check) {
            /* �F�؍ς݂ɃZ�b�g */
            session.setAttribute("login", "OK");

            /* �F�ؐ�����͕K��MonthView�T�[�u���b�g���Ăт��� */
            response.sendRedirect("/jspScheduleManager/schedule/MonthView");
        } else {
            /* �F�؂Ɏ��s������A���O�C����ʂɖ߂� */
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
