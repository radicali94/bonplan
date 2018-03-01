/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
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
import entite.Evenement;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import service.EvenementService;
import service.PositionService;

/**
 * FXML Controller class
 *
 * @author Mariem
 */
public class EventAddController implements Initializable, MapComponentInitializedListener, DirectionsServiceCallback {

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
    private JFXButton btnAddEvent;
    @FXML
    private JFXButton btnShowEvents;
    @FXML
    private JFXButton btnMyEvents;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXHamburger ham1;
    @FXML
    private JFXTextField nomE;
    @FXML
    private JFXTextArea descE;
    @FXML
    private JFXTextField lieuE;
    
    @FXML
    private JFXButton ajouterE;
    @FXML
    private JFXComboBox<String> typeE;
    @FXML
    private JFXButton btnImage;
    @FXML
    private JFXTextField imageE1;
    @FXML
    private GoogleMapView mapView;
    @FXML
    private JFXDatePicker dateE;
     @FXML
    private ImageView myImageView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        typeE.getItems().add("Culturel");
        typeE.getItems().add("Loisirs");
        typeE.getItems().add("Sportif");
       
        typeE.setEditable(true);
        
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
    private void makeAddEvent(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/eventAdd.fxml"));
        Parent root = loader.load();
        //root.setId("pane");
        
        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        //scene1.getStylesheets().addAll(this.getClass().getResource("/Content/style.css").toExternalForm());
        stage.show();
    }

    @FXML
    private void makeShowEvents(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/event.fxml"));
        Parent root = loader.load();
        root.setId("pane");
        
        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        scene1.getStylesheets().addAll(this.getClass().getResource("/Content/style.css").toExternalForm());
        stage.show();
    }

    @FXML
    private void makeMyEvents(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/eventMine.fxml"));
        Parent root = loader.load();
        root.setId("pane");
        
        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        scene1.getStylesheets().addAll(this.getClass().getResource("/Content/style.css").toExternalForm());
        stage.show();
    }

    
    @FXML
    private void makeAjouterE(ActionEvent event) throws SQLException {
        
        String posS = lieuE.getText();
        posS=posS.replace("'", "_");
        Position pos = new Position(0,posS,pos_lat,pos_long);
        PositionService ps = new PositionService(pos);
        ps.ajouterPosition(pos);
        Position pos1 = ps.selectPosition(lieuE.getText());
        
        
        String nomS = nomE.getText();
        String descS = descE.getText();
        String typeS = typeE.getValue();
        descS=descS.replace("'", "_");
        nomS=nomS.replace("'", "_");
        String imgS = "file:C:/xampp/htdocs/Images/"+imageE1.getText();
        int lieuS = pos1.getId_pos();
        
        
        Evenement e = new Evenement(1, nomS, typeS, descS, imgS, lieuS, LoginController.getIdCnx());
        service.EvenementService es = new EvenementService(e);
        es.ajouterEvenement(e);
        
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
           imageE1.setText(selectedFile.getName());
            
                  
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
    private void imageDragOver(DragEvent de) {
        Dragboard board = de.getDragboard();
      if (board.hasFiles()) {
        de.acceptTransferModes(TransferMode.ANY);
      }
    }

    @FXML
    private void imageDropped(DragEvent de) {
        try {
        Dragboard board = de.getDragboard();
        List<File> phil = board.getFiles();
        FileInputStream fis;
          fis = new FileInputStream(phil.get(0));
        Image image = new Image(fis);
        myImageView.setImage(image);
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      }
    }
    
    
}
