package model;

import java.io.Serializable;

public class Joueur implements Serializable {
    private final String nom;
    private final String couleur;
    private int jetonsRestants;

    public Joueur(String nom, String couleur, int jetonsDepart) {
        this.nom = nom;
        this.couleur = couleur;
        this.jetonsRestants = jetonsDepart;
    }

    public void utiliserJeton() {
        jetonsRestants--;
    }

    public int getJetonsRestants() {
        return jetonsRestants;
    }

    public String getCouleur() {
        return this.couleur;
    }

    public String getNom() {
        return this.nom;
    }
}