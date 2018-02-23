/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import entite.BonPlan;
import entite.Position;
import entite.User;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import service.BonPlanService;
import java.util.Scanner;
import javafx.collections.ObservableList;
import service.PositionService;
import service.UserService;

/**
 *
 * @author Radhi
 */
public class testPosition {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    
    public static void main(String[] args) throws IOException, SQLException {
        
        
        try 
        {
            // TODO code application logic here
            String url = "jdbc:mysql://localhost:3306/pi";
            String login = "root";
            String pwd = "";
            
            
            Connection con = DriverManager.getConnection(url, login, pwd);
            System.out.println("connexion OK!");
        } 
        catch (SQLException ex) 
        {
            System.out.println("pas de connexion"+ex.getSQLState());
        }
        
        Scanner sc = new Scanner(System.in);
        
        Position pos1 = new Position(1,"nom_pos1", 11.111, 11.111);
        Position pos2 = new Position(2,"nom_pos2", 22.222, 22.222);
       
       
        
        PositionService ps = new PositionService(pos1);
        
        
        
            
            
            System.out.println("\n1.Ajouter\n2.Afficher\n3.Modifier\n4.Quitter");
            //entree = System.in.read();
              int entree = sc.nextInt();
            
            switch (entree)
            {
              
              case 1:
                ps.ajouterPosition(pos1);
                ps.ajouterPosition(pos2);
                
                break;
                
              case 2:
                System.out.println(ps.selectPosition(1).toString());
                System.out.println(ps.selectPosition(2).toString());
                
                break;
              
              case 3:
                  ps.modifierPosition(pos1, 1);
                  
                  break;
                  
              case 4:
                  
                  System.exit(0);
                  
                  break;
               
            }
        
        
     
        
        //username,username_canonical,email,email_canonical,
            //enabled,password,roles,nom,prenom,tel,photo,type
            
            
        /*Scanner sc = new Scanner(System.in);
        
        User u1 = new User(1, "username1", "email1",1, "password1", "roles1", "nom1", "prenom1", "tel1", "photo1", "type1");
        User u2 = new User(2, "username2", "email2",1, "password2", "roles2", "nom2", "prenom2", "tel2", "photo2", "type2");
       
       
        
        UserService userService = new UserService(u1);
        
        
    
            
            
            System.out.println("\n1.Ajouter\n2.Afficher\n3.Supp\n4.Modifier\n5.Quitter");
            //entree = System.in.read();
              int entree = sc.nextInt();
            
            switch (entree)
            {
              
              case 1:
                userService.ajouterUser(u1);
                userService.ajouterUser(u2);
                
                break;
              case 2:
                List liste;
                liste = (ArrayList) userService.selectUsers();
                System.out.println(liste.toString());
                break;
              
              case 3:
                  userService.supprimerUser(u1);
                  userService.supprimerUser(u2);
                break;
                  
              case 4:
                  userService.modifierUser(u2,3);
                  
                  
                  break;
                  
              case 5:
                  System.exit(10);
                  break;
            }    
            */
    }
    
}
