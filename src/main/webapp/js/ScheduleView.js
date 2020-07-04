'use strict';

{
    // 時間が未定のスケジュールを取得
    let undefinedSchedule = jsonSchedule.filter(function(item) {
        if (!item.time) return true
    });

    // 時間が未定のスケジュールを記載
    undefinedSchedule.forEach(jsonSchedule => {
        let tagContent = document.getElementById('undefinedId');
        if (tagContent.textContent == '') {
            tagContent.innerHTML = '<a href="/jspScheduleManager/schedule/ScheduleView?ID=' + jsonSchedule['id'] + '">' + jsonSchedule.schedule;
        } else {
            tagContent.innerHTML += '<br>' + '<a href="/jspScheduleManager/schedule/ScheduleView?ID=' + jsonSchedule['id'] + '">' + jsonSchedule.schedule;
        }
    });

    // 時間が決まっているスケジュールを取得
    let definedSchedule = jsonSchedule.filter(function(item) {
        if (item.time) return true
    });

    definedSchedule.forEach(jsonSchedule => {
        let diff = 2 * (parseInt(jsonSchedule['endTime'].substr(0, 2)) - parseInt(jsonSchedule['startTime'].substr(0, 2)));

        // 30分単位調整
        if (jsonSchedule['endTime'].substr(3, 2) == '30') {
            diff++;
        }

        if (jsonSchedule['startTime'].substr(3, 2) == '30') {
            diff--;
        }

        // rowspanの長さに使うためのオブジェクトkeyを追加
        // jsonSchedule['timeDiff'] = diff;
        jsonSchedule.timeDiff = diff;

    });

    // 開始時間でソート
    definedSchedule.sort(function(a, b) {
        if (a.startTime < b.startTime) return -1;
        if (a.startTime > b.startTime) return 1;
        return 0;
    });

    // 当日のスケジュール情報をテーブルへセット
    definedSchedule.forEach(jsonSchedule => {
        let startTime = jsonSchedule['startTime'].substr(0, 2);
        let targetCounter = 0;

        if (jsonSchedule['startTime'].substr(3, 2) == '00') {
            targetCounter = 2 * parseInt(startTime);
        } else {
            targetCounter = (2 * parseInt(startTime)) + 1;
        }

        let targetTag =document.getElementById('tdContent' + targetCounter);
        // テーブルに既に格納済みであればスキップ（時間重複は無視）
        if (targetTag.textContent) {
            return;
        }

        for (let i = 0; i < jsonSchedule['timeDiff']; i++) {
            let targetTd = document.getElementById('tdContent' + (targetCounter + i));
            if (i == 0) {
                targetTd.innerHTML = jsonSchedule['time'] + ' <a href="/jspScheduleManager/schedule/ScheduleView?ID=' + jsonSchedule['id'] + '">' + jsonSchedule['schedule'] + '</a>';
                targetTd.classList.add('ex');
                targetTd.rowSpan = String(jsonSchedule['timeDiff']);
                targetTd.setAttribute("rowSpan", String(jsonSchedule['timeDiff']));
            } else {
                targetTd.remove();
            }
        }
    });
}