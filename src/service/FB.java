/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import com.restfb.BinaryAttachment;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import entite.CustomUser;

/**
 *
 * @author Radhi
 */
public class FB {
    public static void publish()  {
        
        String MY_APP_ID = "225518324672414";
        String MY_APP_SECRET = "afbc3e808685f9fa2c133af6c90a6538";
        String MyAccessToken = "EAADNG5Oh554BAOChZByI9oMVryTXZArIvpLNZBNyvgTfTZAZCZAPGntPU217mCUNqZBR2u40hF5nJn0mFHdBojPNytpTpzHUa4DZAOgcrvc5eOrWrO7Asma0PWeOJpZBUqnqLZCGoeeS3Iymgk97HQQ7JTajt8ciWq1zEfz7ATBsRgiI7MFu9ibm7cYNNA4Re9ruQZD";

        //AccessToken accessToken = new DefaultFacebookClient().obtainAppAccessToken(MY_APP_ID, MY_APP_SECRET);
        DefaultFacebookClient facebookClient = new DefaultFacebookClient(MyAccessToken);
        String fbMessage = "Hello from java!";
        //facebookClient.publish("me/feed", FacebookType.class, Parameter.with("message", fbMessage));
        facebookClient.publish("me/photos", FacebookType.class,
                BinaryAttachment.with("jpg", Controllers.BonPlanDetailsController.fbImage),
                Parameter.with("message", Controllers.BonPlanDetailsController.fbPost + "\n#FORSA\n#BitsPlease"));
        
         CustomUser user = facebookClient.fetchObject("me", CustomUser.class,
               Parameter.with("fields",
                       "id, name, email, first_name, last_name"));
 
        System.out.println("First Name= " + user.getFirstName());
        System.out.println("Last Name= " + user.getLastName());
        System.out.println("Full Name= " + user.getFullName());
        System.out.println("Email= " + user.getEmail());

        
        
    }
    
    
    
   
    
}
