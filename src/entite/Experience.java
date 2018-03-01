/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import service.ReactionService;

/**
 *
 * @author USER
 */
public class Experience extends RecursiveTreeObject<Experience>{
   private int id_exp;
    private String nom_exp;
    private String type_exp;
    private Date  date_exp;
    private String lieu_exp;
    private String img_exp;
    private String desc_exp;
    private int id_user;
    private List<Reaction> reactions;
    
     public Experience(){
        
    }

    public Experience(int id_exp, String nom_exp, String type_exp, Date date_exp, String lieu_exp, String img_exp, String desc_exp, int id_user) {
        this.id_exp = id_exp;
        this.nom_exp = nom_exp;
        this.type_exp = type_exp;
        this.date_exp = date_exp;
        this.lieu_exp = lieu_exp;
        this.img_exp = img_exp;
        this.desc_exp = desc_exp;
        this.id_user = id_user;
    }
      
    public Experience(String nom_exp, String type_exp, Date date_exp, String lieu_exp, String img_exp, String desc_exp, int id_user) {
        
        this.nom_exp = nom_exp;
        this.type_exp = type_exp;
        this.date_exp = date_exp;
        this.lieu_exp = lieu_exp;
        this.img_exp = img_exp;
        this.desc_exp = desc_exp;
        this.id_user = id_user;
    }

    public Experience(String nom_exp, String type_exp, Date date_exp, String lieu_exp, String img_exp, String desc_exp) {
        this.nom_exp = nom_exp;
        this.type_exp = type_exp;
        this.date_exp = date_exp;
        this.lieu_exp = lieu_exp;
        this.img_exp = img_exp;
        this.desc_exp = desc_exp;
    }

    
    
    public List<Reaction> getReactions() {
        return reactions;
    }

    public void setReactions() throws SQLException {
        this.reactions = ReactionService.getInstance().selectReactions(id_exp);
    }

   
     
      public int getId_exp() {
        return id_exp;
    }

    public void setId_exp(int id_exp) {
        this.id_exp = id_exp;
    }
    
     public String getNom_exp() {
        return nom_exp;
    }

    public void setNom_exp(String nom_exp) {
        this.nom_exp = nom_exp;
    }
    
    public String getType_exp() {
        return type_exp;
    }

    public void setType_exp(String type_exp) {
        this.type_exp = type_exp;
    }
    
    public Date getDate_exp() {
        return date_exp;
    }

    public void setDate_exp(Date date_exp) {
        this.date_exp = date_exp;
    }
    
    public String getLieu_exp() {
        return lieu_exp;
    }

    public void setLieu_exp(String lieu_exp) {
        this.lieu_exp = lieu_exp;
    }
    
    public String getImg_exp() {
        return img_exp;
    }

    public void setImg_exp(String img_exp) {
        this.img_exp = img_exp;
    }
    
    public String getDesc_exp() {
        return desc_exp;
    }

    public void setDesc_exp(String desc_exp) {
        this.desc_exp = desc_exp;
    }
    
    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }
    @Override
    public String toString() {
        return "Experience{id_exp="+id_exp + "nom_exp=" + nom_exp + ", type_exp=" + type_exp + "date_exp=" + date_exp + "lieu_exp=" + lieu_exp + "img_exp=" + img_exp + "desc_exp=" + desc_exp + '}';
    }
}
