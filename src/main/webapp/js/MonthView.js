"use strict";

{
  // 日付の設定
  for (let i = 1; i <= jsonDate.length; i++) {
    let dayObject = document.getElementById("tdDay" + i);

    // 先月または来月の場合
    if (
      jsonDate[i - 1]["month"] == month ||
      jsonDate[i - 1]["month"] == month + 2
    ) {
      dayObject.classList.add("otherday");
      dayObject.textContent = jsonDate[i - 1]["date"];
    }

    // 今月の場合
    if (jsonDate[i - 1]["month"] == month + 1) {
      dayObject.classList.add("day");
      dayObject.textContent = jsonDate[i - 1]["date"];

      // 新規登録リンクの設定
      let scheduleObject = document.getElementById("tdSchedule" + i);
      scheduleObject.innerHTML =
        '<a style="font-size: 0.75em" href="/jspScheduleManager/schedule/NewSchedule?YEAR=' +
        year +
        "&MONTH=" +
        month +
        "&DAY=" +
        jsonDate[i - 1]["date"] +
        '">新規</a>';

      // 既存スケジュールリンクの設定
      // 当日のスケジュールでフィルター
      let scheduleADay = jsonSchedule.filter(function (schedule) {
        if (dayObject.textContent == schedule.date) return true;
      });

      scheduleADay.forEach((scheduleElement) => {
        scheduleObject.innerHTML +=
          '<br><span class="small">' +
          scheduleElement["time"] +
          ' <a href="/jspScheduleManager/schedule/ScheduleView?ID=' +
          scheduleElement["ID"] +
          '">' +
          scheduleElement["schedule"] +
          "</a>";
      });
    }
  }
}
