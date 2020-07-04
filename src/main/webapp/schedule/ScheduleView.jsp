<%@ page contentType="text/html;charset=Shift_JIS" %>

<%
// �Z�b�V�����p�����[�^�̎擾
int scheduleId = (int)session.getAttribute("ID");
String userName = (String)session.getAttribute("userName");
int year = (int)session.getAttribute("year");
int month = (int)session.getAttribute("month");
String dateStr = (String)session.getAttribute("date");
String dateTimeStr = (String)session.getAttribute("dateTime");
String schedule = (String)session.getAttribute("schedule");
String memo = (String)session.getAttribute("memo");
Object allSchedule = session.getAttribute("allSchedule");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0.1//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html lang="ja">

<head>
    <meta http-equiv="Content-Type" Content="text/html;charset=Shift_JIS">
    <title>�X�P�W���[���o�^</title>
    <link rel="stylesheet" href="../css/styles.css">
</head>

<body>
    <p><%= userName %>����̃X�P�W���[���ł�</p>
    <p>�����X�P�W���[���m�F&nbsp;&nbsp;[<a href="/jspScheduleManager/schedule/MonthView?YEAR=<%= year %>&MONTH=<%= month %>">�J�����_�[�֖߂�</a>]</p>
    <div id="contents">
        <div id="left">
            <table class="sche">
                <tr>
                    <td class="top" style="width:80px">����</td>
                    <td class="top" style="width:300px">�\��</td>
                </tr>
                <tr>
                    <td class="timeb">����</td>
                    <td id="undefinedId" class="contentsb"></td>
                </tr>
                <%
                    for(int i = 0; i <= 23; i++) {
                %>
                        <tr>
                            <td class="time"><%= i %>:00</td>
                            <td id="tdContent<%= i * 2 %>" class="contents"></td>
                        </tr>
                        <tr>
                            <td class="timeb"></td>
                            <td id="tdContent<%= (i * 2) + 1%>" class="contentsb"></td>
                        </tr>
                <%
                    }
                %>
            </table>
        </div>
        <div id="right">
            <table class="view">
                <tr>
                    <td class="left">���t</td>
                    <td><%= dateStr %></td>
                </tr>
                <tr>
                    <td class="left">����</td>
                    <td><%= dateTimeStr %></td>
                </tr>
                <tr>
                    <td class="left">�X�P�W���[��</td>
                    <td><%= schedule %></td>
                </tr>
                <tr>
                    <td class="left" style="height:150px;">����</td>
                    <td><%= memo %></td>
                </tr>
            </table>
            <p>[<a href="/jspScheduleManager/schedule/EditSchedule?ID=<%= scheduleId %>">�X�P�W���[���̕ύX</a>]&nbsp;&nbsp;[<a
                    href="/jspScheduleManager/schedule/DeleteCheck?ID=<%= scheduleId %>">�X�P�W���[���̍폜</a>]</p>
        </div>
    </div>

    <script type="text/javascript">
        let jsonSchedule = <%= allSchedule %>;
    </script>

    <script type="text/javascript" src="../js/ScheduleView.js"></script>

</body>

</html>
