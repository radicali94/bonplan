/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import static Controllers.LoginController.user;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import connexionDatabase.MyDB;
import entite.BCrypt;
import entite.User;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.mail.MessagingException;
import service.UserService;

/**
 * FXML Controller class
 *
 * @author Omrane
 */
public class ModifierProfilController implements Initializable {

    /**
     * Initializes the controller class.
     */
   
    private String fileName;
    
       @FXML
    private JFXTextField nom;

    @FXML
    private JFXButton annuler;

    @FXML
    private JFXButton valider;

    @FXML
    private JFXButton btnX;

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
    private JFXTextField txTelephone;

    @FXML
    private JFXComboBox<String> cbType;

    @FXML
    private JFXButton btPicture;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger ham1;

    @FXML
    private CheckBox cbModifierMotDePasse;

    @FXML
    private JFXPasswordField pass2;

    

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
       
        String username=this.username.getText();
        
        String password= user.getPassword();
      
        int test =0;
       
       
        if (BCrypt.checkpw(pass.getText(), password)){
        
        if(cbModifierMotDePasse.isSelected()==true)
        {
            if(pass1.getText().equalsIgnoreCase(pass2.getText())){
                password = BCrypt.hashpw(pass1.getText(), BCrypt.gensalt());
            }else{
                test = 1;
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(" mot de passe");
                alert.setContentText("Confirmation differente");
                alert.showAndWait();
                /////Alerte
            }
        }
       
        if (test == 0)
        
        {User e = new User(username, "", 0, password, tel, nomM, prenomM, tel, nomM, typeM) ; 
        e.setPhoto(fileName);
        UserService us=new UserService(user);
        user = e;
        us.modifierUser(e,LoginController.getIdCnx());
        
       
        
         
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/compte.fxml"));
        Parent root = loader.load();
        root.setId("pane");
        
        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        scene1.getStylesheets().addAll(this.getClass().getResource("/Content/style.css").toExternalForm());
        stage.show();
        
        }
        
        }else{
            
            Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setHeaderText(" mot de passe");
                alert.setContentText("mot de passe incorrect");
                alert.showAndWait();
                System.out.println("aaaaaaaaaaaaaaaaaaaaa");
        }

    }

    @FXML
    public void close(ActionEvent actionEvent) throws Exception
    {
        Stage stage = (Stage) btnX.getScene().getWindow();
        stage.close();
    }
    
     @FXML
    void modifier(ActionEvent event) {

        if(cbModifierMotDePasse.isSelected()==true)
        { pass2.setVisible(true);
        pass1.setVisible(true);}
        else{
            pass2.setVisible(false);
        pass1.setVisible(false);}
        }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            
            hamburger();
            
            identifyUser();
            
            nom.setText(user.getNom());
            email.setText(user.getEmail());
            prenom.setText(user.getPrenom());
            username.setText(user.getUsername());
            txTelephone.setText(user.getTel());
            cbType.setValue(user.getType());
            fileName = user.getPhoto();
            
            fileName = user.getPhoto();
            
            pass2.setVisible(false);
            pass1.setVisible(false);
            
             ObservableList<String> options = 
    FXCollections.observableArrayList(
        "Admin",
        "Membre",
        "Membre Pro"
    );
                    cbType.setItems(options);
                    
        } catch (SQLException ex) {
            Logger.getLogger(ModifierProfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    
     private void identifyUser() throws SQLException
    {
                int id = LoginController.getIdCnx();
                System.out.println("ID = "+id);
                MyDB myDB = MyDB.getInstance();
                Statement stm = myDB.getConnexion().createStatement();
                ResultSet rest=stm.executeQuery("select * from user where id = "+id+"");
                
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
                
               
                
    }
    
    private void hamburger()
    {
        try {
            AnchorPane box = FXMLLoader.load(getClass().getResource("/views/drawerContent.fxml"));
            drawer.setSidePane(box);
            
            HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(ham1);
            
            ham1.addEventHandler(MouseEvent.MOUSE_PRESSED, (e)->{
                transition.setRate(transition.getRate()*(-1));
                transition.play();
                
                
                
                if(drawer.isShown())
                {
                    transition.setRate(-1);
                    drawer.close();
                }
                else
                {
                    transition.setRate(1);
                    drawer.open();
                }
            });
        } catch (IOException ex) {
            System.out.println("Erreur hamburger!");
        }
    }
    
}
