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
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0.1//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html lang="ja">

<head>
    <meta http-equiv="Content-Type" Content="text/html;charset=Shift_JIS">
    <title>�X�P�W���[���폜</title>
    <link rel="stylesheet" href="../css/styles.css">
</head>

<body>
    <p><%= userName %>����̃X�P�W���[���ł�</p>
    <p>�X�P�W���[���̍폜�m�F&nbsp;&nbsp;[<a href="/jspScheduleManager/schedule/ScheduleView?ID=<%= scheduleId %>">�X�P�W���[���\���ɖ߂�</a>]</p>
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
    <p>�X�P�W���[�����폜���܂��B��x�폜����ƌ��ɂ͖߂��܂���</p>
    <p>�폜���܂����H</p>
    <p>[<a href="/jspScheduleManager/schedule/ExeDeleteSchedule?ID=<%= scheduleId %>&YEAR=<%= year %>&MONTH=<%= month %>">�폜����</a>]&nbsp;&nbsp;[<a
            href="/jspScheduleManager/schedule/ScheduleView?ID=<%= scheduleId %>">�L�����Z��</a>]</p>
</body>

</html>
