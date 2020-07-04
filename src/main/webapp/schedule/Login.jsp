<%@ page contentType="text/html;charset=Shift_JIS" %>

<%
// セッション変数の取得
Object status = (String)session.getAttribute("status");
%>

<html>

<head>
    <title>ログインページ</title>
</head>

<body>

    <% 
        if (status != null) {
    %>
            <p>認証に失敗しました</p>
            <p>再度ユーザー名とパスワードを入力して下さい</p>
    <%
            session.setAttribute("status", null);
        }
    %>

    <h1>スケジュール帳へようこそ</h1>
    <p>スケジュール帳をご利用頂くにはまずログインして頂く必要があります。ユーザー名とパスワードを入力してログインして下さい。</p>
    <form method="POST" action="/jspScheduleManager/schedule/LoginCheck" name="loginform">
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
                <td><input type="submit" value="login"></td>
                <td><input type="reset" value="reset"></td>
            </tr>
        </table>
    </form>
</body>

</html>
