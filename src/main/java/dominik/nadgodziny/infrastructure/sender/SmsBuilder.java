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
    @Value("${twilio.account.user}")
    private String accountSid;
    @Value("${twilio.account.token}")
    private String authToken;

    @Override
    public void sendSms() {
        smsSenderSchema.smsSchema(accountSid,authToken,phoneNumberTo,phoneNumberFrom);
    }
}
