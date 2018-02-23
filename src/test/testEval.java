/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import entite.BonPlan;
import entite.Evaluation;
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
import service.EvaluationService;
import service.UserService;

/**
 *
 * @author Radhi
 */
public class testEval {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    
    public static void main(String[] args) throws IOException {
        
        
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
        
        Evaluation eval1 = new Evaluation(0, 4.0f, "très bien", 1, 0, 0, 3);
        Evaluation eval2 = new Evaluation(0, 5.0f, "excellent", 1, 0, 0, 3);
       
        
        EvaluationService evs = new EvaluationService(eval2);
            
            System.out.println("\n1.Ajouter\n2.Afficher\n3Modifier\n4.Quitter");
            //entree = System.in.read();
              int entree = sc.nextInt();
            
            switch (entree)
            {
              
              case 1:
                evs.ajouterEval(eval1);
                evs.ajouterEval(eval2);
                
                break;
              case 2:
                
                ObservableList liste = (ObservableList)  evs.selectEvals();
                System.out.println(liste.toString());
                break;
              
              case 3:
                  evs.modifierEval(eval1,1);
                break;
                  
              case 4:
                  
                  System.exit(10);
                  break;
            }
    }
}