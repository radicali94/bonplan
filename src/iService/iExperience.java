/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iService;

import entite.Experience;
import java.util.List;
import javafx.collections.ObservableList;

/**
 *
 * @author Radhi
 */
public interface iExperience {
    
    
    public void ajouterExperience(Experience exp);
    public void supprimerExperience(int id);
    public List<Experience> selectBonPlansByID(int idu);
    public List<Experience>selectExperiences();
    public void updateexperience(Experience exp,int id_exp);
}
