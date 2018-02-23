/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iService;

import entite.User;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author Radhi
 */
public interface iUser {
    
    
    public void ajouterUser(User u);
    public void supprimerUser(User u);
    public List<User>selectUsers();
    public User selectUser(int id)throws SQLException;
    public void modifierUser(User u,int id);
    
}
