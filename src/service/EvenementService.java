/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import connexionDatabase.MyDB;
import entite.Evenement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Radhi
 */
public class EvenementService implements iService.iEvenement
{

    MyDB myDB;
    public EvenementService(Evenement e)
    {
        myDB = MyDB.getInstance();
    }
    
    
    @Override
    public void ajouterEvenement(Evenement e) {
        try 
        {
        Statement stm = myDB.getConnexion().createStatement();
        String query = "insert into event (nom_event,type_event,desc_event,img_event,date_event,lieu_event,id_user) values ('"+e.getNom_event()+"','"+e.getType_event()+"','"+e.getDesc_event()+"','"+e.getImg_event()+"','"+e.getDate_event()+"','"+e.getLieu_event()+"',"+e.getId_user()+")";//varchar 'var'
        stm.executeUpdate(query);
            System.out.println("Ajout OK!");
        }
        catch(SQLException ex)
        {
            System.out.println("probleme d'ajout!");
        }
    }

    @Override
    public void supprimerEvenement(Evenement e) 
    {   
        PreparedStatement prep;
        try 
        {
            prep = myDB.getConnexion().prepareStatement("delete from event where nom_event = ?");
            prep.setString(1, e.getNom_event());
            prep.executeUpdate();
            System.out.println("delete OK!");
        }
        catch (SQLException ex) 
        {
            System.out.println("Probleme de suppression!");
        }
        
    }

    @Override
    public List<Evenement> selectEvenements() {
        List<Evenement> evenements = new ArrayList<>();
        try
        {
        Statement stm = myDB.getConnexion().createStatement();
        ResultSet rest=stm.executeQuery("select * from event");
        while(rest.next())
        {
            Evenement e = new Evenement();
            
            e.setId_event(rest.getInt(1));
            e.setNom_event(rest.getString(2));
            e.setType_event(rest.getString(3));
            e.setDesc_event(rest.getString(4));
            e.setImg_event(rest.getString(5));
            e.setDate_event(rest.getDate(6));
            e.setLieu_event(rest.getString(7));
            e.setId_user(rest.getInt(8));
            
            
            evenements.add(e);
        }
        }
        catch (SQLException ex)
        {
            System.out.println("Probleme affichage");
        }
        return evenements;
    }
    
    @Override
    public void modifierEvenement(Evenement e,int id_e){
        
        
        try {
            PreparedStatement prep;
            String req;
            req = "UPDATE event SET `nom_event`=?,`type_event`=?,`desc_event`=?,`img_event`=?,`date_event`=?,`lieu_event`=? WHERE id_event = "+id_e;
            
            prep= myDB.getConnexion().prepareStatement(req);
            
            prep.setString(1, e.getNom_event());
            prep.setString(2, e.getType_event());
            prep.setString(3, e.getDesc_event());
            prep.setString(4, e.getImg_event());
            prep.setDate(5, e.getDate_event());
            prep.setString(6, e.getLieu_event());
            prep.executeUpdate();
            System.out.println("Modification OK!");
         } catch (SQLException ex) {
            System.out.println("Problème de Modification");
        }
      }
    
}
