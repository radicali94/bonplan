/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.GMapMouseEvent;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.Animation;
import com.lynden.gmapsfx.javascript.object.DirectionsPane;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.InfoWindow;
import com.lynden.gmapsfx.javascript.object.InfoWindowOptions;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.service.directions.DirectionStatus;
import com.lynden.gmapsfx.service.directions.DirectionsRenderer;
import com.lynden.gmapsfx.service.directions.DirectionsRequest;
import com.lynden.gmapsfx.service.directions.DirectionsResult;
import com.lynden.gmapsfx.service.directions.DirectionsService;
import com.lynden.gmapsfx.service.directions.DirectionsServiceCallback;
import com.lynden.gmapsfx.service.directions.TravelModes;
import connexionDatabase.MyDB;
import entite.BonPlan;
import entite.Position;
import entite.Upload;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import service.BonPlanService;
import service.PositionService;

/**
 * FXML Controller class
 *
 * @author Radhi
 */
public class BonPlanAddController implements Initializable, MapComponentInitializedListener, DirectionsServiceCallback {

    private double xOffset = 0;
    private double yOffset = 0;
    
    private GoogleMap map;
    
    private double pos_lat;
    private double pos_long;
    
    @FXML
    private AnchorPane pane1;
    @FXML
    private JFXButton btnX;
    @FXML
    private JFXButton btnAddPlan;
    @FXML
    private JFXButton btnShowPlans;
    @FXML
    private JFXButton btnMesPlans;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXHamburger ham1;
    @FXML
    private JFXTextField nomBP;
    @FXML
    private JFXTextArea descBP;
    @FXML
    private JFXTextField lieuBP;
    @FXML
    private JFXTextField prixBP;
    @FXML
    private JFXButton ajouterBP;
    @FXML
    private JFXComboBox<String> typeBP;
    @FXML
    private JFXButton btnImage;
    @FXML
    private JFXTextField imageBP1;
    @FXML
    private GoogleMapView mapView;
    @FXML
    private JFXSlider slider;
    @FXML
    private ImageView myImageView;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        hamburger();
        descBP.setOnKeyTyped(event ->{
        int maxCharacters = 130;
        if(descBP.getText().length() > maxCharacters) event.consume();
        });
        
        prixBP.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.matches("\\d*")&& (prixBP.getText().length() < 5) && (prixBP.getText().length() > 0) ) {
                    int value = Integer.parseInt(newValue);
                } else {
                    prixBP.setText(oldValue);
                }
            }
        });
        
        typeBP.getItems().add("Café");
        typeBP.getItems().add("Restaurant");
        typeBP.getItems().add("Fast-food");
        typeBP.getItems().add("Hôtel");
        typeBP.getItems().add("Shopping");
        typeBP.getItems().add("Bar à tapas");
        typeBP.getItems().add("Sport et Spa");
        typeBP.getItems().add("Arts et loisirs");
        typeBP.getItems().add("Divertissement");
        typeBP.setEditable(true);
        
        mapView.addMapInitializedListener(this);
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
    private void makeAddPlan(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/bonPlanAdd.fxml"));
        Parent root = loader.load();
        //root.setId("pane");
        
        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        //scene1.getStylesheets().addAll(this.getClass().getResource("/Content/style.css").toExternalForm());
        stage.show();
    }

    @FXML
    private void makeShowPlans(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/bonPlan.fxml"));
        Parent root = loader.load();
        root.setId("pane");
        
        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        scene1.getStylesheets().addAll(this.getClass().getResource("/Content/style.css").toExternalForm());
        stage.show();
    }

    @FXML
    private void makeMesPlans(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/bonPlanMine.fxml"));
        Parent root = loader.load();
        root.setId("pane");
        
        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        scene1.getStylesheets().addAll(this.getClass().getResource("/Content/style.css").toExternalForm());
        stage.show();
    }

    
    @FXML
    private void makeAjouterBP(ActionEvent event) throws SQLException {
        
        if(nomBP.getText().length()==0)
        {
            nomBP.setStyle("-fx-text-inner-color: red");
            nomBP.setStyle("-fx-prompt-text-fill: red");
            nomBP.setStyle("-fx-border-color: red");
            
            Alert alert = new Alert(Alert.AlertType.WARNING);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.initStyle(StageStyle.TRANSPARENT);
            
            alert.setTitle("Attention");
            alert.setContentText("Veuillez nommer votre bon plan!");
            alert.showAndWait();
            lieuBP.setCursor(Cursor.WAIT);
            nomBP.setStyle("-fx-text-inner-color:  #663399");
            nomBP.setStyle("-fx-prompt-text-fill:  #663399");
            
            
        }
        else if(prixBP.getText().length()==0)
        {
            
            prixBP.setStyle("-fx-text-inner-color: red");
            prixBP.setStyle("-fx-prompt-text-fill: red");
            prixBP.setStyle("-fx-border-color: red");
            
            Alert alert = new Alert(Alert.AlertType.WARNING);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.initStyle(StageStyle.TRANSPARENT);
            alert.setTitle("Attention");
            alert.setContentText("Veuillez nommer votre bon plan!");
            alert.showAndWait();
            prixBP.setCursor(Cursor.WAIT);
            
            
            
        }
        else if(imageBP1.getText().length()==0)
        {
            imageBP1.setStyle("-fx-border-color: red");
            
            Alert alert = new Alert(Alert.AlertType.WARNING);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.initStyle(StageStyle.TRANSPARENT);
            alert.setTitle("Attention");
            alert.setContentText("Veuillez ajouter une image!");
            alert.showAndWait();
            
            
        }
        else if(descBP.getText().length()==0)
        {
            descBP.setStyle("-fx-text-inner-color: red");
            descBP.setStyle("-fx-prompt-text-fill: red");
            descBP.setStyle("-fx-border-color: red");
            
            Alert alert = new Alert(Alert.AlertType.WARNING);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.initStyle(StageStyle.TRANSPARENT);
            alert.setTitle("Attention");
            alert.setContentText("Veuillez saisir une description!");
            alert.showAndWait();
            descBP.setCursor(Cursor.WAIT);
            
            
        }
        else if(lieuBP.getText().length()==0)
        {
            lieuBP.setStyle("-fx-inner-color: red");
            lieuBP.setStyle("-fx-prompt-text-fill: red");
            lieuBP.setStyle("-fx-border-color: red");
            
            Alert alert = new Alert(Alert.AlertType.WARNING);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.initStyle(StageStyle.TRANSPARENT);
            alert.setTitle("Attention");
            alert.setContentText("Veuillez saisir la ville!");
            alert.showAndWait();
            lieuBP.setCursor(Cursor.WAIT);
            
            
        }
        else
        {
        
            String posS = lieuBP.getText();
            posS=posS.replace("'", "_");
            Position pos = new Position(0,posS,pos_lat,pos_long);
            PositionService ps = new PositionService(pos);
            ps.ajouterPosition(pos);
            Position pos1 = ps.selectPosition(lieuBP.getText());


            String nomS = nomBP.getText();
            String descS = descBP.getText();
            String typeS = typeBP.getValue();
            descS=descS.replace("'", "_");
            nomS=nomS.replace("'", "_");
            String imgS = "file:C:/xampp/htdocs/Images/"+imageBP1.getText();
            int lieuS = pos1.getId_pos();
            double prixS = Double.parseDouble(prixBP.getText());

            BonPlan bp = new BonPlan(1, nomS, typeS, descS, imgS, lieuS, prixS, LoginController.getIdCnx());
            service.BonPlanService bps = new BonPlanService(bp);
            bps.ajouterBonPlan(bp);
        }
        
    }

    @FXML
    private void addImage(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
        new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
           
           Upload u = new Upload();
           u.upload(selectedFile);
           imageBP1.setText(selectedFile.getName());
            
                  
        }
    }

    @Override
    public void mapInitialized() {
                
                    
                    //Set the initial properties of the map.
                    MapOptions mapOptions = new MapOptions();
                    
                    mapOptions.center(new LatLong(36.8065, 10.1815))
                            .mapType(MapTypeIdEnum.ROADMAP)
                            .overviewMapControl(false)
                            .panControl(false)
                            .rotateControl(false)
                            .scaleControl(false)
                            .streetViewControl(false)
                            .zoomControl(false)
                            .zoom(12);
                    
                    map = mapView.createMap(mapOptions);
            
                    //add Pin with long and lat
                    map.addMouseEventHandler(UIEventType.click, (GMapMouseEvent event) -> {
                    LatLong latLong = event.getLatLong();
                    System.out.println("Latitude: " + latLong.getLatitude());
                    System.out.println("Longitude: " + latLong.getLongitude());
                    LatLong myLocation1 = new LatLong(latLong.getLatitude() ,latLong.getLongitude());
                    MarkerOptions myMarkerOptions1 = new MarkerOptions();
                    myMarkerOptions1.position(myLocation1);
                    
                    Marker myMarker1 = new Marker(myMarkerOptions1);
                    
                    map.clearMarkers();
                    map.addMarker( myMarker1 );
                    myMarker1.setTitle("Ma position");
                    myMarker1.setAnimation(Animation.BOUNCE);
                    
                    pos_lat = latLong.getLatitude();
                    pos_long = latLong.getLongitude();
                    
                    //Position pos = new Position(0,lieuBP.getText(),latLong.getLongitude(),latLong.getLatitude());
                    
                 
                    });
    }

    @Override
    public void directionsReceived(DirectionsResult results, DirectionStatus status) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void makePrix(MouseEvent event) {
        Double prix = slider.getValue();
        int prix1 = (int)Math.round(prix);
        prixBP.setText(Integer.toString(prix1));
    }

    @FXML
    private void makeSlide(KeyEvent event) {
        if(prixBP.getText().length()>0)
        {
            String prixS1 = prixBP.getText().toString();
            slider.setValue(Double.parseDouble(prixS1));
        }
        
    }
    
    @FXML
    private void imageDragOver(DragEvent de) {
        Dragboard board = de.getDragboard();
      if (board.hasFiles()) {
        de.acceptTransferModes(TransferMode.ANY);
      }
    }

    @FXML
    private void imageDropped(DragEvent de) throws IOException {
        try {
        Dragboard board = de.getDragboard();
        List<File> phil = board.getFiles();
        FileInputStream fis;
          fis = new FileInputStream(phil.get(0));
        Image image = new Image(fis);
        myImageView.setImage(image);
        /*File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
           
           Upload u = new Upload();
           u.upload(selectedFile);
           imageBP1.setText(selectedFile.getName());*/
        File selectedFile = phil.get(0);
        if (selectedFile != null) {
           
           Upload u = new Upload();
           u.upload(selectedFile);
           imageBP1.setText(selectedFile.getName());
            
                  
        }
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
    
    private void hamburger()
    {
        try {
            AnchorPane box = FXMLLoader.load(getClass().getResource("/views/drawerContent.fxml"));
            drawer.setSidePane(box);
            
            
            HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(ham1);
            
            pane1.addEventHandler(MouseEvent.MOUSE_PRESSED, e1->{
                
                if(drawer.isShown())
                {
                    transition.setRate(-1);
                    transition.play();
                    drawer.close();
                }
                });
            
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
