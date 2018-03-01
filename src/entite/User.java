/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entite;

/**
 *
 * @author Radhi
 */
public class User {
    private int id;
    private String username;
    private String email;
    private int enabled;
    private String password;
    private String roles;
    private String nom;
    private String prenom;
    private String tel;
    private String photo;
    private String type;
    private int etat;

    public User(String username, String email, String password, String nom, String prenom, String tel, String photo, String type) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.photo = photo;
        this.type = type;
    }

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }
    
    
    
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEnabled() {
        return enabled;
    }

    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return "a:1:{i:0;s:16:\"ROLE_SUPER_ADMIN\";}";
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public User( String username, String email, int enabled, String password, String roles, String nom, String prenom, String tel, String photo, String type) {
      
        this.username = username;
        this.email = email;
        this.enabled = enabled;
        this.password = password;
        this.roles = roles;
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.photo = photo;
        this.type = type;
    }
    
    public User()
    {
        
    }
    
    
    @Override
    public String toString()
    {
        return "\nBUser:\nID: "+id+"\nUsername: "+username+"\nE-mail: "+email+"\nPassword: "+password+"\nNom: "+nom
                +"\nPrenom: "+prenom+"\nTel: "+tel+"\nPhoto: "+photo+"\nType: "+type+"\n";
    }
    
}
