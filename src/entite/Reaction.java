/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;

/**
 *
 * @author USER
 */
public class Reaction {

    public enum ReactionType {
        like,
        dislike
    }
    private int id;
    private int id_user;
    private int id_exp;
    private ReactionType type;

    public Reaction(int id, int id_user, int id_exp, ReactionType type) {
        this.id = id;
        this.id_user = id_user;
        this.id_exp = id_exp;
        this.type = type;
    }

    public Reaction() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public int getId_exp() {
        return id_exp;
    }

    public void setId_exp(int id_exp) {
        this.id_exp = id_exp;
    }

    public ReactionType getType() {
        return type;
    }

    public void setType(ReactionType type) {
        this.type = type;
    }
    

}
