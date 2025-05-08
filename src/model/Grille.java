package model;

import java.io.Serializable;

public class Grille implements Serializable {
    private final int taille;
    private Case[][] cases;

    public Grille(int taille) {
        this.taille = taille;
        initialiserGrille();
    }

    private void initialiserGrille() {
        cases = new Case[taille][taille];
        
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                cases[i][j] = new Case();
            }
        }
        
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if (i > 0) cases[i][j].connecterVoisin(Direction.N, cases[i-1][j]);
                if (i < taille-1) cases[i][j].connecterVoisin(Direction.S, cases[i+1][j]);
                if (j > 0) cases[i][j].connecterVoisin(Direction.O, cases[i][j-1]);
                if (j < taille-1) cases[i][j].connecterVoisin(Direction.E, cases[i][j+1]);
                
                if (i > 0 && j > 0) cases[i][j].connecterVoisin(Direction.NO, cases[i-1][j-1]);
                if (i > 0 && j < taille-1) cases[i][j].connecterVoisin(Direction.NE, cases[i-1][j+1]);
                if (i < taille-1 && j > 0) cases[i][j].connecterVoisin(Direction.SO, cases[i+1][j-1]);
                if (i < taille-1 && j < taille-1) cases[i][j].connecterVoisin(Direction.SE, cases[i+1][j+1]);
            }
        }
    }

    public boolean aDesJetons() {
        for (int i = 0; i < taille; i++) {
            for (int j = 0; j < taille; j++) {
                if (!cases[i][j].estVide()) {
                    return true;
                }
            }
        }
        return false;
    }
    
    

    public Case getCase(int ligne, int colonne) {
        return cases[ligne][colonne];
    }

    public int getTaille() {
        return taille;
    }
}