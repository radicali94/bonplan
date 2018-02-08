/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connexionDatabase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Radhi
 */
public class MyDB {

     static MyDB instance;
     String url = "jdbc:mysql://localhost:3306/pi";
     String login = "root";
     String pwd = "";
     
     Connection con;
     
     @SuppressWarnings("empty-statement")
     private MyDB()
     {

         try
         {
             con = DriverManager.getConnection(url, login, pwd);
             System.out.println("Connexion OK!");
         }
         catch(SQLException ex)
                 {
                     System.out.println("Pas de Connexion!");;
                 }
     }


    public Connection getConnexion() {
        return con ; 
    }

    public static connexionDatabase.MyDB getInstance() {
        if(instance==null)
        {
            instance = new MyDB();
        }
        return instance;
    }
}
