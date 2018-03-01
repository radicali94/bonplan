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
import entite.Experience;
import entite.Reaction;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import service.ExperienceService;
import service.ReactionService;

public class ExperienceDetailsController implements Initializable{

    @FXML
    private JFXTreeTableView<Experience> treeview;

    @FXML
    private JFXButton btnX;

    @FXML
    private JFXButton btnAddExperience;

    @FXML
    private JFXButton btnShowExperiences;

    @FXML
    private JFXButton btnMesExperiences;

    @FXML
    private JFXTextField search;

    @FXML
    private Label labelNom;

    @FXML
    private Label labelType;

    @FXML
    private Label labelLieu;

    @FXML
    private Text labelDesc;

    @FXML
    private JFXDrawer drawer;

    @FXML
    private JFXHamburger ham1;
    @FXML
    private ImageView ImageEXP;

    @FXML
    void close(ActionEvent event) {
        Stage stage = (Stage) btnX.getScene().getWindow();
        stage.close();
    }

    @FXML
    void makeAddPlan(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ExperienceAdd.fxml"));
        Parent root = loader.load();
        Scene scene1 = new Scene(root);
        stage.setScene(scene1);
        stage.show();
    }

    @FXML
    void makeMesPlans(ActionEvent event) throws IOException {
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
    void makeShowPlans(ActionEvent event) throws IOException {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/experience.fxml"));
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
         try {
            int idexp1 = ExperienceController.getIdEXP();
            System.out.println("ID de l'experience"+idexp1);
            Experience exp1 = new Experience();
            ExperienceService exps1 = new ExperienceService(exp1);
       
            exp1 = exps1.selectExperience(idexp1);
            
            ImageEXP.setImage(new Image(exp1.getImg_exp()));
            labelNom.setText(exp1.getNom_exp());
            labelType.setText(exp1.getType_exp());
            labelLieu.setText(exp1.getLieu_exp());
            labelDesc.setText(exp1.getDesc_exp());
           
        } catch (SQLException ex) {
            Logger.getLogger(ExperienceDetailsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        afficher();
    }
    private void afficher()
    {
        
        
        JFXTreeTableColumn<Experience,Image> Image = new JFXTreeTableColumn<>("");
        Image.setPrefWidth(400);
        
        Image.setCellValueFactory((TreeTableColumn.CellDataFeatures<Experience, Image> param) ->{
            return new SimpleObjectProperty(new Image(param.getValue().getValue().getImg_exp()));
        });  
        
        
        JFXTreeTableColumn<Experience,HBox> reaction = new JFXTreeTableColumn<>("");
        
        reaction.setCellValueFactory((TreeTableColumn.CellDataFeatures<Experience, HBox> param) ->{
            Experience exp = param.getValue().getValue();
            try {
                exp.setReactions();
            } catch (SQLException ex) {
                Logger.getLogger(ExperienceController.class.getName()).log(Level.SEVERE, null, ex);
            }
            int likes = (int) exp.getReactions().stream().filter(r -> r.getType() == Reaction.ReactionType.like).count();
            int dislikes = (int) exp.getReactions().stream().filter(r -> r.getType() == Reaction.ReactionType.dislike).count();
            
            HBox h = new HBox();
            ImageView likeImage = new ImageView(new Image(getClass().getResourceAsStream("/Content/like.png")));
            ImageView dislikeImage = new ImageView(new Image(getClass().getResourceAsStream("/Content/dislike.png")));
            likeImage.setFitHeight(50);
            likeImage.setFitWidth(50);
            dislikeImage.setFitHeight(50);
            dislikeImage.setFitWidth(50);
            
            Button like = new Button("" + likes,likeImage);
            Button dislike = new Button("" + dislikes,dislikeImage);
            if(exp.getId_user() != LoginController.getIdCnx()){
                like.setOnAction(e -> {
                try {
                    if(exp.getReactions().stream().anyMatch(r ->
                        r.getId_user() == LoginController.getIdCnx() && r.getType() == Reaction.ReactionType.like)){
                        ReactionService.getInstance().supprimerReaction(LoginController.getIdCnx());
                        exp.getReactions().removeIf(r -> r.getId_user() == LoginController.getIdCnx());
                        like.setText("" + exp.getReactions().stream().filter(r -> r.getType() == Reaction.ReactionType.like).count());
                    }
                    else if(exp.getReactions().stream().anyMatch(r ->
                        r.getId_user() == LoginController.getIdCnx() && r.getType() == Reaction.ReactionType.dislike)){
                        ReactionService.getInstance().supprimerReaction(LoginController.getIdCnx());
                        exp.getReactions().removeIf(r -> r.getId_user() == LoginController.getIdCnx());
                        Reaction r = new Reaction(0,LoginController.getIdCnx(),exp.getId_exp(),
                                Reaction.ReactionType.like);
                        ReactionService.getInstance().ajouterReaction(r);
                        exp.getReactions().add(r);
                        like.setText("" + exp.getReactions().stream().filter(rr -> rr.getType() == Reaction.ReactionType.like).count());
                        dislike.setText("" + exp.getReactions().stream().filter(rr -> rr.getType() == Reaction.ReactionType.dislike).count());
                    }
                    else{
                        Reaction r = new Reaction(0,LoginController.getIdCnx(),exp.getId_exp(),
                                Reaction.ReactionType.like);
                        ReactionService.getInstance().ajouterReaction(r);
                        exp.getReactions().add(r);
                        like.setText("" + exp.getReactions().stream().filter(rr -> rr.getType() == Reaction.ReactionType.like).count());
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ExperienceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            dislike.setOnAction(e -> {
                try {
                    if(exp.getReactions().stream().anyMatch(r ->
                        r.getId_user() == LoginController.getIdCnx() && r.getType() == Reaction.ReactionType.dislike)){
                        ReactionService.getInstance().supprimerReaction(LoginController.getIdCnx());
                        exp.getReactions().removeIf(r -> r.getId_user() == LoginController.getIdCnx());
                        dislike.setText("" + exp.getReactions().stream().filter(r -> r.getType() == Reaction.ReactionType.dislike).count());
                    }
                    else if(exp.getReactions().stream().anyMatch(r ->
                        r.getId_user() == LoginController.getIdCnx() && r.getType() == Reaction.ReactionType.like)){
                        ReactionService.getInstance().supprimerReaction(LoginController.getIdCnx());
                        exp.getReactions().removeIf(r -> r.getId_user() == LoginController.getIdCnx());
                        Reaction r = new Reaction(0,LoginController.getIdCnx(),exp.getId_exp(),
                                Reaction.ReactionType.dislike);
                        ReactionService.getInstance().ajouterReaction(r);
                        exp.getReactions().add(r);
                        dislike.setText("" + exp.getReactions().stream().filter(rr -> rr.getType() == Reaction.ReactionType.dislike).count());
                        like.setText("" + exp.getReactions().stream().filter(rr -> rr.getType() == Reaction.ReactionType.like).count());
                    }
                    else{
                        Reaction r = new Reaction(0,LoginController.getIdCnx(),exp.getId_exp(),
                                Reaction.ReactionType.dislike);
                        ReactionService.getInstance().ajouterReaction(r);
                        exp.getReactions().add(r);
                        dislike.setText("" + exp.getReactions().stream().filter(rr -> rr.getType() == Reaction.ReactionType.dislike).count());
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(ExperienceController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            }
            
            h.getChildren().addAll(like,dislike);
            return new SimpleObjectProperty(h);
        });  
         
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
        
        
        treeview.getColumns().addAll(reaction);
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

}
