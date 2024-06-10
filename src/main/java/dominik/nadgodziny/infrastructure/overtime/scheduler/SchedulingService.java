package dominik.nadgodziny.infrastructure.overtime.scheduler;

import dominik.nadgodziny.infrastructure.overtime.sender.SmsProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.LocalDate;

import static dominik.nadgodziny.domain.overtime.ConsoleWriter.printText;


@Component
@RequiredArgsConstructor
class SchedulingService {

    private final SmsProcessor smsBuilder;

    @Scheduled(cron = "${scheduling.request.delay}")
    public void sendSmsAfterScheduling() {
        smsBuilder.sendSms();
        printText("wiadomosc wyslana " + LocalDate.now(Clock.systemDefaultZone()));
    }
}
