package dominik.nadgodziny.infrastructure.sender;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class SmsBuilder implements SmsProcessor {

    private final SmsSenderSchema smsSenderSchema;

    public SmsBuilder(SmsSenderSchema smsSenderSchema) {
        this.smsSenderSchema = smsSenderSchema;
    }

    @Value("${twilio.phone.number.to}")
    private String phoneNumberTo;
    @Value("${twilio.phone.number.from}")
    private String phoneNumberFrom;
    @Value("${twilio.message.body}")
    private String smsBody;

    @Override
    public void sendSms() {
        smsSenderSchema.smsSchema(phoneNumberTo,phoneNumberFrom,smsBody);
    }
}
