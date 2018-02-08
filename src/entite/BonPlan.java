/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;

/**
 *
 * @author Radhi
 */
public class BonPlan {
    private int id_bp;
    private String nom_bp;
    private String type_bp;
    private String desc_bp;
    private String img_bp;
    private String lieu_bp;
    private double prix_bp;
    private int id_user;

    public int getId_bp() {
        return id_bp;
    }

    public void setId_bp(int id_bp) {
        this.id_bp = id_bp;
    }

    public String getNom_bp() {
        return nom_bp;
    }

    public void setNom_bp(String nom_bp) {
        this.nom_bp = nom_bp;
    }

    public String getType_bp() {
        return type_bp;
    }

    public void setType_bp(String type_bp) {
        this.type_bp = type_bp;
    }

    public String getDesc_bp() {
        return desc_bp;
    }

    public void setDesc_bp(String desc_bp) {
        this.desc_bp = desc_bp;
    }

    public String getImg_bp() {
        return img_bp;
    }

    public void setImg_bp(String img_bp) {
        this.img_bp = img_bp;
    }

    public String getLieu_bp() {
        return lieu_bp;
    }

    public void setLieu_bp(String lieu_bp) {
        this.lieu_bp = lieu_bp;
    }

    public double getPrix_bp() {
        return prix_bp;
    }

    public void setPrix_bp(double prix_bp) {
        this.prix_bp = prix_bp;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    
    
    public BonPlan(int id_bp,String nom_bp, String type_bp,String desc_bp, String img_bp,String lieu_bp, double prix_bp, int id_user)
    {
        this.id_bp=id_bp;
        this.nom_bp=nom_bp;
        this.type_bp=type_bp;
        this.desc_bp=desc_bp;
        this.img_bp=img_bp;
        this.lieu_bp=lieu_bp;
        this.prix_bp=prix_bp;
        this.id_user=id_user;
    }
    
    public BonPlan()
    {
        
    }
    
    @Override
    public String toString()
    {
        return "\nBon Plan:\nID: "+id_bp+"\nNom: "+nom_bp+"\nType: "+type_bp+"\nDesc: "+desc_bp+"\nImage: "+img_bp
                +"\nLieu: "+lieu_bp+"\nPrix: "+prix_bp+"\nUserID: "+id_user+"\n";
    }
    
}
