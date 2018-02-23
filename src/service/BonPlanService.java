/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import connexionDatabase.MyDB;
import entite.BonPlan;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Radhi
 */
public class BonPlanService implements iService.iBonPlan
{

    MyDB myDB;
    public BonPlanService(BonPlan bp)
    {
        myDB = MyDB.getInstance();
    }
    
    
    @Override
    public void ajouterBonPlan(BonPlan bp) {
        
        try {
            java.util.Date utilDate = new java.util.Date();
            java.sql.Date date1 = new java.sql.Date(utilDate.getTime());
            Statement stm = myDB.getConnexion().createStatement();
            String query = "insert into bon_plan (nom_bp,type_bp,desc_bp,img_bp,lieu_bp,prix_bp,id_user,date_bp) values ('"+bp.getNom_bp()+"','"+bp.getType_bp()+"','"+bp.getDesc_bp()+"','"+bp.getImg_bp()+"',"+bp.getLieu_bp()+","+bp.getPrix_bp()+","+bp.getId_user()+",'"+date1+"')";//varchar 'var'
            stm.executeUpdate(query);
            System.out.println("Ajout OK!");
        } catch (SQLException ex) {
            Logger.getLogger(BonPlanService.class.getName()).log(Level.SEVERE, null, ex);
        }
       
        
    }

    @Override
    public void supprimerBonPlan(BonPlan bp) 
    {
        PreparedStatement prep;
        try 
        {
            prep = myDB.getConnexion().prepareStatement("delete from bon_plan where nom_bp = ?");
            prep.setString(1, bp.getNom_bp());
            prep.executeUpdate();
            System.out.println("delete OK!");
        }
        catch (SQLException ex) 
        {
            System.out.println("Probleme de suppression!");
        }
        
    }

    @Override
    public ObservableList<BonPlan> selectBonPlans() {
        ObservableList<BonPlan> bonPlans = FXCollections.observableArrayList();
        try
        {
        Statement stm = myDB.getConnexion().createStatement();
        ResultSet rest=stm.executeQuery("select * from bon_plan");
        while(rest.next())
        {
            BonPlan bp = new BonPlan();
            
            bp.setId_bp(rest.getInt(1));
            bp.setNom_bp(rest.getString(2));
            bp.setType_bp(rest.getString(3));
            bp.setDesc_bp(rest.getString(4));
            bp.setImg_bp(rest.getString(5));
            bp.setLieu_bp(rest.getInt(6));
            bp.setPrix_bp(rest.getDouble(7));
            bp.setId_user(rest.getInt(8));
            bp.setDate_bp(rest.getDate(9));
            
            bonPlans.add(bp);
        }
        }
        catch (SQLException ex)
        {
            System.out.println("Probleme affichage");
        }
        return bonPlans;
    }
    
    @Override
    public void modifierBonPlan(BonPlan bp,int id_bp){
        
        
        try {
            PreparedStatement prep;
            String req;
            req = "UPDATE bon_plan SET `nom_bp`=?,`type_bp`=?,`desc_bp`=?,`img_bp`=?,`lieu_bp`=?,`prix_bp`=? WHERE id_bp = "+id_bp;
            
            prep= myDB.getConnexion().prepareStatement(req);
            
            prep.setString(1, bp.getNom_bp());
            prep.setString(2, bp.getType_bp());
            prep.setString(3, bp.getDesc_bp());
            prep.setString(4, bp.getImg_bp());
            prep.setInt(5, bp.getLieu_bp());
            prep.setDouble(6, bp.getPrix_bp());
            
            prep.executeUpdate();
            System.out.println("Modification OK!");
         } catch (SQLException ex) {
            System.out.println("Problème de Modification");
        }
      }

    @Override
    public ObservableList<BonPlan> selectBonPlansByID(int idu) {
        ObservableList<BonPlan> bonPlans = FXCollections.observableArrayList();
        try
        {
        Statement stm = myDB.getConnexion().createStatement();
        ResultSet rest=stm.executeQuery("select * from bon_plan where id_user ="+idu+"");
        while(rest.next())
        {
            BonPlan bp = new BonPlan();
            
            bp.setId_bp(rest.getInt(1));
            bp.setNom_bp(rest.getString(2));
            bp.setType_bp(rest.getString(3));
            bp.setDesc_bp(rest.getString(4));
            bp.setImg_bp(rest.getString(5));
            bp.setLieu_bp(rest.getInt(6));
            bp.setPrix_bp(rest.getDouble(7));
            bp.setId_user(rest.getInt(8));
            bp.setDate_bp(rest.getDate(9));
            
            
            bonPlans.add(bp);
        }
        }
        catch (SQLException ex)
        {
            System.out.println("Probleme affichage");
        }
        return bonPlans;
    }

    @Override
    public BonPlan selectBonPlan(int id_bp) throws SQLException
    {
        
        BonPlan bp = new BonPlan();
        
        Statement stm = myDB.getConnexion().createStatement();
        ResultSet rest=stm.executeQuery("select * from bon_plan where id_bp ="+id_bp+"");
            
            while(rest.next())
            {
                bp.setId_bp(rest.getInt(1));
                bp.setNom_bp(rest.getString(2));
                bp.setType_bp(rest.getString(3));
                bp.setDesc_bp(rest.getString(4));
                bp.setImg_bp(rest.getString(5));
                bp.setLieu_bp(rest.getInt(6));
                bp.setPrix_bp(rest.getDouble(7));
                bp.setId_user(rest.getInt(8));
                bp.setDate_bp(rest.getDate(9));
            }
            System.out.println(bp.toString());
            return bp;
            
        
        
    }
    
}
