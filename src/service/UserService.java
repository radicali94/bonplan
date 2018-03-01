/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import connexionDatabase.MyDB;
import entite.BCrypt;
import entite.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
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
public class UserService implements iService.iUser
{

    MyDB myDB;
    public UserService(User u)
    {
        myDB = MyDB.getInstance();
    }
    
    
    @Override
    public void ajouterUser(User u) {
        try 
        {
        int etat = 1;
            if(u.getType().equalsIgnoreCase("Admin")){etat=0; }
        Statement stm = myDB.getConnexion().createStatement();
        String query = "insert into user (username,username_canonical,email,email_canonical,password,roles,nom,prenom,tel,photo,type,etat) "
                + "values ('"+u.getUsername()+"','"+u.getUsername()+"','"+u.getEmail()+"','"+u.getEmail()+"','"+u.getPassword()+"','"+u.getRoles()+"','"+u.getNom()+"','"+u.getPrenom()+"','"+u.getTel()+"','"+u.getPhoto()+"','"+u.getType()+"','"+etat+"')";//varchar 'var'
        stm.executeUpdate(query);
            System.out.println("Ajout OK!");
        }
        catch(SQLException ex)
        {
            System.out.println("probleme d'ajout!");
        }
    }

    @Override   
    public void supprimerUser(int id) 
    {
        PreparedStatement prep;
        try 
        {
            prep = myDB.getConnexion().prepareStatement("delete from user where id = ?");
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
    public ObservableList<User> selectUsers() {
         ObservableList<User> users = FXCollections.observableArrayList();
        try {
           
            
            Statement stm = myDB.getConnexion().createStatement();
            ResultSet rest=stm.executeQuery("select * from user");
            while(rest.next())
            {
                User u = new User();
                
                u.setId(rest.getInt(1));
                u.setUsername(rest.getString(2));
                u.setEmail(rest.getString(4));
                u.setPassword(rest.getString(8));
                u.setNom(rest.getString(13));
                u.setPrenom(rest.getString(14));
                u.setTel(rest.getString(15));
                u.setPhoto(rest.getString(16));
                u.setType(rest.getString(17));
                
                
                users.add(u);
            }
            
            
        } catch (SQLException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return users;
    }
    
    @Override
    public void modifierUser(User u,int id){
        
        
        try {
            PreparedStatement prep;
            String req;
            req = "UPDATE user SET `username`=?,`username_canonical`=?,`email`=?,`email_canonical`=?,`enabled`=?,`password`=?,`roles`=?,`nom`=?,`prenom`=?,`tel`=?,`photo`=?,`type`=? WHERE id = "+id;
            //username,username_canonical,email,email_canonical,
            //enabled,password,roles,nom,prenom,tel,photo,type
            prep= myDB.getConnexion().prepareStatement(req);
            
            prep.setString(1, u.getUsername());
            prep.setString(2, u.getUsername());
            prep.setString(3, u.getEmail());
            prep.setString(4, u.getEmail());
            prep.setInt(5, 1);
            prep.setString(6, u.getPassword());
            prep.setString(7, u.getRoles());
            prep.setString(8, u.getNom());
            prep.setString(9, u.getPrenom());
            prep.setString(10, u.getTel());
            prep.setString(11, u.getPhoto());
            prep.setString(12, u.getType());
            prep.executeUpdate();
            System.out.println("Modification OK!");
         } catch (SQLException ex) {
            System.out.println("Problème de Modification");
        }
      }

    @Override
    public User selectUser(int id) throws SQLException {
        User u = new User();
        
        Statement stm = myDB.getConnexion().createStatement();
        ResultSet rest=stm.executeQuery("select * from user where id ="+id+"");
            
            while(rest.next())
            {
                u.setId(rest.getInt(1));
                u.setUsername(rest.getString(2));
                u.setEmail(rest.getString(4));
                u.setPassword(rest.getString(8));
                u.setNom(rest.getString(13));
                u.setPrenom(rest.getString(14));
                u.setTel(rest.getString(15));
                u.setPhoto(rest.getString(16));
                u.setType(rest.getString(17));
            }
            System.out.println(u.toString());
            return u;
    }
    
    public void acceptAdmin(int id){
        
        
        try {
            PreparedStatement prep;
            String req;
            req = "UPDATE user SET `etat`=1 WHERE id = "+id;
            
            prep= myDB.getConnexion().prepareStatement(req);
            
            
           
            prep.executeUpdate();
            System.out.println("Accepter OK!");
         } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
      }
    
    public ObservableList<User> selectUsersAdmin() {
        ObservableList<User> users = FXCollections.observableArrayList();
        try
        {
        Statement stm = myDB.getConnexion().createStatement();
        ResultSet rest=stm.executeQuery("select * from user WHERE etat=0");
        while(rest.next())
        {
            User u = new User();
            
            u.setId(rest.getInt(1));
            u.setUsername(rest.getString(2));
            u.setEmail(rest.getString(4));
            u.setPassword(rest.getString(8));
            u.setNom(rest.getString(13));
            u.setPrenom(rest.getString(14));
            u.setTel(rest.getString(15));
            u.setPhoto(rest.getString(16));
            u.setType(rest.getString(17));
            
            
            users.add(u);
        }
        }
        catch (SQLException ex)
        {
            System.out.println("Probleme affichage");
        }
        return users;
    }
    
     public void bannerUser(int id){
        
        
        try {
            PreparedStatement prep;
            String req;
            req = "UPDATE user SET `enabled`=0 WHERE id = "+id;
            
            prep= myDB.getConnexion().prepareStatement(req);
            
            
           
            prep.executeUpdate();
            System.out.println("Accepter OK!");
         } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
      }
     
     public void desactiverUser(int id){
        
        
        try {
            PreparedStatement prep;
            String req;
            req = "UPDATE user SET `enabled`=2 WHERE id = "+id;
            
            prep= myDB.getConnexion().prepareStatement(req);
            
            
           
            prep.executeUpdate();
            System.out.println("Accepter OK!");
         } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
      }
    
    public User GetUserById (Integer id) throws ParseException, SQLException {
        
        
       PreparedStatement prep;
       Statement stm = myDB.getConnexion().createStatement();
        ResultSet rest=stm.executeQuery("select * from user where id="+id);
        User e = null;
            
            while (rest.next()) {
                e = 
                     new User(rest.getString("username"), rest.getString("email"), rest.getString("password"), rest.getString("nom"), rest.getString("prenom"), rest.getString("tel"), rest.getString("photo"), rest.getString("type"));
                e.setId(Integer.valueOf(rest.getString("id")));
                return e;
            }
       
         return e;
      
    }
    
    public void edit(User e) throws SQLException {
        PreparedStatement prep;
       Statement stm = myDB.getConnexion().createStatement();
       
        String req="UPDATE user SET nom='"+e.getNom()+"',prenom='"+e.getPrenom()+"',tel='"+e.getTel()+"',email='"+e.getEmail()+"',username='"+e.getUsername()+"'WHERE id=8";
         
        
        try {
            stm.executeUpdate(req);
           
           
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public ObservableList<User> rechercheUsers(String recherche) {
        ObservableList<User> users = FXCollections.observableArrayList();
        try
        {
        Statement stm = myDB.getConnexion().createStatement();
        ResultSet rest=stm.executeQuery("select * from user WHERE etat=1 AND (nom LIKE '%" + recherche + "%' OR prenom LIKE '%" + recherche + "%' OR tel LIKE '%" + recherche + "%')");
        while(rest.next())
        {
            User u = new User();
            
            u.setId(rest.getInt(1));
            u.setUsername(rest.getString(2));
            u.setEmail(rest.getString(4));
            u.setPassword(rest.getString(8));
            u.setNom(rest.getString(13));
            u.setPrenom(rest.getString(14));
            u.setTel(rest.getString(15));
            u.setPhoto(rest.getString(16));
            u.setType(rest.getString(17));
            
            
            users.add(u);
        }
        }
        catch (SQLException ex)
        {
            System.out.println(ex.getMessage());
        }
        return users;
    }
    
    public ObservableList<User> rechercheUsers2(String recherche) {
        ObservableList<User> users = FXCollections.observableArrayList();
        try
        {
        Statement stm = myDB.getConnexion().createStatement();
        ResultSet rest=stm.executeQuery("select * from user WHERE etat=0 AND (nom LIKE '%" + recherche + "%' OR prenom LIKE '%" + recherche + "%'OR tel LIKE '%" + recherche + "%')");
        while(rest.next())
        {
            User u = new User();
            
            u.setId(rest.getInt(1));
            u.setUsername(rest.getString(2));
            u.setEmail(rest.getString(4));
            u.setPassword(rest.getString(8));
            u.setNom(rest.getString(13));
            u.setPrenom(rest.getString(14));
            u.setTel(rest.getString(15));
            u.setPhoto(rest.getString(16));
            u.setType(rest.getString(17));
            
            
            users.add(u);
        }
        }
        catch (SQLException ex)
        {
            System.out.println("Probleme affichage");
        }
        return users;
    }
    
}
