package view;

import model.Grille;
import model.Joueur;

public class Affichage {
    public static void afficherGrille(Grille grille) {
        for (int i = 0; i < grille.getTaille(); i++) {
            for (int j = 0; j < grille.getTaille(); j++) {
                Joueur jr = grille.getCase(i, j).getJoueur();
                String symbole = (jr == null) ? "◌" : 
                    (jr.getCouleur().equals("Rouge") ? "\u001B[31m●\u001B[0m" : "\u001B[34m●\u001B[0m");
                System.out.print(symbole + " ");
            }
            System.out.println();
        }
    }
}