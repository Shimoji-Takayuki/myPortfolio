<%@ page contentType="text/html;charset=Shift_JIS" %>

<%
// �Z�b�V�����ϐ��̎擾
String role = (String)session.getAttribute("role");
Object status = (String)session.getAttribute("CreateUser");
%>

<html>

<head>
    <title>���[�U�[�̍쐬</title>
</head>

<body>
    <h1>���[�U�[�̍쐬</h1>
    <p>�V�������[�U�[���쐬���܂�</p>

    <% 
        if (status != null) {
            String statusStr = (String)status;

            if (statusStr.equals("Fail")){
    %>
                <p>���[�U�[�̍쐬�Ɏ��s���܂���</p>
                <p>�ēx���[�U�[���ƃp�X���[�h����͂��ĉ�����</p>
    <%
            } else if (statusStr.equals("Success")){
    %>
                <p>���[�U�[�̍쐬�ɐ������܂���</p>
                <p>�����č쐬����ꍇ�̓��[�U�[���ƃp�X���[�h����͂��ĉ�����</p>
    <%
            }
            
            session.setAttribute("CreateUser", null);
        }
    %>

    <form method="POST" action="/jspScheduleManager/schedule/ExeInsertUser" name="loginform">
        <table>
            <tr>
                <td>���[�U�[��</td>
                <td><input type="text" name="user" size="32"></td>
            </tr>
            <tr>
                <td>�p�X���[�h</td>
                <td><input type="password" name="pass" size="32"></td>
            </tr>
            <tr>
                <td>����</td>
                <td><select name="role">
                        <option value="1">�Ǘ���
                        <option value="0" selected>���
                    </select></td>
            </tr>
            <tr>
                <td><input type="submit" value="create"></td>
                <td><input type="reset" value="reset"></td>
            </tr>
        </table>
    </form>
    <p><a href="/jspScheduleManager/schedule/MonthView">�X�P�W���[���ꗗ��</a></p>
</body>

</html>
