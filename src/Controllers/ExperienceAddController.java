/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import Mains.Login;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import entite.Experience;
import entite.Upload;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import service.ExperienceService;

/**
 * FXML Controller class
 *
 * @author USER
 */
public class ExperienceAddController implements Initializable {
  
    private double xOffset = 0;
    private double yOffset = 0;
    private boolean checker;
   //private  java.util.Date check;
    
    @FXML
    private AnchorPane pane1;
    @FXML
    private JFXButton btnX;
    @FXML
    private JFXTextField search;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXHamburger ham1;
    @FXML
    private JFXButton btnAddExperience;
    @FXML
    private JFXTextField nomEXP;
    @FXML
    private JFXTextArea descEXP;
    @FXML
    private JFXTextField imgEXP;
    @FXML
    private JFXTextField lieuEXP;
    @FXML
    private JFXButton ajouterEXP;
    @FXML
    private ComboBox<String> typeEXP;
    @FXML
    private JFXButton btnShowExperiences;
    @FXML
    private JFXButton btnMesExperiences;
    @FXML
    private DatePicker dateEXP;
    @FXML
    private JFXButton btnImage;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        typeEXP.getItems().add("Café");
        typeEXP.getItems().add("Restaurant");
        typeEXP.getItems().add("Fast-food");
        typeEXP.getItems().add("Hôtel");
        typeEXP.getItems().add("Shopping");
        typeEXP.getItems().add("Bar à tapas");
        typeEXP.getItems().add("Sport et Spa");
        typeEXP.getItems().add("Arts et loisirs");
        typeEXP.getItems().add("Divertissement");
        typeEXP.setEditable(true);
        
         pane1.setOnMousePressed((javafx.scene.input.MouseEvent event) -> {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                });

                pane1.setOnMouseDragged((javafx.scene.input.MouseEvent event) -> {
                    Stage stage = (Stage) pane1.getScene().getWindow();
                    stage.setX(event.getScreenX()-xOffset);
                    stage.setY(event.getScreenY()-yOffset);
                });
    }    

    @FXML
    private void close(ActionEvent event) {
        Stage stage = (Stage) btnX.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void makeAddExperience(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/experienceAdd.fxml"));
        Parent root = loader.load();
        //root.setId("pane");
        
        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        //scene1.getStylesheets().addAll(this.getClass().getResource("/Content/style.css").toExternalForm());
        stage.show();
    }

    @FXML
    private void makeShowExperiences(ActionEvent event)  throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/experience.fxml"));
        Parent root = loader.load();
        root.setId("pane");
        
        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        scene1.getStylesheets().addAll(this.getClass().getResource("/Content/style.css").toExternalForm());
        stage.show();
    }

    @FXML
    private void makeMesExperiences(ActionEvent event)  throws IOException {
        
    }

    @FXML
    private void makeAjouterEXP(ActionEvent event)   {
        String nomS = nomEXP.getText();
        String typeS = typeEXP.getValue();
        Date dateS = Date.valueOf(dateEXP.getValue());
        String lieuS = lieuEXP.getText();
        String imgS = "file:C:/xampp/htdocs/images/"+imgEXP.getText();
        String descS = descEXP.getText();
        
        if(nomEXP.getText().trim().equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText("Le champ -Nom experience- est vide");
            alert.setContentText("Veuillez remplir tout les champs");
            alert.showAndWait();
        }
        
         if(lieuEXP.getText().trim().equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText("Le champ -Lieu experience- est vide");
            alert.setContentText("Veuillez remplir tout les champs");
            alert.showAndWait();
        }
         
         if(descEXP.getText().trim().equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText("Le champ -Description experience- est vide");
            alert.setContentText("Veuillez remplir tout les champs");
            alert.showAndWait();
        }
         if(imgEXP.getText().trim().equals(""))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("");
            alert.setHeaderText("Le champ -Image experience- est vide");
            alert.setContentText("Veuillez importer une image");
            alert.showAndWait();
        }
        
        
        
                
        
        Experience exp = new Experience( nomS,typeS,dateS,lieuS,imgS,descS,LoginController.getIdCnx());
        service.ExperienceService bps = new ExperienceService(exp);
        bps.ajouterExperience(exp);
        
    }

    @FXML
    private void addImage(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
        new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
           
           Upload u = new Upload();
           u.upload(selectedFile);
           imgEXP.setText(selectedFile.getName());
            
                  
        }
    }
    
}
