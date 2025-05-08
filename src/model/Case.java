package model;

import java.io.Serializable;
import java.util.EnumMap;

public class Case implements Serializable {
    private Joueur joueur;
    private final EnumMap<Direction, Case> voisins = new EnumMap<>(Direction.class);
    private static final long serialVersionUID = 1L;


    public boolean estDansAlignement(int alignementGagnant) {
        if (this.joueur == null) return false;
    
        for (Direction dir : Direction.values()) {
            int count = 1; 
            count += compterAlignementDansDirection(dir);
            count += compterAlignementDansDirection(dir.oppose());
    
            if (count >= alignementGagnant) {
                return true;
            }
        }
        return false;
    }
    
    private int compterAlignementDansDirection(Direction dir) {
        int count = 0;
        Case courant = this.getVoisin(dir);
        while (courant != null && courant.getJoueur() == this.joueur) {
            count++;
            courant = courant.getVoisin(dir);
        }
        return count;
    }
    
    public void connecterVoisin(Direction dir, Case voisin) {
        this.voisins.put(dir, voisin);
        voisin.voisins.put(dir.oppose(), this);
    }

    public Case getVoisin(Direction dir) {
        return voisins.get(dir);
    }

    public boolean estVide() {
        return joueur == null;
    }

    public void placerJeton(Joueur j) {
        this.joueur = j;
    }

    public Joueur getJoueur() {
        return joueur;
    }
}