package Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.cells.editors.base.JFXTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
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
import com.lynden.gmapsfx.javascript.object.MVCArray;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapShape;
import com.lynden.gmapsfx.javascript.object.MarkerOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import com.lynden.gmapsfx.javascript.object.Marker;
import com.lynden.gmapsfx.service.directions.DirectionStatus;
import com.lynden.gmapsfx.service.directions.DirectionsRenderer;
import com.lynden.gmapsfx.service.directions.DirectionsRequest;
import com.lynden.gmapsfx.service.directions.DirectionsResult;
import com.lynden.gmapsfx.service.directions.DirectionsService;
import com.lynden.gmapsfx.service.directions.DirectionsServiceCallback;
import com.lynden.gmapsfx.service.directions.TravelModes;
import com.lynden.gmapsfx.shapes.Polyline;
import com.lynden.gmapsfx.shapes.PolylineOptions;
import entite.BonPlan;
import entite.Evaluation;
import entite.Position;
import entite.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;
import jdk.nashorn.api.scripting.JSObject;
import org.controlsfx.control.Rating;
import service.BonPlanService;
import service.EvaluationService;
import service.PositionService;
import service.UserService;

public class BonPlanDetailsController extends ListView<String> implements Initializable, MapComponentInitializedListener, DirectionsServiceCallback{

    private double xOffset = 0;
    private double yOffset = 0;
    
    @FXML
    private JFXTreeTableView<Evaluation> treeview;

    @FXML
    private JFXButton btnX;

    @FXML
    private JFXButton btnAddPlan;

    @FXML
    private JFXButton btnShowPlans;

    @FXML
    private JFXButton btnMesPlans;

    @FXML
    private JFXTextField search;

    @FXML
    private Label labelNom;

    @FXML
    private Label labelType;

    @FXML
    private Label labelPrix;

    @FXML
    private Text labelDesc;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger ham1;
    @FXML
    private ImageView ImageBP;
    
    private GoogleMap map;
    @FXML
    private GoogleMapView mapView;
    @FXML
    private JFXButton btnComment;
    @FXML
    private AnchorPane pane1;
    @FXML
    private Rating rating;
    @FXML
    private Label labelUser1;
    
    public float rateValue;
    @FXML
    private JFXTextArea comm;

    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage) btnX.getScene().getWindow();
        stage.close();
    }

    @FXML
    void makeAddPlan(ActionEvent event) throws IOException {
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
    void makeMesPlans(ActionEvent event) throws IOException {
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
    void makeShowPlans(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/bonPlan.fxml"));
        Parent root = loader.load();
        root.setId("pane");
        
        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        scene1.getStylesheets().addAll(this.getClass().getResource("/Content/style.css").toExternalForm());
        stage.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        rating.ratingProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            rateValue = newValue.floatValue();
            System.out.println("pppppppp\n"+newValue);
            System.out.println("pppppppp\n"+rateValue);
        });
        
        
        mapView.addMapInitializedListener(this);
         
        try {
            afficher();
        } catch (SQLException ex) {
            Logger.getLogger(BonPlanDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
         
                pane1.setOnMousePressed((javafx.scene.input.MouseEvent event) -> {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                });

                pane1.setOnMouseDragged((javafx.scene.input.MouseEvent event) -> {
                    Stage stage = (Stage) pane1.getScene().getWindow();
                    stage.setX(event.getScreenX()-xOffset);
                    stage.setY(event.getScreenY()-yOffset);
                });
         
                 
        try {
            int idbp1 = BonPlanController.getIdBP();
            System.out.println("ID du bon plan"+idbp1);
            BonPlan bp1 = new BonPlan();
            BonPlanService bps1 = new BonPlanService(bp1);
            bp1 = bps1.selectBonPlan(idbp1);
            
            User u1 = new User();
            UserService us1 = new UserService(u1);
            u1 = us1.selectUser(bp1.getId_user());
            
            labelNom.setText(bp1.getNom_bp());
            labelType.setText(bp1.getType_bp());
            labelDesc.setText(bp1.getDesc_bp());
            labelUser1.setText(u1.getUsername());
            labelPrix.setText(Double.toString(bp1.getPrix_bp())+" DT");
            ImageBP.setImage(new Image(bp1.getImg_bp()));
            
        } catch (SQLException ex) {
            Logger.getLogger(BonPlanDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    @Override
    public void mapInitialized() {
        try {
            int idbp1 = BonPlanController.getIdBP();
            BonPlan bp1 = new BonPlan();
            BonPlanService bps1 = new BonPlanService(bp1);
            bp1 = bps1.selectBonPlan(idbp1);
            Position pos1 = new Position();
            PositionService pserv1 = new PositionService(pos1);
            pos1 = pserv1.selectPosition(bp1.getLieu_bp());
            
            System.out.println(pos1.toString());
            System.out.println(pos1.getLatitude());
            System.out.println(pos1.getLongitude());
                    //LatLong myLocation = new LatLong(36.8065, 10.1815);
                    LatLong myLocation = new LatLong(pos1.getLongitude() ,pos1.getLatitude());
                    
                    
                    
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
                    
                    //Add markers to the map
                    MarkerOptions myMarkerOptions = new MarkerOptions();
                    myMarkerOptions.position(myLocation);
                    
                    Marker myMarker = new Marker(myMarkerOptions);
                    
                    map.addMarker( myMarker );
                                        
                    InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
                    infoWindowOptions.content("<h2>"+bp1.getNom_bp() +"</h2>"
                            + pos1.getNom_pos()+"<br>");
                    
                    InfoWindow myInfoWindow = new InfoWindow(infoWindowOptions);
                    myInfoWindow.open(map, myMarker);
                    
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
                    map.addMarker( myMarker );
                    map.addMarker( myMarker1 );
                    myMarker1.setTitle("Ma position");
                    
                    
                    
                    DirectionsService directionsService = new DirectionsService();
                    DirectionsPane directionsPane = mapView.getDirec();
                    
                    DirectionsRequest request = new DirectionsRequest(myLocation, myLocation1, TravelModes.TRANSIT);
                    
                    directionsService.getRoute(request,this, new DirectionsRenderer(true, mapView.getMap(), directionsPane));
                    
                    
                    
                    myMarker1.setAnimation(Animation.BOUNCE);
                    double dist =myLocation.distanceFrom(myLocation1);
                    InfoWindowOptions infoWindowOptions1 = new InfoWindowOptions();
                    infoWindowOptions1.content("<h2>"+"Ma position" +"</h2>"
                            + "Distance: "+(int)dist/1000+" km"+"<br>"
                            + "ETA:"+(int)((dist*6)/4000) +" minutes" );
                    InfoWindow myInfoWindow1 = new InfoWindow(infoWindowOptions1);
                    myInfoWindow1.open(map, myMarker1);
                    //myInfoWindow.close();
                    
                    
                        System.out.println("Distance= "+dist);
                    
                    });
        } catch (SQLException ex) {
            Logger.getLogger(BonPlanDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void directionsReceived(DirectionsResult results, DirectionStatus status) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void makeComment(ActionEvent event) throws SQLException {
        
        User u = new User();
        UserService us = new UserService(u);
        u=us.selectUser(LoginController.getIdCnx());
        
        
        System.out.println("pppppppp\n"+rateValue);
        int idbp1 = BonPlanController.getIdBP();
        
        Evaluation eval = new Evaluation(0, rateValue, comm.getText(), idbp1, 0, 0, u.getId());
        EvaluationService evals = new EvaluationService(eval);
        evals.ajouterEval(eval);
        
    }
    
    private void afficher() throws SQLException
    {
        
        
        JFXTreeTableColumn<Evaluation,Image> Note = new JFXTreeTableColumn<>("Note");
        Note.setPrefWidth(150);
        Note.setCellValueFactory((TreeTableColumn.CellDataFeatures<Evaluation, Image> param) ->  
                new SimpleObjectProperty(new Image("/Content/rating"+(int)(param.getValue().getValue().getVal_eval())+".png")));
         //"+((int)(param.getValue().getValue().getVal_eval()))+"
        JFXTreeTableColumn<Evaluation,String> Comm = new JFXTreeTableColumn<>("Commentaire");
        Comm.setPrefWidth(150);
        Comm.setCellValueFactory((TreeTableColumn.CellDataFeatures<Evaluation, String> param) ->  
                new SimpleStringProperty(param.getValue().getValue().getComment_eval()));
        
        JFXTreeTableColumn<Evaluation,String> Nom = new JFXTreeTableColumn<>("User");
        Nom.setPrefWidth(100);
        Nom.setCellValueFactory((TreeTableColumn.CellDataFeatures<Evaluation, String> param) -> {
            User u = new User();
            UserService us = new UserService(u);
            
            try {
                u = us.selectUser(param.getValue().getValue().getId_user());
                
            } catch (SQLException ex) {
                Logger.getLogger(BonPlanDetailsController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return new SimpleStringProperty(u.getUsername());
        });
        
        
        
        
        
       
        //ObservableList<entite.BonPlan> bonPlans = FXCollections.observableArrayList();  
        
        int idbp1 = BonPlanController.getIdBP();
        BonPlan bp1 = new BonPlan();
        BonPlanService bps1 = new BonPlanService(bp1);
        bp1 = bps1.selectBonPlan(idbp1);
        
        Evaluation eval = new Evaluation();
        service.EvaluationService evs = new EvaluationService(eval);
        ObservableList<Evaluation> evals = (ObservableList<Evaluation>) evs.selectEvalsByIDbp(bp1.getId_bp());
        
        final TreeItem<Evaluation> root = new RecursiveTreeItem<>(evals,RecursiveTreeObject::getChildren);
        
        
        Note.setCellFactory(column -> new JFXTreeTableCell<Evaluation, Image>(){ 
        private final ImageView imageView1 = new ImageView();             
        private final ImageView imageView2 = new ImageView();             
        private final Tooltip tooltip = new Tooltip(); 

        { 
            imageView1.setFitHeight(20); 
            imageView1.setPreserveRatio(true); 
            imageView1.setSmooth(true); 
            tooltip.setText(null); 
            tooltip.setGraphic(imageView2); 
        }
        

        @Override 
        protected void updateItem(Image item, boolean empty) { 
            super.updateItem(item, empty);  
            setGraphic(null); 
            setText(null); 
            setTooltip(null); 
            if (!empty && item != null) { 
                imageView1.setImage(item); 
                imageView2.setImage(item); 
                setGraphic(imageView1); 
                setTooltip(tooltip); 
            } 
        }             
    });
        
        
       
        
        
        treeview.getColumns().addAll(Nom,Comm,Note);
        treeview.setRoot(root);
        treeview.setShowRoot(false);
        
        search.textProperty().addListener(new ChangeListener<String>(){
             @Override
             public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        treeview.setPredicate(new Predicate<TreeItem<Evaluation>>() {
                            @Override
                            public boolean test(TreeItem<Evaluation> t) {
                                 Boolean flag= (t.getValue().getComment_eval().contains(newValue));
                                 
                                 return flag;
                            }
                        });
             }
            
        });
        
    }

}
