/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import connexionDatabase.MyDB;
import entite.Reaction;
import entite.Reaction.ReactionType;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author USER
 */
public class ReactionService {

    private static ReactionService instance;
    private MyDB myDB;

    private ReactionService() {
        myDB = MyDB.getInstance();
    }

    public static ReactionService getInstance() {
        if (instance == null) {
            instance = new ReactionService();
        }
        return instance;
    }

    public void ajouterReaction(Reaction R) throws SQLException {
        String req = "INSERT INTO `reaction`(`id_user`, `id_exp`, `type`) VALUES (?,?,?)";
        PreparedStatement pst = myDB.getConnexion().prepareStatement(req);
        pst.setInt(1, R.getId_user());
        pst.setInt(2, R.getId_exp());
        pst.setInt(3, R.getType().ordinal());
        pst.executeUpdate();
    }
    public List<Reaction> selectReactions(int id_exp) throws SQLException {
        String req = "SELECT * FROM `reaction` WHERE id_exp="+id_exp;
        ResultSet rs = myDB.getConnexion().createStatement().executeQuery(req);
        List<Reaction> l = new ArrayList<>();
        while (rs.next()){
            Reaction r = new Reaction();
            r.setId(rs.getInt("id"));
            r.setId_exp(rs.getInt("id_exp"));
            r.setId_user(rs.getInt("id_user"));
            r.setType(ReactionType.values()[rs.getInt("type")]);
            l.add(r);
        }
        return l;
    }
     public void supprimerReaction(int id) throws SQLException {
        String req = "DELETE FROM `reaction` WHERE id_user="+id;
        myDB.getConnexion().createStatement().executeUpdate(req);
        
     }
}
