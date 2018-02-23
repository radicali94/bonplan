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
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
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

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
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
    
    
}
