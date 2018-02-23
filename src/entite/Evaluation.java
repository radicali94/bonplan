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
public class Evaluation extends RecursiveTreeObject<Evaluation>{
    private int id_eval;
    private float val_eval;
    private String comment_eval;
    private int id_bp,id_bus,id_event,id_user;
    private java.util.Date utilDate = new java.util.Date();
    private java.sql.Date date_eval = new java.sql.Date(utilDate.getTime());

    public Date getDate_eval() {
        return date_eval;
    }

    public void setDate_eval(Date date_eval) {
        this.date_eval = date_eval;
    }
    
    

    public int getId_eval() {
        return id_eval;
    }

    public void setId_eval(int id_eval) {
        this.id_eval = id_eval;
    }

    public float getVal_eval() {
        return val_eval;
    }

    public void setVal_eval(float val_eval) {
        this.val_eval = val_eval;
    }

    public String getComment_eval() {
        return comment_eval;
    }

    public void setComment_eval(String comment_eval) {
        this.comment_eval = comment_eval;
    }

    public int getId_bp() {
        return id_bp;
    }

    public void setId_bp(int id_bp) {
        this.id_bp = id_bp;
    }

    public int getId_bus() {
        return id_bus;
    }

    public void setId_bus(int id_bus) {
        this.id_bus = id_bus;
    }

    public int getId_event() {
        return id_event;
    }

    public void setId_event(int id_event) {
        this.id_event = id_event;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public Evaluation() {
    }

    public Evaluation(int id_eval, float val_eval, String comment_eval, int id_bp, int id_bus, int id_event, int id_user) {
        this.id_eval = id_eval;
        this.val_eval = val_eval;
        this.comment_eval = comment_eval;
        this.id_bp = id_bp;
        this.id_bus = id_bus;
        this.id_event = id_event;
        this.id_user = id_user;
    }

    @Override
    public String toString() {
        return "Evaluation{" + "id_eval=" + id_eval + ", val_eval=" + val_eval + ", comment_eval=" + comment_eval + ", id_bp=" + id_bp + ", id_bus=" + id_bus + ", id_event=" + id_event + ", id_user=" + id_user + '}';
    }
    
    
    
}
