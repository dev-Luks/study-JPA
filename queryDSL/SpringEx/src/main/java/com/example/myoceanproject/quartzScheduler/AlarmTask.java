package com.example.myoceanproject.quartzScheduler;


import com.example.myoceanproject.repository.alarm.AlarmRepositoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

@Component
@Slf4j
@RequiredArgsConstructor
public class AlarmTask {

    private final AlarmRepositoryImpl alarmRepositoryImpl;

    @Scheduled(cron = "0 0 0 * * ?")
    public void insertSequence(){
        log.warn("===============================");
        log.warn("~14일 지난 알람 삭제~");
        log.warn("===============================");
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd 00:00:00.000");

        cal.add(Calendar.DATE, -13);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        LocalDateTime dateTime = LocalDateTime.parse(df.format(cal.getTime()), formatter);
        log.warn("===============================");
        log.warn("~" + dateTime + "~");
        log.warn("===============================");

        alarmRepositoryImpl.removeAlarm(dateTime);

    }
}
