/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iService;

import entite.Position;
import java.sql.SQLException;
/**
 *
 * @author Radhi
 */
public interface iPosition {
    public void ajouterPosition(Position pos);
    public Position selectPosition(int id_pos)throws SQLException;
    public Position selectPosition(String nom_pos)throws SQLException;
    public void modifierPosition(Position pos,int id_pos);
    
}
