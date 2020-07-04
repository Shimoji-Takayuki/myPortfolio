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
Object allSchedule = session.getAttribute("allSchedule");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0.1//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html lang="ja">

<head>
    <meta http-equiv="Content-Type" Content="text/html;charset=Shift_JIS">
    <title>スケジュール登録</title>
    <link rel="stylesheet" href="../css/styles.css">
</head>

<body>
    <p><%= userName %>さんのスケジュールです</p>
    <p>既存スケジュール確認&nbsp;&nbsp;[<a href="/jspScheduleManager/schedule/MonthView?YEAR=<%= year %>&MONTH=<%= month %>">カレンダーへ戻る</a>]</p>
    <div id="contents">
        <div id="left">
            <table class="sche">
                <tr>
                    <td class="top" style="width:80px">時刻</td>
                    <td class="top" style="width:300px">予定</td>
                </tr>
                <tr>
                    <td class="timeb">未定</td>
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
            <p>[<a href="/jspScheduleManager/schedule/EditSchedule?ID=<%= scheduleId %>">スケジュールの変更</a>]&nbsp;&nbsp;[<a
                    href="/jspScheduleManager/schedule/DeleteCheck?ID=<%= scheduleId %>">スケジュールの削除</a>]</p>
        </div>
    </div>

    <script type="text/javascript">
        let jsonSchedule = <%= allSchedule %>;
    </script>

    <script type="text/javascript" src="../js/ScheduleView.js"></script>

</body>

</html>
