<%@ page contentType="text/html;charset=Shift_JIS" %>

<%
// �Z�b�V�����p�����[�^�̎擾
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
    <title>�X�P�W���[���Ǘ�</title>
    <link rel="stylesheet" href="../css/calendarStyles.css">
</head>

<body>
    <p><%= userName %>����̃X�P�W���[���ł�&nbsp;
    <%
        if (role.equals("1")) {
    %>
            [<a href="/jspScheduleManager/schedule/NewUser.jsp">���[�U�[�̒ǉ�</a>]&nbsp;
    <%
        }
    %>
    [<a href="/jspScheduleManager/schedule/Logout">���O�A�E�g</a>]</p>
    <p><a href="/jspScheduleManager/schedule/MonthView?YEAR=<%= year %>&MONTH=<%= month - 1 %>"><span
                class="small">�O��</span></a>&nbsp;&nbsp;<%= year %>�N<%= month + 1 %>��&nbsp;&nbsp;<a
            href="/jspScheduleManager/schedule/MonthView?YEAR=<%= year %>&MONTH=<%= month + 1 %>"><span class="small">����</span></a></p>
    <table>
        <tr>
            <td class="week">��</td>
            <td class="week">��</td>
            <td class="week">��</td>
            <td class="week">��</td>
            <td class="week">��</td>
            <td class="week">��</td>
            <td class="week">�y</td>
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
