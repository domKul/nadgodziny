package dominik.nadgodziny.infrastructure.sender;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class SmsBuilder implements SmsProcessor {

    private final SmsSenderConfig smsSenderConfig;

    @Value("${twilio.phone.number.to}")
    private  String phoneNumberTo;
    @Value("${twilio.phone.number.from}")
    private String phoneNumberFrom;
    @Value("${twilio.message.body}")
    private String smsBody;

    @Override
    public void sendSms() {
        smsSenderConfig.smsSchema(phoneNumberTo,phoneNumberFrom,smsBody);
    }
}
