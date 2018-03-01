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
import entite.Evenement;
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
import service.EvenementService;

/**
 * FXML Controller class
 *
 * @author Mariem
 */
public class EventMineController implements Initializable {

    private double xOffset = 0;
    private double yOffset = 0;
    
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
    private JFXTextField search;
    @FXML
    private JFXDrawer drawer;
    @FXML
    private JFXHamburger ham1;
    @FXML
    private JFXTreeTableView<Evenement> treeview;

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
        
        
        JFXTreeTableColumn<Evenement,Image> Image = new JFXTreeTableColumn<>("");
        Image.setPrefWidth(400);
        Image.setCellValueFactory((TreeTableColumn.CellDataFeatures<Evenement, Image> param) ->  
                new SimpleObjectProperty(new Image(param.getValue().getValue().getImg_event())));
        
        JFXTreeTableColumn<Evenement,String> Nom = new JFXTreeTableColumn<>("Nom");
        Nom.setPrefWidth(150);
        Nom.setCellValueFactory((TreeTableColumn.CellDataFeatures<Evenement, String> param) ->  
                new SimpleStringProperty(param.getValue().getValue().getNom_event()));
        
        JFXTreeTableColumn<Evenement,String> Type = new JFXTreeTableColumn<>("Type");
        Type.setPrefWidth(150);
        Type.setCellValueFactory((TreeTableColumn.CellDataFeatures<Evenement, String> param) ->  
                new SimpleStringProperty(param.getValue().getValue().getType_event()));
        
       JFXTreeTableColumn<Evenement,String> Date = new JFXTreeTableColumn<>("Date");
        Date.setPrefWidth(100);
        Date.setCellValueFactory((TreeTableColumn.CellDataFeatures<Evenement, String> param) ->  
                new SimpleStringProperty((param.getValue().getValue().getDate_event().toString())));
        
        
        
        
        
       
        //ObservableList<entite.BonPlan> bonPlans = FXCollections.observableArrayList();  
        int idu = LoginController.getIdCnx();
        Evenement e = new Evenement();
        service.EvenementService es = new EvenementService(e);
        ObservableList<Evenement> evenements = es.selectEvenementsByID(idu);
        
        
              
       
        
        final TreeItem<Evenement> root = new RecursiveTreeItem<>(evenements,RecursiveTreeObject::getChildren);
        
        
        Image.setCellFactory(column -> new JFXTreeTableCell<Evenement, Image>(){ 
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
        
        Nom.setOnEditCommit((TreeTableColumn.CellEditEvent<Evenement, String> event) -> {
            TreeItem<Evenement> currentEditingEvenement = treeview.getTreeItem(event.getTreeTablePosition().getRow());
            currentEditingEvenement.getValue().setNom_event(event.getNewValue());
            
            Evenement e1 = currentEditingEvenement.getValue();
            service.EvenementService es1 = new EvenementService(e1);
            es1.modifierEvenement(e1, e1.getId_event());

        
        });
        
        ObservableList<String> listType = FXCollections.observableArrayList();
        listType.addAll("Culturel","Loisirs","Sportif");
        Type.setCellFactory(ChoiceBoxTreeTableCell.forTreeTableColumn(listType));
        
        
        Type.setOnEditCommit((TreeTableColumn.CellEditEvent<Evenement, String> event) -> {
            TreeItem<Evenement> currentEditingEvenement = treeview.getTreeItem(event.getTreeTablePosition().getRow());
            currentEditingEvenement.getValue().setType_event(event.getNewValue());
            
            Evenement e1 = currentEditingEvenement.getValue();
            System.out.println(currentEditingEvenement.toString());
            service.EvenementService es1 = new EvenementService(e1);
            es1.modifierEvenement(e1, e1.getId_event());

        
        });
        
        Date.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        
        Date.setOnEditCommit((TreeTableColumn.CellEditEvent<Evenement, String> event) -> {
            TreeItem<Evenement> currentEditingEvenement = treeview.getTreeItem(event.getTreeTablePosition().getRow());
            currentEditingEvenement.getValue().setLieu_event(Integer.parseInt(event.getNewValue()));
            
            Evenement e1 = currentEditingEvenement.getValue();
            service.EvenementService es1 = new EvenementService(e1);
            es1.modifierEvenement(e1, e1.getId_event());

        
        });
        
        
        treeview.getColumns().addAll(Image,Nom,Type,Date);
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
