<%@ page contentType="text/html;charset=Shift_JIS" %>

<%
// セッションパラメータの取得
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
    <title>スケジュール削除</title>
    <link rel="stylesheet" href="../css/styles.css">
</head>

<body>
    <p><%= userName %>さんのスケジュールです</p>
    <p>スケジュールの削除確認&nbsp;&nbsp;[<a href="/jspScheduleManager/schedule/ScheduleView?ID=<%= scheduleId %>">スケジュール表示に戻る</a>]</p>
    <table class="view">
        <tr>
            <td class="left">日付</td>
            <td><%= dateStr %></td>
        </tr>
        <tr>
            <td class="left">時間</td>
            <td><%= dateTimeStr %></td>
        </tr>
        <tr>
            <td class="left">スケジュール</td>
            <td><%= schedule %></td>
        </tr>
        <tr>
            <td class="left" style="height:150px;">メモ</td>
            <td><%= memo %></td>
        </tr>
    </table>
    <p>スケジュールを削除します。一度削除すると元には戻せません</p>
    <p>削除しますか？</p>
    <p>[<a href="/jspScheduleManager/schedule/ExeDeleteSchedule?ID=<%= scheduleId %>&YEAR=<%= year %>&MONTH=<%= month %>">削除する</a>]&nbsp;&nbsp;[<a
            href="/jspScheduleManager/schedule/ScheduleView?ID=<%= scheduleId %>">キャンセル</a>]</p>
</body>

</html>
