/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.sql.Date;

/**
 *
 * @author Radhi
 */
public class Evenement extends RecursiveTreeObject<Evenement> {
    private int id_event;
    private String nom_event;
    private String type_event;
    private String desc_event;
    
    private java.util.Date utilDate = new java.util.Date();
    private java.sql.Date date_event = new java.sql.Date(utilDate.getTime());
    private int lieu_event;
    private String img_event;
    private int id_user;

    public Evenement(int i, String nomS, String typeS, String descS, String imgS, int lieuS, int idCnx) {
        this.id_event = i;
        this.nom_event = nomS;
        this.type_event = typeS;
        this.desc_event = descS;
        this.img_event = imgS;
        
        this.lieu_event = lieuS;
        
        this.id_user = idCnx;
    }

    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    public String getNom_event() {
        return nom_event;
    }

    public void setNom_event(String nom_event) {
        this.nom_event = nom_event;
    }

    public String getType_event() {
        return type_event;
    }

    public void setType_event(String type_event) {
        this.type_event = type_event;
    }

    public String getDesc_event() {
        return desc_event;
    }

    public void setDesc_event(String desc_event) {
        this.desc_event = desc_event;
    }

    public String getImg_event() {
        return img_event;
    }

    public void setImg_event(String img_event) {
        this.img_event = img_event;
    }

    public Date getDate_event() {
        return date_event;
    }

    public void setDate_event(Date date_event) {
        this.date_event = date_event;
    }

    public int getLieu_event() {
        return lieu_event;
    }

    public void setLieu_event(int lieu_event) {
        this.lieu_event = lieu_event;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    /*public Evenement(int id_event, String nom_event, String type_event, String desc_event,  Date date_event, int lieu_event,String img_event, int id_user) {
        this.id_event = id_event;
        this.nom_event = nom_event;
        this.type_event = type_event;
        this.desc_event = desc_event;
        
        this.date_event = date_event;
        this.lieu_event = lieu_event;
        this.img_event = img_event;
        this.id_user = id_user;
    }

   */
    
    
   
    
    public Evenement()
    {
        
    }

    @Override
    public String toString() {
        return "\nEvenement{" + "id_event=" + id_event + ", nom_event=" + nom_event + ", type_event=" + type_event + ", desc_event=" + desc_event + ",  date_event=" + date_event + ", lieu_event=" + lieu_event + ",img_event=" + img_event + " , id_user=" + id_user + '}';
    }
    
   
    
}
