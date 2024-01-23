package sender;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.stereotype.Component;

@Component
public class SmsSender {


    private static final String ACCOUNT_SID = "ACc88d082fd152619024b33f84b0a51d94";
    private static final String AUTH_TOKEN = "ab1f9bfbbbd8f82ddf4d2f83c3f36fd3";

    public static void smsSchema() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("+48697709761"),
                        new com.twilio.type.PhoneNumber("+12058832506"),
                        "Dodaj nadgodziny")
                .create();

        System.out.println(message.getSid());
    }
}
