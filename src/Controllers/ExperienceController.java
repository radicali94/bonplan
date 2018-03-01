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
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.cells.editors.base.JFXTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import entite.Experience;
import entite.Reaction;
import entite.Reaction.ReactionType;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import service.ExperienceService;
import service.ReactionService;
import service.StatApi;


public class ExperienceController implements Initializable{
     private static int idEXP;
     private JFreeChart chart;
    public static int getIdEXP() {
        return idEXP;
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
    private JFXTreeTableView<Experience> treeview;
    @FXML
    private JFXButton btnShowEcperiences;
    @FXML
    private JFXButton btnAddExperience;
    @FXML
    private JFXButton btnMesExperiences;
    @FXML
    private JFXTextField search;

      @FXML
    private void makeShowExperiences(ActionEvent event) throws IOException {
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
    private void makeAddExperience(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/experienceAdd.fxml"));
        Parent root = loader.load();
        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        stage.show();
    }

   @FXML
    private void makeMesExperiences(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ExperienceMine.fxml"));
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
            Logger.getLogger(BonPlanController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        afficher();   
        hamburger(); 
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
private void afficher()
    {
        
//       JFXTreeTableColumn<Experience,ChartFrame> chart = new JFXTreeTableColumn<>("");
//        
//        
//        chart.setCellValueFactory((TreeTableColumn.CellDataFeatures<Experience, ChartFrame> param) ->{
//             try {
//                 /*ChartFrame box = new ChartFrame();
//                 box.getChildren().add(StatApi.drawChart(ReactionService.getInstance().selectReactions(
//                         param.getValue().getValue().getId_exp())));*/
//                 return new SimpleObjectProperty(StatApi.drawChart(ReactionService.getInstance().selectReactions(
//                         param.getValue().getValue().getId_exp())));
//             } catch (SQLException ex) {
//                 Logger.getLogger(ExperienceController.class.getName()).log(Level.SEVERE, null, ex);
//             }
//             return null;
//        });  
        
        JFXTreeTableColumn<Experience,Image> Image = new JFXTreeTableColumn<>("");
        Image.setPrefWidth(400);
        
        Image.setCellValueFactory((TreeTableColumn.CellDataFeatures<Experience, Image> param) ->{
            return new SimpleObjectProperty(new Image(param.getValue().getValue().getImg_exp()));
        });  
         
        JFXTreeTableColumn<Experience,String> Nom = new JFXTreeTableColumn<>("Nom");
        Nom.setPrefWidth(150);
        Nom.setCellValueFactory((TreeTableColumn.CellDataFeatures<Experience, String> param) ->  
                new SimpleStringProperty(param.getValue().getValue().getNom_exp()));
        
        JFXTreeTableColumn<Experience,String> Type = new JFXTreeTableColumn<>("Type");
        Type.setPrefWidth(150);
        Type.setCellValueFactory((TreeTableColumn.CellDataFeatures<Experience, String> param) ->  
                new SimpleStringProperty(param.getValue().getValue().getType_exp()));
       
          Experience exp = new Experience();
        service.ExperienceService exps = new ExperienceService(exp);
        ObservableList<Experience> experiences = exps.selectExperiences();
                
        final TreeItem<Experience> root = new RecursiveTreeItem<>(experiences,RecursiveTreeObject::getChildren);
        
        
        Image.setCellFactory(column -> new JFXTreeTableCell<Experience, Image>(){ 
        private final ImageView imageView1 = new ImageView();             
        private final ImageView imageView2 = new ImageView();             
        private final Tooltip tooltip = new Tooltip(); 

        { 
            imageView1.setFitWidth(250);
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
        
        
        treeview.getColumns().addAll(Image,Nom,Type/*chart*/);
        treeview.setRoot(root);
        treeview.setShowRoot(false);
        
        search.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                treeview.setPredicate(new Predicate<TreeItem<Experience>>() {
                    @Override
                    public boolean test(TreeItem<Experience> t) {
                         Boolean flag= t.getValue().getNom_exp().contains(newValue);
                                return flag;
                    }
                });
            }
        });
        
        

    }
     @FXML 
     private void makeContextMenu(ContextMenuEvent event) 
    {
        System.out.println(treeview.getSelectionModel().getSelectedItem().toString());
        final ContextMenu contextMenu = new ContextMenu();
        MenuItem info = new MenuItem("plus d'infos");
        MenuItem hide = new MenuItem("cacher");
        MenuItem supp = new MenuItem("supprimer");
       
        contextMenu.getItems().addAll(info, hide,supp);
        treeview.setContextMenu(contextMenu);
        
        info.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) 
            {
                idEXP=treeview.getSelectionModel().getSelectedItem().getValue().getId_exp(); 
                try 
                {
                    Stage stage = (Stage) btnX.getScene().getWindow();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/experienceDetails.fxml"));
                    Parent root = loader.load();
                    Scene scene1 = new Scene(root);
                    stage.setScene(scene1);
                    stage.show();
                    
                } catch (IOException ex) {
                    Logger.getLogger(BonPlanController.class.getName()).log(Level.SEVERE, null, ex);
                }
            
            }
        });
        supp.setOnAction(new EventHandler<ActionEvent>() {
            
           
            @Override
            public void handle(ActionEvent event) {
                idEXP=treeview.getSelectionModel().getSelectedItem().getValue().getId_exp();
                Experience e = new Experience(idEXP,"", "", Date.valueOf(LocalDate.MAX), "", "", "",1);
                ExperienceService s = new ExperienceService(e);
                s.supprimerExperience(idEXP);
                afficher();
                
            }
        } );
    }

  
}