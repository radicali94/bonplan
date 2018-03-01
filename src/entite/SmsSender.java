/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;


import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

/**
 *
 * @author Omrane
 */
public class SmsSender {

    // Find your Account Sid and Auth Token at twilio.com/console
    public static final String ACCOUNT_SID = "AC85a1bada4621d6d0449760f431c7aba4";
    public static final String AUTH_TOKEN  = "5022ecf13e8a3a19316a2c7ca46f55d8";
    public static final String API_PHONE   = "+13345390118";

    public static void SendSMS(String to, String from, String body){
        
        Message message = Message
                .creator(new PhoneNumber(to),  // to
                         new PhoneNumber(from),  // from
                         body)
                .create();
    }
}
