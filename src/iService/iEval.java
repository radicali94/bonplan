/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iService;

import entite.Evaluation;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Radhi
 */
public interface iEval {
    
    public void ajouterEval(Evaluation eval);
    public List<Evaluation> selectEvals();
    public List<Evaluation> selectEvalsByIDuser(int idu);
    public List<Evaluation> selectEvalsByIDbp(int idbp);
    public List<Evaluation> selectEvalsByIDbus(int idbus);
    public List<Evaluation> selectEvalsByIDevent(int idevent);
    public Evaluation selectEval(int id_eval)throws SQLException;
    public Evaluation selectEvalByIDuserBP(int id_user,int idbp)throws SQLException;
    public void modifierEval(Evaluation eval,int id_eval);
    public Evaluation selectEvalByIDuserE(int id_user, int ide) throws SQLException;
    public List<Evaluation> selectEvalsByIDE(int ide);
}
