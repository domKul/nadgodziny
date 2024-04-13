package dominik.nadgodziny.infrastructure.sender;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Component;

@Component
class SmsSenderSchema {

    private static final String SMS_BODY = "Dodaj nadgodziny";
     void smsSchema(String user,String token,String to, String from) {
        Twilio.init(user, token);
        Message.creator(
                        new PhoneNumber(to),
                        new PhoneNumber(from),
                        SMS_BODY)
                .create();
    }
}
