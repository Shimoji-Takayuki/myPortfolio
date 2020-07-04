"use strict";

{
  // ���t�̐ݒ�
  for (let i = 1; i <= jsonDate.length; i++) {
    let dayObject = document.getElementById("tdDay" + i);

    // �挎�܂��͗����̏ꍇ
    if (
      jsonDate[i - 1]["month"] == month ||
      jsonDate[i - 1]["month"] == month + 2
    ) {
      dayObject.classList.add("otherday");
      dayObject.textContent = jsonDate[i - 1]["date"];
    }

    // �����̏ꍇ
    if (jsonDate[i - 1]["month"] == month + 1) {
      dayObject.classList.add("day");
      dayObject.textContent = jsonDate[i - 1]["date"];

      // �V�K�o�^�����N�̐ݒ�
      let scheduleObject = document.getElementById("tdSchedule" + i);
      scheduleObject.innerHTML =
        '<a style="font-size: 0.75em" href="/jspScheduleManager/schedule/NewSchedule?YEAR=' +
        year +
        "&MONTH=" +
        month +
        "&DAY=" +
        jsonDate[i - 1]["date"] +
        '">�V�K</a>';

      // �����X�P�W���[�������N�̐ݒ�
      // �����̃X�P�W���[���Ńt�B���^�[
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
