/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iService;

import entite.BonPlan;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Radhi
 */
public interface iBonPlan {
    
    
    public void ajouterBonPlan(BonPlan bp);
    public void supprimerBonPlan(BonPlan bp);
    public List<BonPlan> selectBonPlans();
    public List<BonPlan> selectBonPlansByID(int idu);
    public BonPlan selectBonPlan(int id_bp)throws SQLException;
    public void modifierBonPlan(BonPlan bp,int id_bp);
    
}
