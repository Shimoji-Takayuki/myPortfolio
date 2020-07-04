<%@ page contentType="text/html;charset=Shift_JIS" %>

<%
// セッションパラメータの取得
int scheduleId = (int)session.getAttribute("ID");
String userName = (String)session.getAttribute("userName");
int year = (int)session.getAttribute("year");
int month = (int)session.getAttribute("month");
int day = (int)session.getAttribute("day");
String sTime = (String)session.getAttribute("sTime");
String eTime = (String)session.getAttribute("eTime");
String schedule = (String)session.getAttribute("schedule");
String memo = (String)session.getAttribute("memo");
int lastDay = (int)session.getAttribute("lastDay");
Object allSchedule = session.getAttribute("allSchedule");
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0.1//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html lang="ja">

<head>
    <meta http-equiv="Content-Type" Content="text/html;charset=Shift_JIS">
    <title>スケジュール変更</title>
    <link rel="stylesheet" href="../css/styles.css">
</head>

<body>
    <p><%= userName %>さんのスケジュールです</p>
    <p>スケジュールの変更&nbsp;&nbsp;[<a href="/jspScheduleManager/schedule/ScheduleView?ID=<%= scheduleId %>">スケジュール表示に戻る</a>]</p>
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
            <form method="post" action="/jspScheduleManager/schedule/ExeUpdateSchedule">
                <table>
                    <tr>
                        <td nowrap>日付</td>
                        <td><select id="scheduleYear" name="YEAR">
                                <%
                                    for(int i = year; i <= year + 4 ; i++) {
                                %>
                                    <option value="<%= i %>"><%= i %>年
                                <%
                                    }
                                %>
                            </select>
                            <select id="scheduleMonth" name="MONTH">
                                <%
                                    for(int i = 1; i <= 12 ; i++) {
                                %>
                                    <option value="<%= i %>"><%= i %>月
                                <%
                                    }
                                %>
                            </select>
                            <select id="scheduleDay" name="DAY">
                                <%
                                    for(int i = 1; i <= lastDay; i++) {
                                %>
                                    <option value="<%= i %>"><%= i %>日
                                <%
                                    }
                                %>
                            </select></td>
                    </tr>
                    <tr>
                        <td nowrap>時刻</td>
                        <td><select id="scheduleSHour" name="SHOUR">
                            <option value="">--時
                            <%
                                for(int i = 0; i <= 23 ; i++) {
                            %>
                                <option value="<%= i %>"><%= i %>時
                            <%
                                }
                            %>
                            </select>
                            <select id="scheduleSMinute" name="SMINUTE">
                                <option value="0">00分
                                <option value="30">30分
                            </select> -- <select id="scheduleEHour" name="EHOUR">
                                <option value="">--時
                            <%
                                for(int i = 0; i <= 23 ; i++) {
                            %>
                                <option value="<%= i %>"><%= i %>時
                            <%
                                }
                            %>
                            </select> <select id="scheduleEMinute" name="EMINUTE">
                                <option value="0">00分
                                <option value="30">30分
                            </select></td>
                    </tr>
                    <tr>
                        <td nowrap>予定</td>
                        <td><input type="text" name="PLAN" value="<%= schedule %>" size="30" maxlength="100"></td>
                    </tr>
                    <tr>
                        <td valign="top" nowrap>メモ</td>
                        <td><textarea name="MEMO" cols="30" rows="10" wrap="virtual"><%= memo %></textarea></td>
                    </tr>
                </table>
                <p><input type="hidden" name="ID" value="<%= scheduleId %>"><input type="submit" name="Register" value="変更する">
                <p>
            </form>
        </div>
    </div>

    <script type="text/javascript">
        let jsonSchedule = <%= allSchedule %>;
        let month = <%= month %>;
        let day = <%= day %>;
        let sTime = "<%= sTime %>";
        let eTime = "<%= eTime %>";
    </script>

    <script type="text/javascript" src="../js/EditSchedule.js"></script>

</body>

</html>
