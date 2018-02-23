package Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
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

    @FXML
    private void makeSignUp(ActionEvent event) throws IOException
    {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/signUp.fxml"));
        Parent root = loader.load();
        root.setId("pane");
        
        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        scene1.getStylesheets().addAll(this.getClass().getResource("/Content/style.css").toExternalForm());
        stage.show();

        
        
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
            User u = new User();
            while(rest.next())
            {
                
                
                u.setId(rest.getInt(1));
                u.setUsername(rest.getString(2));
                u.setEmail(rest.getString(4));
                u.setPassword(rest.getString(8));
                u.setNom(rest.getString(13));
                u.setPrenom(rest.getString(14));
                u.setTel(rest.getString(15));
                u.setPhoto(rest.getString(16));
                u.setType(rest.getString(17));
                
                
            
            }
            idCnx =u.getId();
            
            
            
            
            System.out.println(u.getPassword());
            System.out.println(pass.getText());
                if(BCrypt.checkpw(pass.getText(), u.getPassword())) {
                    System.out.println("True Password");
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/accueil.fxml"));
                    Parent root = loader.load();
                    root.setId("pane");

                    Scene scene1 = new Scene(root);
                    stage.setScene(scene1);
                    scene1.getStylesheets().addAll(this.getClass().getResource("/Content/style.css").toExternalForm());
                    stage.show();
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
    
    

    
}
