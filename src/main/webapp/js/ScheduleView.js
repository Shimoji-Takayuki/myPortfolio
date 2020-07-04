'use strict';

{
    // ���Ԃ�����̃X�P�W���[�����擾
    let undefinedSchedule = jsonSchedule.filter(function(item) {
        if (!item.time) return true
    });

    // ���Ԃ�����̃X�P�W���[�����L��
    undefinedSchedule.forEach(jsonSchedule => {
        let tagContent = document.getElementById('undefinedId');
        if (tagContent.textContent == '') {
            tagContent.innerHTML = '<a href="/jspScheduleManager/schedule/ScheduleView?ID=' + jsonSchedule['id'] + '">' + jsonSchedule.schedule;
        } else {
            tagContent.innerHTML += '<br>' + '<a href="/jspScheduleManager/schedule/ScheduleView?ID=' + jsonSchedule['id'] + '">' + jsonSchedule.schedule;
        }
    });

    // ���Ԃ����܂��Ă���X�P�W���[�����擾
    let definedSchedule = jsonSchedule.filter(function(item) {
        if (item.time) return true
    });

    definedSchedule.forEach(jsonSchedule => {
        let diff = 2 * (parseInt(jsonSchedule['endTime'].substr(0, 2)) - parseInt(jsonSchedule['startTime'].substr(0, 2)));

        // 30���P�ʒ���
        if (jsonSchedule['endTime'].substr(3, 2) == '30') {
            diff++;
        }

        if (jsonSchedule['startTime'].substr(3, 2) == '30') {
            diff--;
        }

        // rowspan�̒����Ɏg�����߂̃I�u�W�F�N�gkey��ǉ�
        // jsonSchedule['timeDiff'] = diff;
        jsonSchedule.timeDiff = diff;

    });

    // �J�n���ԂŃ\�[�g
    definedSchedule.sort(function(a, b) {
        if (a.startTime < b.startTime) return -1;
        if (a.startTime > b.startTime) return 1;
        return 0;
    });

    // �����̃X�P�W���[�������e�[�u���փZ�b�g
    definedSchedule.forEach(jsonSchedule => {
        let startTime = jsonSchedule['startTime'].substr(0, 2);
        let targetCounter = 0;

        if (jsonSchedule['startTime'].substr(3, 2) == '00') {
            targetCounter = 2 * parseInt(startTime);
        } else {
            targetCounter = (2 * parseInt(startTime)) + 1;
        }

        let targetTag =document.getElementById('tdContent' + targetCounter);
        // �e�[�u���Ɋ��Ɋi�[�ς݂ł���΃X�L�b�v�i���ԏd���͖����j
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