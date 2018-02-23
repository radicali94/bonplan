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
public class Position {
    private int id_pos;
    private String nom_pos;
    private Double longitude;
    private Double latitude;

    public int getId_pos() {
        return id_pos;
    }

    public void setId_pos(int id_pos) {
        this.id_pos = id_pos;
    }

    public String getNom_pos() {
        return nom_pos;
    }

    public void setNom_pos(String nom_pos) {
        this.nom_pos = nom_pos;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Position(int id_pos, String nom_pos, Double longitude, Double latitude) {
        this.id_pos = id_pos;
        this.nom_pos = nom_pos;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Position() {
    }

    @Override
    public String toString() {
        return "position{" + "id_pos=" + id_pos + ", nom_pos=" + nom_pos + ", longitude=" + longitude + ", latitude=" + latitude + '}';
    }
    
    
    
}
