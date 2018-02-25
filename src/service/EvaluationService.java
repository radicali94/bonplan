/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import connexionDatabase.MyDB;
import entite.Evaluation;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Radhi
 */
public class EvaluationService implements iService.iEval{

    MyDB myDB;
    public EvaluationService(Evaluation eval)
    {
        myDB = MyDB.getInstance();
    }
    
    @Override
    public void ajouterEval(Evaluation eval) {
        
        try {
            java.util.Date utilDate = new java.util.Date();
            java.sql.Date date1 = new java.sql.Date(utilDate.getTime());
            Statement stm = myDB.getConnexion().createStatement();
            String query = "insert into evaluation (val_eval,comment_eval,date_eval,id_bp,id_bus,id_event,id_user) values ("+eval.getVal_eval()+",'"+eval.getComment_eval()+"','"+date1+"',"+eval.getId_bp()+","+eval.getId_bus()+","+eval.getId_event()+","+eval.getId_user()+")";//varchar 'var'
            stm.executeUpdate(query);
            System.out.println("Ajout OK!");
        } catch (SQLException ex) {
            Logger.getLogger(BonPlanService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public List<Evaluation> selectEvals() {
        
        ObservableList<Evaluation> evals = FXCollections.observableArrayList();
        try
        {
        Statement stm = myDB.getConnexion().createStatement();
        ResultSet rest=stm.executeQuery("select * from evaluation");
        while(rest.next())
        {
            Evaluation eval = new Evaluation();
            
            eval.setId_eval(rest.getInt(1));
            eval.setVal_eval(rest.getFloat(2));
            eval.setComment_eval(rest.getString(3));
            eval.setDate_eval(rest.getDate(4));
            eval.setId_bp(rest.getInt(5));
            eval.setId_bus(rest.getInt(6));
            eval.setId_event(rest.getInt(7));
            eval.setId_user(rest.getInt(8));
            
            
            evals.add(eval);
        }
        }
        catch (SQLException ex)
        {
            System.out.println("Probleme affichage");
        }
        return evals;
    }

    @Override
    public List<Evaluation> selectEvalsByIDuser(int idu) {
        
        ObservableList<Evaluation> evals = FXCollections.observableArrayList();
        try
        {
        Statement stm = myDB.getConnexion().createStatement();
        ResultSet rest=stm.executeQuery("select * from evaluation where id_user ="+idu+"");
        while(rest.next())
        {
            Evaluation eval = new Evaluation();
            
            eval.setId_eval(rest.getInt(1));
            eval.setVal_eval(rest.getFloat(2));
            eval.setComment_eval(rest.getString(3));
            eval.setDate_eval(rest.getDate(4));
            eval.setId_bp(rest.getInt(5));
            eval.setId_bus(rest.getInt(6));
            eval.setId_event(rest.getInt(7));
            eval.setId_user(rest.getInt(8));
            
            
            evals.add(eval);
        }
        }
        catch (SQLException ex)
        {
            System.out.println("Probleme affichage");
        }
        return evals;
        
    }

    @Override
    public Evaluation selectEval(int id_eval) throws SQLException {
        
         Evaluation eval = new Evaluation();
        
        Statement stm = myDB.getConnexion().createStatement();
        ResultSet rest=stm.executeQuery("select * from evaluation where id_eval ="+id_eval+"");
            
            while(rest.next())
            {
                eval.setId_eval(rest.getInt(1));
                eval.setVal_eval(rest.getFloat(2));
                eval.setComment_eval(rest.getString(3));
                eval.setDate_eval(rest.getDate(4));
                eval.setId_bp(rest.getInt(5));
                eval.setId_bus(rest.getInt(6));
                eval.setId_event(rest.getInt(7));
                eval.setId_user(rest.getInt(8));
            }
            System.out.println(eval.toString());
            return eval;
        
    }

    @Override
    public void modifierEval(Evaluation eval, int id_eval) {
        
        try {
            PreparedStatement prep;
            String req;
            req = "UPDATE evaluation SET `val_eval`=?,`comment_eval`=?,`date_eval`=? WHERE id_eval = "+id_eval;
            
            prep= myDB.getConnexion().prepareStatement(req);
            
            prep.setFloat(1, eval.getVal_eval());
            prep.setString(2, eval.getComment_eval());
            prep.setDate(3, eval.getDate_eval());
            
            
            
            prep.executeUpdate();
            System.out.println("Modification OK!");
         } catch (SQLException ex) {
            System.out.println("Problème de Modification");
        }
        
    }

    @Override
    public List<Evaluation> selectEvalsByIDbp(int idbp) {
        
        ObservableList<Evaluation> evals = FXCollections.observableArrayList();
        try
        {
        Statement stm = myDB.getConnexion().createStatement();
        ResultSet rest=stm.executeQuery("select * from evaluation where id_bp ="+idbp+"");
            while(rest.next())
            {
                Evaluation eval = new Evaluation();

                eval.setId_eval(rest.getInt(1));
                eval.setVal_eval(rest.getFloat(2));
                eval.setComment_eval(rest.getString(3));
                eval.setDate_eval(rest.getDate(4));
                eval.setId_bp(rest.getInt(5));
                eval.setId_bus(rest.getInt(6));
                eval.setId_event(rest.getInt(7));
                eval.setId_user(rest.getInt(8));


                evals.add(eval);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Probleme affichage");
        }
        return evals;
        
    }

    @Override
    public List<Evaluation> selectEvalsByIDbus(int idbus) {
        
        ObservableList<Evaluation> evals = FXCollections.observableArrayList();
        try
        {
        Statement stm = myDB.getConnexion().createStatement();
        ResultSet rest=stm.executeQuery("select * from evaluation where id_bus ="+idbus+"");
            while(rest.next())
            {
                Evaluation eval = new Evaluation();

                eval.setId_eval(rest.getInt(1));
                eval.setVal_eval(rest.getFloat(2));
                eval.setComment_eval(rest.getString(3));
                eval.setDate_eval(rest.getDate(4));
                eval.setId_bp(rest.getInt(5));
                eval.setId_bus(rest.getInt(6));
                eval.setId_event(rest.getInt(7));
                eval.setId_user(rest.getInt(8));


                evals.add(eval);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Probleme affichage");
        }
        return evals;
        
    }

    @Override
    public List<Evaluation> selectEvalsByIDevent(int idevent) {
        
        ObservableList<Evaluation> evals = FXCollections.observableArrayList();
        try
        {
        Statement stm = myDB.getConnexion().createStatement();
        ResultSet rest=stm.executeQuery("select * from evaluation where id_event ="+idevent+"");
            while(rest.next())
            {
                Evaluation eval = new Evaluation();

                eval.setId_eval(rest.getInt(1));
                eval.setVal_eval(rest.getFloat(2));
                eval.setComment_eval(rest.getString(3));
                eval.setDate_eval(rest.getDate(4));
                eval.setId_bp(rest.getInt(5));
                eval.setId_bus(rest.getInt(6));
                eval.setId_event(rest.getInt(7));
                eval.setId_user(rest.getInt(8));


                evals.add(eval);
            }
        }
        catch (SQLException ex)
        {
            System.out.println("Probleme affichage");
        }
        return evals;
        
    }

    @Override
    public Evaluation selectEvalByIDuserBP(int id_user, int idbp) throws SQLException {
        Evaluation eval = new Evaluation();
        
        Statement stm = myDB.getConnexion().createStatement();
        ResultSet rest=stm.executeQuery("select * from evaluation where (id_user = "+id_user+" AND id_bp = "+idbp+" )");
            
            while(rest.next())
            {
                eval.setId_eval(rest.getInt(1));
                eval.setVal_eval(rest.getFloat(2));
                eval.setComment_eval(rest.getString(3));
                eval.setDate_eval(rest.getDate(4));
                eval.setId_bp(rest.getInt(5));
                eval.setId_bus(rest.getInt(6));
                eval.setId_event(rest.getInt(7));
                eval.setId_user(rest.getInt(8));
            }
            System.out.println(eval.toString());
            return eval;
    }
    
}
