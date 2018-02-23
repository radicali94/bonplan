/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import connexionDatabase.MyDB;
import entite.Position;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Radhi
 */
public class PositionService implements iService.iPosition{
    
    MyDB myDB;
    public PositionService(Position pos)
    {
        myDB = MyDB.getInstance();
    }

    @Override
    public void ajouterPosition(Position pos) {
        try 
        {
        Statement stm = myDB.getConnexion().createStatement();
        String query = "insert into position (id_pos,nom_pos,longitude,latitude) values ("+pos.getId_pos()+",'"+pos.getNom_pos()+"',"+pos.getLongitude()+","+pos.getLatitude()+")";//varchar 'var'
        stm.executeUpdate(query);
            System.out.println("Ajout OK!");
        }
        catch(SQLException ex)
        {
            System.out.println("probleme d'ajout!");
        }
    }

    @Override
    public Position selectPosition(int id_pos) throws SQLException {
        Position pos = new Position();
        
        Statement stm = myDB.getConnexion().createStatement();
        ResultSet rest=stm.executeQuery("select * from position where id_pos ="+id_pos+"");
            
            while(rest.next())
            {
                pos.setId_pos(rest.getInt(1));
                pos.setNom_pos(rest.getString(2));
                pos.setLongitude(rest.getDouble(3));
                pos.setLatitude(rest.getDouble(4));
            }
            pos.toString();
            return pos;
    }

    @Override
    public void modifierPosition(Position pos, int id_pos) {
        try {
            PreparedStatement prep;
            String req;
            req = "UPDATE position SET `nom_pos`=?,`longitude`=? ,`latitude`=? WHERE id_pos = "+id_pos;
            
            prep= myDB.getConnexion().prepareStatement(req);
            
            prep.setString(1, pos.getNom_pos());
            prep.setDouble(2, pos.getLongitude());
            prep.setDouble(3, pos.getLatitude());
            prep.executeUpdate();
            System.out.println("Modification OK!");
         } catch (SQLException ex) {
            System.out.println("Problème de Modification");
        }
    }

    @Override
    public Position selectPosition(String nom_pos) throws SQLException {
         Position pos = new Position();
        
        Statement stm = myDB.getConnexion().createStatement();
        ResultSet rest=stm.executeQuery("select * from position where nom_pos ='"+nom_pos+"'");
            
            while(rest.next())
            {
                pos.setId_pos(rest.getInt(1));
                pos.setNom_pos(rest.getString(2));
                pos.setLongitude(rest.getDouble(3));
                pos.setLatitude(rest.getDouble(4));
            }
            pos.toString();
            return pos;
    }
    
}
