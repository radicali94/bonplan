/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.scope.ScopeBuilder;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 * FXML Controller class
 *
 * @author Radhi
 */
public class FXMLwebviewFBController implements Initializable {

    public static final String SUCCESS_URL = "https://www.facebook.com/connect/login_success.html";
    
    private String code;

    private final String appId= "225518324672414";

    private final String appSecret= "afbc3e808685f9fa2c133af6c90a6538";
    @FXML
    private WebView browser;
    
    final WebEngine webEngine = browser.getEngine();

    /*public Browser(String appId, String appSecret) {
      this.appId = appId;
      this.appSecret = appSecret;
      // add the web view to the scene
      getChildren().add(browser);
    }*/
    
    public void showLogin() {
    DefaultFacebookClient facebookClient = new DefaultFacebookClient(Version.LATEST);
    ScopeBuilder scopes = new ScopeBuilder();
    String loadUrl = facebookClient.getLoginDialogUrl(appId, SUCCESS_URL, scopes);
    webEngine.load(loadUrl + "&display=popup&response_type=code");
    webEngine.getLoadWorker().stateProperty().addListener(
      (ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue) -> {
        if (newValue != Worker.State.SUCCEEDED) {
          return;
        }

        String myUrl = webEngine.getLocation();

        if ("https://www.facebook.com/dialog/close".equals(myUrl)) {
          System.out.println("dialog closed");
          System.exit(0);
        }

        if (myUrl.startsWith(SUCCESS_URL)) {
          System.out.println(myUrl);
          int pos = myUrl.indexOf("code=");
          code = myUrl.substring(pos + "code=".length());
          FacebookClient.AccessToken token = facebookClient.obtainUserAccessToken(appId,
                  appSecret, SUCCESS_URL, code);
          System.out.println("Accesstoken: " + token.getAccessToken());
          System.out.println("Expires: " + token.getExpires());
        }

      });
  }
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        showLogin();
       
    }

    
    
    
    
}
