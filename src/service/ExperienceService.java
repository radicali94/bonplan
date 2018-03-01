/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;
import java.sql.Date;
import connexionDatabase.MyDB;
import entite.Experience;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Radhi
 */

public class ExperienceService implements iService.iExperience
{

    MyDB myDB;
    public ExperienceService(Experience exp)
    {
        myDB = MyDB.getInstance();
    }
    
    @Override
     public void ajouterExperience(Experience exp)
     {
        String req="INSERT INTO experience (nom_exp,type_exp,date_exp,lieu_exp,img_exp,desc_exp,id_user) VALUES(?,?,?,?,?,?,?)";
        PreparedStatement pre;
        try {
            pre = myDB.getConnexion().prepareStatement(req);
        
         
         pre.setString(1, exp.getNom_exp());
         pre.setString(2, exp.getType_exp());
         pre.setDate(3,(Date) exp.getDate_exp());
         pre.setString(4, exp.getLieu_exp());
         pre.setString(5, exp.getImg_exp());
         pre.setString(6, exp.getDesc_exp()); 
         pre.setInt(7, exp.getId_user()); 
         pre.executeUpdate();
        System.out.println("Ajout OK!");
        } catch (SQLException ex) {
            System.out.println("Problème d'ajout");
        }
        
    }

   @Override
    public void supprimerExperience(int id) 
    {
        PreparedStatement prep;
        try 
        {
            prep = myDB.getConnexion().prepareStatement("delete from experience where id_exp = ?");
            prep.setInt(1, id);
            prep.executeUpdate();
            System.out.println("delete OK!");
        }
        catch (SQLException ex) 
        {
            System.out.println("Probleme de suppression!");
        }
        
    }

    @Override
    public ObservableList<Experience> selectExperiences() {
       ObservableList<Experience> experiences = FXCollections.observableArrayList();
        try
        {
        Statement stm = myDB.getConnexion().createStatement();
        ResultSet rest=stm.executeQuery("select * from experience");
        while(rest.next())
        {
            Experience exp = new Experience();
            
            exp.setId_exp(rest.getInt(1));
            exp.setNom_exp(rest.getString(2));
            exp.setType_exp(rest.getString(3));
            exp.setDate_exp(rest.getDate(4));
            exp.setLieu_exp(rest.getString(5));
            exp.setImg_exp(rest.getString(6));
            exp.setDesc_exp(rest.getString(7));
            exp.setId_user(rest.getInt(8));
            
            
            experiences.add(exp);
        }
        }
        catch (SQLException ex)
        {
            System.out.println("Probleme affichage");
        }
        return experiences;
    }
   
@Override
        public void updateexperience(Experience exp,int id_exp){
        try {
            PreparedStatement prep;
            String req;
            req = "UPDATE experience SET `nom_exp`=?,`type_exp`=?,`date_exp`=?,`lieu_exp`=?,`img_exp`=?,`desc_exp`=? WHERE id_exp="+id_exp;
            
            prep = myDB.getConnexion().prepareStatement(req);
            prep.setString(1, exp.getNom_exp());
            prep.setString(2, exp.getType_exp());
            prep.setDate(3,(Date)exp.getDate_exp());
            prep.setString(4, exp.getLieu_exp());
            prep.setString(5, exp.getImg_exp());
            prep.setString(6, exp.getDesc_exp());
            prep.executeUpdate();
            System.out.println("modification");
      
         } catch (SQLException ex) {
            Logger.getLogger(ExperienceService.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    public ObservableList<Experience> selectExperiencesByID(int idu) {
        ObservableList<Experience> experiences = FXCollections.observableArrayList();
        try
        {
        Statement stm = myDB.getConnexion().createStatement();
        ResultSet rest=stm.executeQuery("select * from experience where id_user ="+idu+"");
        while(rest.next())
        {
            Experience exp = new Experience();
            
            exp.setId_exp(rest.getInt(1));
            exp.setNom_exp(rest.getString(2));
            exp.setType_exp(rest.getString(3));
            exp.setDate_exp(rest.getDate(4));
            exp.setLieu_exp(rest.getString(5));
            exp.setImg_exp(rest.getString(6));
            exp.setDesc_exp(rest.getString(7));
            exp.setId_user(rest.getInt(8));
            
            
            experiences.add(exp);
        }
        }
        catch (SQLException ex)
        {
            System.out.println("Probleme affichage");
        }
        return experiences;
    }
    public Experience selectExperience(int id_exp) throws SQLException
    {
        
        Experience exp = new Experience();
        
        Statement stm = myDB.getConnexion().createStatement();
        ResultSet rest=stm.executeQuery("select * from experience where id_exp ="+id_exp+"");
            
            while(rest.next())
            {
            exp.setId_exp(rest.getInt(1));
            exp.setNom_exp(rest.getString(2));
            exp.setType_exp(rest.getString(3));
            exp.setDate_exp(rest.getDate(4));
            exp.setLieu_exp(rest.getString(5));
            exp.setImg_exp(rest.getString(6));
            exp.setDesc_exp(rest.getString(7));
            exp.setId_user(rest.getInt(8));
            }
            System.out.println(exp.toString());
            return exp;
            
        
        
    }

    @Override
    public List<Experience> selectBonPlansByID(int idu) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
