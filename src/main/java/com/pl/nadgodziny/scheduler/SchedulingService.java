package com.pl.nadgodziny.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sender.SmsSender;

@Service
public class SchedulingService {

    @Scheduled(cron = "0 0 14 * * SAT")
    public void sendSmsAfterScheduling() {
        SmsSender.smsSchem();
    }
}
