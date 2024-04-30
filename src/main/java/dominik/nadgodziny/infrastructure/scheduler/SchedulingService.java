package dominik.nadgodziny.infrastructure.scheduler;

import dominik.nadgodziny.infrastructure.sender.SmsProcessor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;

import static dominik.nadgodziny.domain.ConsoleWriter.printText;


@Component
class SchedulingService {

    private final SmsProcessor smsBuilder;

    public SchedulingService(SmsProcessor smsBuilder) {
        this.smsBuilder = smsBuilder;
    }

    @Scheduled(cron = "${scheduling.request.delay}")
    public void sendSmsAfterScheduling() {
        smsBuilder.sendSms();
        printText("wiadomosc wyslana " + LocalDate.now(Clock.systemDefaultZone()));
    }
}
