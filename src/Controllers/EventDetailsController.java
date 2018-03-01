package Controllers;


import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;

import com.itextpdf.text.Element;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
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
import entite.Evenement;
import entite.Evaluation;
import entite.Position;
import entite.User;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import javafx.embed.swing.SwingFXUtils;
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
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import org.controlsfx.control.Rating;
//import org.w3c.dom.Document;
import service.EvenementService;
import service.EvaluationService;
import service.PositionService;
import service.UserService;

public class EventDetailsController extends ListView<String> implements Initializable, MapComponentInitializedListener, DirectionsServiceCallback{

    private double xOffset = 0;
    private double yOffset = 0;
    
    @FXML
    private JFXTreeTableView<Evaluation> treeview;

    @FXML
    private JFXButton btnX;

    @FXML
    private JFXButton btnAddEvent;

    @FXML
    private JFXButton btnShowEvents;

    @FXML
    private JFXButton btnMyEvents;

    @FXML
    private JFXTextField search;

    @FXML
    private Label labelNom;

    private Label labelType;

   

    @FXML
    private Text labelDesc;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger ham1;
    @FXML
    private ImageView ImageE;
    
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
    private ImageView imgOwner;
   
    
    private String url;
    @FXML
    private JFXButton btn_res;

    
    

    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage) btnX.getScene().getWindow();
        stage.close();
    }

    @FXML
    void makeAddEvent(ActionEvent event) throws IOException {
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
    void makeMyEvents(ActionEvent event) throws IOException {
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
    void makeShowEvents(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/event.fxml"));
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
        hamburger();
        rating.ratingProperty().addListener((ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
            comm.setDisable(false);
            btnComment.setDisable(false);
            rateValue = newValue.floatValue();
        });
        
        int ide1 = EventController.getIdE();
        int idusr1 = LoginController.getIdCnx();
        
        
        
        Evaluation eval2 = new Evaluation();
        EvaluationService evals2 = new EvaluationService(eval2);
        
        try {
            eval2 = evals2.selectEvalByIDuserE(idusr1, ide1);
        } catch (SQLException ex) {
            Logger.getLogger(EventDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("rrrrrrrrrrrrrrrrr "+eval2.toString());
        
        
        
        if(eval2.getId_user()!=0)
        {
            
            btnComment.setText("Réévaluer");
            
        }
        else
        {
            btnComment.setText("Evaluer");
        }
        
        
        
        mapView.addMapInitializedListener(this);
         
        try {
            afficher();
        } catch (SQLException ex) {
            Logger.getLogger(EventDetailsController.class.getName()).log(Level.SEVERE, null, ex);
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
            int ide0 = EventController.getIdE();
            System.out.println("ID de l'evenement"+ide0);
            Evenement e1 = new Evenement();
            EvenementService es1 = new EvenementService(e1);
            e1 = es1.selectEvenement(ide0);
            
             User u1 = new User();
            UserService us1 = new UserService(u1);
            u1 = us1.selectUser(e1.getId_user());
            
            
            DropShadow drop = new DropShadow();
            imgOwner.setImage(new Image(u1.getPhoto()));
            imgOwner.setEffect(drop);
            labelNom.setText(e1.getNom_event());
            String descS = e1.getDesc_event();
            descS=descS.replace("_", "'");
            labelDesc.setText(descS);
            labelUser1.setText("   "+u1.getUsername());
            
            ImageE.setImage(new Image(e1.getImg_event()));
            
        } catch (SQLException ex) {
            Logger.getLogger(EventDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }

    @Override
    public void mapInitialized() {
        try {
            int ide1 = EventController.getIdE();
            Evenement e1 = new Evenement();
            EvenementService es1 = new EvenementService(e1);
            e1 = es1.selectEvenement(ide1);
            Position pos1 = new Position();
            PositionService pserv1 = new PositionService(pos1);
            pos1 = pserv1.selectPosition(e1.getLieu_event());
            
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
                    infoWindowOptions.content("<h2>"+e1.getNom_event() +"</h2>"
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
            Logger.getLogger(EventDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void directionsReceived(DirectionsResult results, DirectionStatus status) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @FXML
    private void makeComment(ActionEvent event) throws SQLException {
        
        int idusr1 = LoginController.getIdCnx();
        int ide1 = EventController.getIdE();
        
        Evaluation eval = new Evaluation(0, rateValue, comm.getText(), ide1, 0, 0, LoginController.getIdCnx());
        EvaluationService evals = new EvaluationService(eval);
        
        Evaluation eval2 = new Evaluation();
        EvaluationService evals2 = new EvaluationService(eval2);
        eval2 = evals2.selectEvalByIDuserE(idusr1, ide1);
        System.out.println(eval2.getId_user());
        if(eval2.getId_user()!= 0)
        {
            
            evals2.modifierEval(eval, eval2.getId_eval());
            afficher();
            comm.clear();
            comm.setDisable(true);
            btnComment.setDisable(true);
            
        }
        else
        {
            evals.ajouterEval(eval);
            afficher();
            btnComment.setText("Réévaluer");
            comm.clear();
            comm.setDisable(true);
            btnComment.setDisable(true);
            
        }
        
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
                Logger.getLogger(EventDetailsController.class.getName()).log(Level.SEVERE, null, ex);
            }
            return new SimpleStringProperty(u.getUsername());
        });
        
        
        
        
        
       
        //ObservableList<entite.BonPlan> bonPlans = FXCollections.observableArrayList();  
        
        int ide1 = EventController.getIdE();
        Evenement e1 = new Evenement();
        EvenementService es1 = new EvenementService(e1);
        e1 = es1.selectEvenement(ide1);
        
        Evaluation eval = new Evaluation();
        service.EvaluationService evs = new EvaluationService(eval);
        ObservableList<Evaluation> evals = (ObservableList<Evaluation>) evs.selectEvalsByIDE(e1.getId_event());
        
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

    
    
    
    
    private static Font titreFont = new Font(Font.FontFamily.TIMES_ROMAN, 45, Font.BOLD);
    private static Font dateFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.ORANGE);
    private static Font descFont = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.ITALIC);
    private static Font typeFont = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.NORMAL);
    private static Font nomFont = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.NORMAL);
    
      public void setImg(String url) throws IOException {
        FileInputStream input;
        try {
            input = new FileInputStream(url);
            Image img_event = SwingFXUtils.toFXImage(ImageIO.read(input), null);
            this.ImageE.setImage(img_event);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EventDetailsController.class.getName()).log(Level.SEVERE, null, ex);
       
        }
    }
    
      
      
      
    @FXML
    private void savePdf(ActionEvent event) throws BadElementException, IOException {
        try {
            Document doc = new Document();
            try {
                PdfWriter.getInstance(doc, new FileOutputStream("Reservation.pdf"));

            } catch (DocumentException ex) {
                Logger.getLogger(EventDetailsController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            doc.open();
            Paragraph p = new Paragraph();
            Paragraph ptitre = new Paragraph("reservation".toUpperCase(), titreFont);
            ptitre.setAlignment(Element.ALIGN_CENTER);
            p.add(ptitre);
            addEmptyLine(p, 1);
            com.itextpdf.text.Image pimage = com.itextpdf.text.Image.getInstance(url);
            pimage.setAlignment(Element.ALIGN_CENTER);
            Paragraph predig = new Paragraph(labelNom.getText(), nomFont);
            predig.setAlignment(Element.ALIGN_RIGHT);
            addEmptyLine(p, 2);
            
            p.add(predig);
            doc.add(p);
            
            Paragraph p2 = new Paragraph();
            
            Paragraph pdate = new Paragraph(labelType.getText(), typeFont);
            addEmptyLine(p2, 1);
            p2.add(pdate);
            Paragraph pdesc = new Paragraph(labelDesc.getText(), descFont);
            addEmptyLine(p2, 1);
            p2.add(pdesc);
            doc.add(p2);
            doc.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EventDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(EventDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            
    }

    
     private static void addEmptyLine(Paragraph paragraph, int nb) {
        for (int i = 0; i < nb; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }
    
}