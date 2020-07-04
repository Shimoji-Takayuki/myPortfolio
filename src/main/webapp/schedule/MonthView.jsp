<%@ page contentType="text/html;charset=Shift_JIS" %>

<%
// セッションパラメータの取得
String userName = (String)session.getAttribute("userName");
String role = (String)session.getAttribute("role");
int year = (int)session.getAttribute("year");
int month = (int)session.getAttribute("month");
int dateLength = (int)session.getAttribute("dateLength");
Object dateArray = session.getAttribute("dateArray");
Object scheduleArray = session.getAttribute("scheduleArray");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0.1//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html lang="ja">

<head>
    <meta http-equiv="Content-Type" Content="text/html;charset=Shift_JIS">
    <title>スケジュール管理</title>
    <link rel="stylesheet" href="../css/calendarStyles.css">
</head>

<body>
    <p><%= userName %>さんのスケジュールです&nbsp;
    <%
        if (role.equals("1")) {
    %>
            [<a href="/jspScheduleManager/schedule/NewUser.jsp">ユーザーの追加</a>]&nbsp;
    <%
        }
    %>
    [<a href="/jspScheduleManager/schedule/Logout">ログアウト</a>]</p>
    <p><a href="/jspScheduleManager/schedule/MonthView?YEAR=<%= year %>&MONTH=<%= month - 1 %>"><span
                class="small">前月</span></a>&nbsp;&nbsp;<%= year %>年<%= month + 1 %>月&nbsp;&nbsp;<a
            href="/jspScheduleManager/schedule/MonthView?YEAR=<%= year %>&MONTH=<%= month + 1 %>"><span class="small">翌月</span></a></p>
    <table>
        <tr>
            <td class="week">日</td>
            <td class="week">月</td>
            <td class="week">火</td>
            <td class="week">水</td>
            <td class="week">木</td>
            <td class="week">金</td>
            <td class="week">土</td>
        </tr>
    <%
        for (int i = 0; i < dateLength / 7; i++) {
    %>
        <tr>
    <%
            for (int j = 1; j <= 7; j++) {
    %>
                <td id="tdDay<%= (7 * i) + j %>"></td>
    <%
            }
    %>
        </tr>
        <tr>
    <%
            for (int j = 1; j <= 7; j++) {
    %>
                <td id="tdSchedule<%= (7 * i) + j %>" class="sche"></td>
    <%
            }
    %>
        </tr>
    <%
        }
    %>

    </table>

    <script type="text/javascript">
        let year = <%= year %>;
        let month = <%= month %>;
        let jsonDate = <%= dateArray %>;
        let jsonSchedule = <%= scheduleArray %>;
    </script>

    <script type="text/javascript" src="../js/MonthView.js?1"></script>

</body>

</html>
