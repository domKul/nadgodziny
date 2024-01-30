package dominik.nadgodziny.overtime;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import sender.SmsSender;

@Service
class SchedulingService {

//    @Scheduled(cron = "0 0 14 * * SAT")
//    public void sendSmsAfterScheduling() {
//        SmsSender.smsSchema();
//    }

    @Scheduled(cron = "0 0 14 * * SAT")
    public void sendSmsAfterScheduling() {
        SmsSender.smsSchema();
        System.out.println("wiadomosc wyslana");
    }
}
