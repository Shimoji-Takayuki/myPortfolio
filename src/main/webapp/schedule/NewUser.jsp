<%@ page contentType="text/html;charset=Shift_JIS" %>

<%
// セッション変数の取得
String role = (String)session.getAttribute("role");
Object status = (String)session.getAttribute("CreateUser");
%>

<html>

<head>
    <title>ユーザーの作成</title>
</head>

<body>
    <h1>ユーザーの作成</h1>
    <p>新しいユーザーを作成します</p>

    <% 
        if (status != null) {
            String statusStr = (String)status;

            if (statusStr.equals("Fail")){
    %>
                <p>ユーザーの作成に失敗しました</p>
                <p>再度ユーザー名とパスワードを入力して下さい</p>
    <%
            } else if (statusStr.equals("Success")){
    %>
                <p>ユーザーの作成に成功しました</p>
                <p>続けて作成する場合はユーザー名とパスワードを入力して下さい</p>
    <%
            }
            
            session.setAttribute("CreateUser", null);
        }
    %>

    <form method="POST" action="/jspScheduleManager/schedule/ExeInsertUser" name="loginform">
        <table>
            <tr>
                <td>ユーザー名</td>
                <td><input type="text" name="user" size="32"></td>
            </tr>
            <tr>
                <td>パスワード</td>
                <td><input type="password" name="pass" size="32"></td>
            </tr>
            <tr>
                <td>権限</td>
                <td><select name="role">
                        <option value="1">管理者
                        <option value="0" selected>一般
                    </select></td>
            </tr>
            <tr>
                <td><input type="submit" value="create"></td>
                <td><input type="reset" value="reset"></td>
            </tr>
        </table>
    </form>
    <p><a href="/jspScheduleManager/schedule/MonthView">スケジュール一覧へ</a></p>
</body>

</html>
