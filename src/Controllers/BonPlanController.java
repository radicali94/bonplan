/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.cells.editors.base.JFXTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import connexionDatabase.MyDB;
import entite.BonPlan;
import entite.User;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import service.BonPlanService;

/**
 *
 * @author Radhi
 */
public class BonPlanController extends ListView<String> implements Initializable{

    private static int idBP;

    public static int getIdBP() {
        return idBP;
    }
    
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
    @FXML
    private JFXTextField search;
    @FXML
    private JFXTreeTableView<BonPlan> treeview;
    @FXML
    private JFXButton btnAddPlan;
    @FXML
    private JFXButton btnShowPlans;
    @FXML
    private JFXButton btnMesPlans;

    @FXML
    private void makeAddPlan(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/bonPlanAdd.fxml"));
        Parent root = loader.load();
        root.setId("pane");
        
        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        scene1.getStylesheets().addAll(this.getClass().getResource("/Content/style.css").toExternalForm());
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
    private void close(ActionEvent event) {
        Stage stage = (Stage) btnX.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
                pane1.setOnMousePressed((javafx.scene.input.MouseEvent event) -> {
                    xOffset = event.getSceneX();
                    yOffset = event.getSceneY();
                });

                pane1.setOnMouseDragged((javafx.scene.input.MouseEvent event) -> {
                    Stage stage = (Stage) pane1.getScene().getWindow();
                    stage.setX(event.getScreenX()-xOffset);
                    stage.setY(event.getScreenY()-yOffset);
                });
        
        afficher();   
        hamburger();           
        
    }
    
    
    
    
    
    
    /*class BonPlan extends RecursiveTreeObject<BonPlan>
    {
        
        ObjectProperty<Image> image;
        StringProperty nom;
        StringProperty type;
        StringProperty desc;

        public BonPlan(Image image, String nom, String type, String desc) {
            this.image = new SimpleObjectProperty<>(image);
            this.nom = new SimpleStringProperty(nom);
            this.type = new SimpleStringProperty(type);
            this.desc = new SimpleStringProperty(desc);
        }
        

        public BonPlan(String nom ,String type, String desc) {
            this.type = new SimpleStringProperty(type);
            this.desc = new SimpleStringProperty(desc);
            this.nom = new SimpleStringProperty(nom);
        }
        
    }*/
    
    
    private void afficher()
    {
        
        
        JFXTreeTableColumn<BonPlan,Image> Image = new JFXTreeTableColumn<>("");
        Image.setPrefWidth(200);
        Image.setCellValueFactory((TreeTableColumn.CellDataFeatures<BonPlan, Image> param) ->  
                new SimpleObjectProperty(new Image(param.getValue().getValue().getImg_bp())));
        
        JFXTreeTableColumn<BonPlan,String> Nom = new JFXTreeTableColumn<>("Nom");
        Nom.setPrefWidth(150);
        Nom.setCellValueFactory((TreeTableColumn.CellDataFeatures<BonPlan, String> param) ->  
                new SimpleStringProperty(param.getValue().getValue().getNom_bp()));
        
        JFXTreeTableColumn<BonPlan,String> Type = new JFXTreeTableColumn<>("Type");
        Type.setPrefWidth(0);
        Type.setCellValueFactory((TreeTableColumn.CellDataFeatures<BonPlan, String> param) ->  
                new SimpleStringProperty(param.getValue().getValue().getType_bp()));
        
        JFXTreeTableColumn<BonPlan,String> Date = new JFXTreeTableColumn<>("Date");
        Date.setPrefWidth(100);
        Date.setCellValueFactory((TreeTableColumn.CellDataFeatures<BonPlan, String> param) ->  
                new SimpleStringProperty((param.getValue().getValue().getDate_bp().toString())));
        
        
        
        
        
       
        //ObservableList<entite.BonPlan> bonPlans = FXCollections.observableArrayList();  
        
        BonPlan bp = new BonPlan();
        service.BonPlanService bps = new BonPlanService(bp);
        ObservableList<BonPlan> bonPlans = bps.selectBonPlans();
        
        
              
        /*users.add(new BonPlan(new Image("/Content/img9.JPG"),"Camping - Zaghouan","Mehdi Jrebi","24"));
        users.add(new BonPlan(new Image("/Content/DSC_0383.JPG"),"Bibo Café - Manar1","Nacer Cherni","35"));
        users.add(new BonPlan(new Image("/Content/DSC_0173.JPG"),"Hôtel La cigogne - Tabarka ","Radicali","30"));
        users.add(new BonPlan(new Image("/Content/DSC_0139.JPG"),"Randonnée - Bni Mtir","Allalah","20"));
        users.add(new BonPlan(new Image("/Content/img9.JPG"),"Camping - Zaghouan","Mehdi Jrebi","24"));
        users.add(new BonPlan(new Image("/Content/DSC_0383.JPG"),"Bibo Café - Manar1","Nacer Cherni","35"));
        users.add(new BonPlan(new Image("/Content/DSC_0173.JPG"),"Hôtel La cigogne - Tabarka ","Radicali","30"));
        users.add(new BonPlan(new Image("/Content/DSC_0139.JPG"),"Randonnée - Bni Mtir","Allalah","20"));
        users.add(new BonPlan(new Image("/Content/img9.JPG"),"Camping - Zaghouan","Mehdi Jrebi","24"));
        users.add(new BonPlan(new Image("/Content/DSC_0383.JPG"),"Bibo Café - Manar1","Nacer Cherni","35"));
        users.add(new BonPlan(new Image("/Content/DSC_0173.JPG"),"Hôtel La cigogne - Tabarka ","Radicali","30"));
        users.add(new BonPlan(new Image("/Content/DSC_0139.JPG"),"Randonnée - Bni Mtir","Allalah","20"));*/
        
        final TreeItem<BonPlan> root = new RecursiveTreeItem<>(bonPlans,RecursiveTreeObject::getChildren);
        
        
        Image.setCellFactory(column -> new JFXTreeTableCell<BonPlan, Image>(){ 
        private final ImageView imageView1 = new ImageView();             
        private final ImageView imageView2 = new ImageView();             
        private final Tooltip tooltip = new Tooltip(); 

        { 
            imageView1.setFitHeight(100);
            imageView1.setFitWidth(100);
            imageView1.setPreserveRatio(true); 
            imageView1.setSmooth(true); 
            tooltip.setText(null); 
            tooltip.setGraphic(imageView2);
            imageView2.setFitHeight(300);
            imageView2.setPreserveRatio(true);
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
        
        
       
        
        
        treeview.getColumns().addAll(Nom,Type,Date,Image);
        treeview.setRoot(root);
        treeview.setShowRoot(false);
        Type.setVisible(false);
        
        
        
        search.textProperty().addListener(new ChangeListener<String>(){
             @Override
             public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                        treeview.setPredicate(new Predicate<TreeItem<BonPlan>>() {
                            @Override
                            public boolean test(TreeItem<BonPlan> t) {
                                 Boolean flag= (t.getValue().getNom_bp().contains(newValue)||
                                                t.getValue().getType_bp().contains(newValue)
                                                //|| t.getValue().getLieu_bp().contains(newValue)
                                                );
                                 
                                 return flag;
                            }
                        });
             }
            
        });
        
    }
    
    
    
    
    /*private void setNode(Node node)
    {
        
        
        FadeTransition ft = new FadeTransition(Duration.millis(1500));
        ft.setNode(node);
        ft.setFromValue(0.1);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.setAutoReverse(false);
        ft.play();
    }*/
    
    
    
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
                
                treeview.addEventHandler(MouseEvent.MOUSE_PRESSED, e1->{
                
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

    @FXML
    private void makeContextMenu(ContextMenuEvent event) 
    {
        System.out.println(treeview.getSelectionModel().getSelectedItem().toString());
        final ContextMenu contextMenu = new ContextMenu();
        MenuItem info = new MenuItem("plus d'infos");
        MenuItem hide = new MenuItem("cacher");
       
        contextMenu.getItems().addAll(info, hide);
        treeview.setContextMenu(contextMenu);
        
        info.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) 
            {
                idBP=treeview.getSelectionModel().getSelectedItem().getValue().getId_bp(); 
                try 
                {
                    Stage stage = (Stage) btnX.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/bonPlanDetails.fxml"));
                    Parent root = loader.load();
                    root.setId("pane");
                    
                    Scene scene1 = new Scene(root);
                    stage.setScene(scene1);
                    scene1.getStylesheets().addAll(this.getClass().getResource("/Content/style.css").toExternalForm());
                    stage.show();
                    
                } catch (IOException ex) {
                    Logger.getLogger(BonPlanController.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            }
        });
    }
    
}
