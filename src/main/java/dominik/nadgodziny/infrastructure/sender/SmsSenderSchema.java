package dominik.nadgodziny.infrastructure.sender;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class SmsSenderSchema {


    @Value("${twilio.account.user}")
    private String accountSid;
    @Value("${twilio.account.token}")
    private String authToken;

    void smsSchema(String to, String from, String message) {
        Twilio.init(accountSid, authToken);
        Message.creator(
                        new PhoneNumber(to),
                        new PhoneNumber(from),
                        message)
                .create();
    }
}
