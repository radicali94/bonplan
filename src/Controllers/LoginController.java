package Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Version;
import com.restfb.scope.ScopeBuilder;
import connexionDatabase.MyDB;
import entite.BCrypt;
import entite.User;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import service.UserService;

public class LoginController implements Initializable
{
   private static int idCnx;

    public static int getIdCnx() {
        return idCnx;
    }

    
    private double xOffset = 0;
    private double yOffset = 0;
    
    public JFXButton signUp;
    @FXML
    protected JFXButton btnX;

    
    @FXML
    private JFXTextField username;
    @FXML
    private JFXPasswordField pass;
    @FXML
    private JFXButton login;
    @FXML
    private AnchorPane loginPane;
    @FXML
    private JFXButton login1;
    @FXML
    private JFXButton login11;
    
    static User user;

    @FXML
    private void makeSignUp(ActionEvent event) throws IOException
    {
        try {
           
            
            MyDB myDB = MyDB.getInstance();
            Statement stm = myDB.getConnexion().createStatement();
            ResultSet rest=stm.executeQuery("select * from user where username = '"+username.getText()+"'");
            System.out.println(username.getText());
            user = new User();
            while(rest.next())
            {
                
                
                user.setId(rest.getInt(1));
                user.setUsername(rest.getString(2));
                user.setEmail(rest.getString(4));
                user.setPassword(rest.getString(8));
                user.setNom(rest.getString(13));
                user.setPrenom(rest.getString(14));
                user.setTel(rest.getString(15));
                user.setPhoto(rest.getString(16));
                user.setType(rest.getString(17));
                user.setEnabled(rest.getInt(6));
                
                
            
            }
            idCnx =user.getId();
            
            
            System.out.println("###############"+user.getEnabled());
           
           // System.err.println(u.getUsername());
                if(BCrypt.checkpw(pass.getText(), user.getPassword())) {
                    System.out.println("True Password");
                    System.out.println("enabled:"+user.getEnabled());
                    if(user.getEnabled()==1){
                    if(user.getType().equalsIgnoreCase("Admin"))
                    {Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/UserAdmin.fxml"));
                    Parent root = loader.load();
                    root.setId("pane");

                    Scene scene1 = new Scene(root);
                    stage.setScene(scene1);
                    scene1.getStylesheets().addAll(this.getClass().getResource("/Content/style.css").toExternalForm());
                    stage.show();}
                    
                    else{Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/accueil.fxml"));
                    Parent root = loader.load();
                    root.setId("pane");

                    Scene scene1 = new Scene(root);
                    stage.setScene(scene1);
                    scene1.getStylesheets().addAll(this.getClass().getResource("/Content/style.css").toExternalForm());
                    stage.show();}}
            } else System.out.println("false password!");
            
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

        
        
    }

    @FXML
    public void close(ActionEvent actionEvent) throws Exception
    {
        Stage stage = (Stage) btnX.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void makeLogin(ActionEvent event) throws IOException
    {
        try {
           
            
            MyDB myDB = MyDB.getInstance();
            Statement stm = myDB.getConnexion().createStatement();
            ResultSet rest=stm.executeQuery("select * from user where username = '"+username.getText()+"'");
            System.out.println(username.getText());
            user = new User();
            while(rest.next())
            {
                
                
                user.setId(rest.getInt(1));
                user.setUsername(rest.getString(2));
                user.setEmail(rest.getString(4));
                user.setPassword(rest.getString(8));
                user.setNom(rest.getString(13));
                user.setPrenom(rest.getString(14));
                user.setTel(rest.getString(15));
                user.setPhoto(rest.getString(16));
                user.setType(rest.getString(17));
                user.setEnabled(rest.getInt(6));
                
            
            }
            idCnx =user.getId();
            
            
            
            
            System.out.println("###############"+user.getEnabled());
           
           // System.err.println(u.getUsername());
                if(BCrypt.checkpw(pass.getText(), user.getPassword())) {
                    System.out.println("True Password");
                    System.out.println("enabled:"+user.getEnabled());
                    if(user.getEnabled()==1){
                    if(user.getType().equalsIgnoreCase("Admin"))
                    {Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/UserAdmin.fxml"));
                    Parent root = loader.load();
                    root.setId("pane");

                    Scene scene1 = new Scene(root);
                    stage.setScene(scene1);
                    scene1.getStylesheets().addAll(this.getClass().getResource("/Content/style.css").toExternalForm());
                    stage.show();}
                    
                    else{Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/accueil.fxml"));
                    Parent root = loader.load();
                    root.setId("pane");

                    Scene scene1 = new Scene(root);
                    stage.setScene(scene1);
                    scene1.getStylesheets().addAll(this.getClass().getResource("/Content/style.css").toExternalForm());
                    stage.show();}}
            } else System.out.println("false password!");
            
            
            
            
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
        
        
        
        

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
                
                
                
                loginPane.setOnMousePressed((javafx.scene.input.MouseEvent event) -> {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                });

                loginPane.setOnMouseDragged((javafx.scene.input.MouseEvent event) -> {
                    Stage stage = (Stage) loginPane.getScene().getWindow();
                    stage.setX(event.getScreenX()-xOffset);
                    stage.setY(event.getScreenY()-yOffset);
                });

    }

    @FXML
    private void makeLoginFB(ActionEvent event) throws IOException {
        
        
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/FXMLwebviewFB.fxml"));
        Parent root = loader.load();
        root.setId("pane");
        
        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        scene1.getStylesheets().addAll(this.getClass().getResource("/Content/style.css").toExternalForm());
        stage.show();
        
        
        
        
    }
    
    
    
    

    
}
