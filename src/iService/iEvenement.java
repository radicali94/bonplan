/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iService;

import entite.Evenement;
import java.util.List;

/**
 *
 * @author Radhi
 */
public interface iEvenement {
    
    
    public void ajouterEvenement(Evenement e);
    public void supprimerEvenement(Evenement e);
    public List<Evenement>selectEvenements();
    public void modifierEvenement(Evenement e,int id_e);
    
}
