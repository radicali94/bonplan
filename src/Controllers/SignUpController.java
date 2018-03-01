package Controllers;

import static Controllers.LoginController.user;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.twilio.Twilio;
import entite.BCrypt;
import entite.SmsSender;
import static entite.SmsSender.ACCOUNT_SID;
import static entite.SmsSender.API_PHONE;
import static entite.SmsSender.AUTH_TOKEN;
import entite.User;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import service.UserService;
import javafx.event.ActionEvent;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SignUpController implements Initializable{

    private double xOffset = 0;
    private double yOffset = 0;
    
    public JFXButton annuler;

    @FXML

    protected JFXButton btnX;
    @FXML
    private AnchorPane pane1;
    @FXML
    private JFXTextField nom;
    @FXML
    private JFXButton valider;
    @FXML
    private JFXTextField prenom;
    @FXML
    private JFXTextField username;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXPasswordField pass;
    @FXML
    private JFXPasswordField pass1;
    @FXML
    private JFXPasswordField txTelephone;

    @FXML
    private JFXComboBox<String> cbType;
    @FXML
    private JFXButton btPicture;
    private String fileName = "No picture";
    
    @FXML
    void upload(ActionEvent event) {
        
          FileChooser fileChooser = new FileChooser();
                     FileChooser.ExtensionFilter exjpg = new FileChooser.ExtensionFilter("JPG files (*.jpg)", "*.JPG");
                     FileChooser.ExtensionFilter exjpg2 = new FileChooser.ExtensionFilter("JPEG (Joint Photographic Experts Group)", "*.jpeg");
                     FileChooser.ExtensionFilter expng = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.PNG");
                     fileChooser.getExtensionFilters().addAll(exjpg,exjpg2, expng);
                     fileChooser.setTitle("Choose an image File");

                     File file = fileChooser.showOpenDialog(null);
                     
                     
                             if (file != null) {
            if (file.length() < 6000000) {
                
                
                                
                               fileName= file.getName();
                               
                               System.out.println(fileName);
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Permiss");
                alert.setHeaderText("Permission denied");
                alert.setContentText("Your Image file is too big to upload \nplease choise another image");
            }


            }

    }

    @FXML
    public void makeAnnuler(ActionEvent event) throws IOException
    {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
        Parent root = loader.load();
        root.setId("pane");
        
        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        scene1.getStylesheets().addAll(this.getClass().getResource("/Content/style.css").toExternalForm());
        stage.show();
    }

    @FXML
    public void makeValider(ActionEvent actionEvent) throws IOException, MessagingException {
        
       
        String nomM = nom.getText() ; 
        String prenomM = prenom.getText(); 
        String typeM =  cbType.getValue();
        String tel=txTelephone.getText();
        String email1  = email.getText();
        String username=this.username.getText();
        String password=BCrypt.hashpw(pass.getText(), BCrypt.gensalt());
        User e = new User(username, "", 0, password, tel, nomM, prenomM, tel, nomM, typeM) ; 
        e.setEmail(email1);
        
        
        e.setPhoto(fileName);
         if(isValidEmailAddress(email.getText()))
        {
            if((!nom.getText().equals(""))&&(!prenom.getText().equals(""))&&(!txTelephone.getText().equals(""))&&(!email.getText().equals(""))&&(!pass1.getText().equals("")))
        {
        if(pass.getText().equalsIgnoreCase(pass1.getText())){
        UserService us=new UserService(user);
        us.ajouterUser(e);
        
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/login.fxml"));
        Parent root = loader.load();
        root.setId("pane");
        
        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        scene1.getStylesheets().addAll(this.getClass().getResource("/Content/style.css").toExternalForm());
        stage.show();
        
        sendMail("oussama.ourabi@esprit.tn","mailesprit","Compte BonPlan","Welcome to BonPlan "+nom.getText()+"  "+prenomM,email1);
//SMS
//        Twilio.setUsername(ACCOUNT_SID);
//            Twilio.setPassword(AUTH_TOKEN);
//            
//            SmsSender.SendSMS("+21653330035",
//                              API_PHONE,  
//                              "Welcome to BonPlan "+nom.getText()+"  "+prenomM);
         
        
        }else{
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(" mot de passe");
                alert.setContentText("Confirmation differente");
                alert.showAndWait();
        }
       
        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(" Control");
                alert.setContentText("All fields are required");
                alert.showAndWait();
            
        }
        }else{
              Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(" Invalid email");
                alert.setContentText("email must in this form: *******@*****.***");
                alert.showAndWait();
         }
    }

    @FXML
    public void close(ActionEvent actionEvent) throws Exception
    {
        Stage stage = (Stage) btnX.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
                pane1.setOnMousePressed((javafx.scene.input.MouseEvent event) -> {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                });

                pane1.setOnMouseDragged((javafx.scene.input.MouseEvent event) -> {
                    Stage stage = (Stage) pane1.getScene().getWindow();
                    stage.setX(event.getScreenX()-xOffset);
                    stage.setY(event.getScreenY()-yOffset);
                });
                
                    ObservableList<String> options = 
    FXCollections.observableArrayList(
        "Admin",
        "Membre",
        "Membre Pro"
    );
                    cbType.setItems(options);
    }
    
    public void sendMail(String userMail,String pass,String sujet,String contenu, String send) throws MessagingException{
    
        
        String to = send;
        String host = "smtp.gmail.com";
        Properties prop = System.getProperties();
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put( "mail.smtp.host", host );
        prop.put("mail.smtp.user", userMail);
        prop.put("mail.smtp.password", pass);
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        
        Session session = Session.getDefaultInstance(prop);
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(userMail));
         
        InternetAddress toAdresse = new InternetAddress(to);
                
        msg.setRecipient(Message.RecipientType.TO, toAdresse);
        msg.setSubject(sujet);
        msg.setContent(contenu,"text/html; charset=utf-8");
        Transport transport = session.getTransport("smtp");
        transport.connect(host, userMail, pass);
        transport.sendMessage(msg, msg.getAllRecipients());
        transport.close();
    
          
        
        
    }
    
    public static boolean isValidEmailAddress(String email) {
   boolean result = true;
   try {
      InternetAddress emailAddr = new InternetAddress(email);
      emailAddr.validate();
   } catch (AddressException ex) {
      result = false;
   }
   return result;
}


    
}
