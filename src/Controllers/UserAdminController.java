/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import connexionDatabase.MyDB;
import entite.User;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.UserService;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import connexionDatabase.MyDB;
import entite.User;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Omrane
 */
public class UserAdminController implements Initializable {
  private double xOffset = 0;
    private double yOffset = 0;
    
    @FXML
    private JFXButton btnX;
    @FXML
    private JFXHamburger ham1;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private AnchorPane pane1;
    private Label labelUser;
    
    static User user;
    
    static int id;
    static int id1;
     
    @FXML
    private TableView<User> tvUser;

    @FXML
    private TableColumn<User, String> colNom;

    @FXML
    private TableColumn<User, String> colPrenom;

    @FXML
    private TableColumn<User, String> colEmail;

    @FXML
    private TableColumn<User, String> colLogin;

    @FXML
    private TableColumn<User, String> colTel;

    @FXML
    private TableColumn<User, String> colType;
    
     @FXML
    private TableView<User> tvUser1;

    @FXML
    private TableColumn<User, String> colNom1;

    @FXML
    private TableColumn<User, String> colPrenom1;

    @FXML
    private TableColumn<User, String> colEmail1;

    @FXML
    private TableColumn<User, String> colLogin1;

    @FXML
    private TableColumn<User, String> colTel1;

    @FXML
    private TableColumn<User, String> colType1;
    
    
    
    @FXML
    private JFXButton btAjouter;

    @FXML
    private JFXButton btSupprimer;

    @FXML
    private JFXButton btBanner;

      @FXML
    private JFXButton btAccepter;
    @FXML
    private JFXTextField txRecherche;
    @FXML
    private JFXTextField txRecherche1;
      
      
         @FXML
    void accepterAdmin(ActionEvent event) throws ParseException {
        
        UserService us = new UserService(user);
             System.out.println("---------------"+id1);
        us.acceptAdmin(id1);
         buildTableviewData();
          
          
          buildTableviewData1();

    }

    @FXML
    void ajouter(ActionEvent event) throws IOException {
        
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
    void bannrMembre(ActionEvent event) {
        
          UserService us = new UserService(user);
          us.bannerUser(id);

    }
     @FXML
    void supprimer(ActionEvent event) throws ParseException {
        UserService us = new UserService(user);
        us.supprimerUser(id);
         buildTableviewData();
          
          
          buildTableviewData1();

    }

    
    private void EventGetData() {
        tvUser.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends User> observable,
                User oldValue,
                User newValue) -> {
            if (newValue == null) {
             
              
                
               
                return;
            }
            id = newValue.getId();
            System.out.println(String.valueOf(id1));
            
        });
    }
    
    private void EventGetData1() {
        tvUser1.getSelectionModel().selectedItemProperty().addListener((ObservableValue<? extends User> observable,
                User oldValue,
                User newValue) -> {
            if (newValue == null) {
             
              
                
               
                return;
            }
            id1 = newValue.getId();
            System.out.println("+++++++++++++++++++++++++"+String.valueOf(id1));
            
        });
    }
    
     private void buildTableviewData() throws ParseException {
       
         
         UserService es = new UserService(user);
        
        ObservableList<User> data = es.selectUsers();
        
        if(!data.isEmpty())
         
        {colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colLogin.setCellValueFactory(new PropertyValueFactory<>("username"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
                

    
        
    
        
        
        tvUser.setItems(data);}
      
        
      }
     
     private void buildTableviewData1() throws ParseException {
       
           UserService es = new UserService(user);
        
        ObservableList<User> data = es.selectUsersAdmin();
        
        tvUser1.setItems(data);
        if(!data.isEmpty()){
         
         colNom1.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom1.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colEmail1.setCellValueFactory(new PropertyValueFactory<>("email"));
        colLogin1.setCellValueFactory(new PropertyValueFactory<>("username"));
        colTel1.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colType1.setCellValueFactory(new PropertyValueFactory<>("type"));
                

    
        
    
      
      
        }
      }

    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) btnX.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
      try {
          hamburger();
          
          identifyUser();
          buildTableviewData();
          
          
          buildTableviewData1();
          
          
          EventGetData();
          EventGetData1();
      } catch (SQLException ex) {
          Logger.getLogger(UserAdminController.class.getName()).log(Level.SEVERE, null, ex);
      } catch (ParseException ex) {
          Logger.getLogger(UserAdminController.class.getName()).log(Level.SEVERE, null, ex);
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

    @FXML
    private void rechercher(KeyEvent event) throws ParseException {
        buildTableviewDataRecherche();
    }

    @FXML
    private void rechercher2(KeyEvent event) throws ParseException {
        buildTableviewDataRecherche1();
    }
    
    private void buildTableviewDataRecherche() throws ParseException {
       
           if((txRecherche.getText().equalsIgnoreCase(" "))||(txRecherche.getText().equalsIgnoreCase("")))
       {
           buildTableviewData();
           System.out.println("null");
       }else{

         UserService es = new UserService(user);
        
        ObservableList<User> data = es.rechercheUsers(txRecherche.getText());
        
        if(!data.isEmpty())
         
        {colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colLogin.setCellValueFactory(new PropertyValueFactory<>("username"));
        colTel.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));           
        
    
        
        
        tvUser.setItems(data);}
         }
        
      }
      
      private void buildTableviewDataRecherche1() throws ParseException {
       
          if((txRecherche1.getText().equalsIgnoreCase(" "))||(txRecherche1.getText().equalsIgnoreCase("")))
       {
           buildTableviewData1();
           
       }else{
         UserService es = new UserService(user);
        
        ObservableList<User> data = es.rechercheUsers2(txRecherche1.getText());
        
        if(!data.isEmpty())
         
        {colNom1.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colPrenom1.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        colEmail1.setCellValueFactory(new PropertyValueFactory<>("email"));
        colLogin1.setCellValueFactory(new PropertyValueFactory<>("username"));
        colTel1.setCellValueFactory(new PropertyValueFactory<>("tel"));
        colType1.setCellValueFactory(new PropertyValueFactory<>("type"));           
        
    
        
        
        tvUser1.setItems(data);}
          }
        
      }
    
}
