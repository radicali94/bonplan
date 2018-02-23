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
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.cell.ChoiceBoxTreeTableCell;
import javafx.scene.control.cell.ComboBoxTreeTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import service.BonPlanService;

/**
 * FXML Controller class
 *
 * @author Radhi
 */
public class BonPlanMineController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;
    
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
    private JFXTextField search;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXHamburger ham1;
    @FXML
    private JFXTreeTableView<BonPlan> treeview;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
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
    
    public void hamburger()
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
    
    
    
    private void afficher()
    {
        
        
        JFXTreeTableColumn<BonPlan,Image> Image = new JFXTreeTableColumn<>("");
        Image.setPrefWidth(400);
        Image.setCellValueFactory((TreeTableColumn.CellDataFeatures<BonPlan, Image> param) ->  
                new SimpleObjectProperty(new Image(param.getValue().getValue().getImg_bp())));
        
        JFXTreeTableColumn<BonPlan,String> Nom = new JFXTreeTableColumn<>("Nom");
        Nom.setPrefWidth(150);
        Nom.setCellValueFactory((TreeTableColumn.CellDataFeatures<BonPlan, String> param) ->  
                new SimpleStringProperty(param.getValue().getValue().getNom_bp()));
        
        JFXTreeTableColumn<BonPlan,String> Type = new JFXTreeTableColumn<>("Type");
        Type.setPrefWidth(150);
        Type.setCellValueFactory((TreeTableColumn.CellDataFeatures<BonPlan, String> param) ->  
                new SimpleStringProperty(param.getValue().getValue().getType_bp()));
        
        JFXTreeTableColumn<BonPlan,String> Lieu = new JFXTreeTableColumn<>("Lieu");
        Lieu.setPrefWidth(150);
        Lieu.setCellValueFactory((TreeTableColumn.CellDataFeatures<BonPlan, String> param) ->  
                new SimpleStringProperty(Integer.toString(param.getValue().getValue().getLieu_bp())));
        
        
        
        
        
       
        //ObservableList<entite.BonPlan> bonPlans = FXCollections.observableArrayList();  
        int idu = LoginController.getIdCnx();
        BonPlan bp = new BonPlan();
        service.BonPlanService bps = new BonPlanService(bp);
        ObservableList<BonPlan> bonPlans = bps.selectBonPlansByID(idu);
        
        
              
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
            imageView1.setFitHeight(200); 
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
        
        treeview.setEditable(true);
        treeview.getSelectionModel().getSelectedItem();
        //System.out.println(ch1);
        Nom.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        
        Nom.setOnEditCommit((TreeTableColumn.CellEditEvent<BonPlan, String> event) -> {
            TreeItem<BonPlan> currentEditingBonPlan = treeview.getTreeItem(event.getTreeTablePosition().getRow());
            currentEditingBonPlan.getValue().setNom_bp(event.getNewValue());
            
            BonPlan bp1 = currentEditingBonPlan.getValue();
            service.BonPlanService bps1 = new BonPlanService(bp1);
            bps1.modifierBonPlan(bp1, bp1.getId_bp());

        
        });
        
        ObservableList<String> listType = FXCollections.observableArrayList();
        listType.addAll("Café","Restaurant","Fast-food","Hôtel","Shopping","Bar à tapas","Sport et Spa","Arts et Loisirs","Divertissement");
        Type.setCellFactory(ChoiceBoxTreeTableCell.forTreeTableColumn(listType));
        
        
        Type.setOnEditCommit((TreeTableColumn.CellEditEvent<BonPlan, String> event) -> {
            TreeItem<BonPlan> currentEditingBonPlan = treeview.getTreeItem(event.getTreeTablePosition().getRow());
            currentEditingBonPlan.getValue().setType_bp(event.getNewValue());
            
            BonPlan bp1 = currentEditingBonPlan.getValue();
            System.out.println(currentEditingBonPlan.toString());
            service.BonPlanService bps1 = new BonPlanService(bp1);
            bps1.modifierBonPlan(bp1, bp1.getId_bp());

        
        });
        
        Lieu.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        
        Lieu.setOnEditCommit((TreeTableColumn.CellEditEvent<BonPlan, String> event) -> {
            TreeItem<BonPlan> currentEditingBonPlan = treeview.getTreeItem(event.getTreeTablePosition().getRow());
            currentEditingBonPlan.getValue().setLieu_bp(Integer.parseInt(event.getNewValue()));
            
            BonPlan bp1 = currentEditingBonPlan.getValue();
            service.BonPlanService bps1 = new BonPlanService(bp1);
            bps1.modifierBonPlan(bp1, bp1.getId_bp());

        
        });
        
        
        treeview.getColumns().addAll(Image,Nom,Type,Lieu);
        treeview.setRoot(root);
        treeview.setShowRoot(false);
    }
    
    private void identifyUser() throws SQLException
    {
                int id = LoginController.getIdCnx();
                System.out.println("ID = "+id);
                MyDB myDB = MyDB.getInstance();
                Statement stm = myDB.getConnexion().createStatement();
                ResultSet rest=stm.executeQuery("select * from user where id = "+id+"");
                
                entite.User u = new entite.User();
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
                
                /*labelUser.setText("Bienvenue "+u.getUsername()+"!");
                Circle circle = new Circle(75, 70, 67);
                circle.setEffect(new DropShadow());
                imgUser.setImage(new Image(u.getPhoto()));
                imgUser.setClip(circle);*/
                
    }

    @FXML
    private void makeContextMenu(ContextMenuEvent event) {
        
    }
    
    
    
}
