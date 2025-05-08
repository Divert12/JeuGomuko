package view;

import model.Grille;
import model.Joueur;

public class Affichage {
    public static void afficherGrille(Grille grille) {
        int taille = grille.getTaille();
        
        // En-tête des colonnes
        System.out.print("   ");  // Espace pour l'angle
        for (int j = 0; j < taille; j++) {
            System.out.printf("%2d ", j);  // Numérotation des colonnes (1-based)
        }
        System.out.println();
        
        // Ligne séparatrice
        System.out.print("  +");
        for (int j = 0; j < taille; j++) {
            System.out.print("---");
        }
        System.out.println("+");
        
        // Contenu de la grille avec numérotation des lignes
        for (int i = 0; i < taille; i++) {
            System.out.printf("%2d|", i);  // Numéro de ligne (1-based)
            
            for (int j = 0; j < taille; j++) {
                Joueur jr = grille.getCase(i, j).getJoueur();
                String symbole = (jr == null) ? " . " : 
                    (jr.getCouleur().equals("Rouge") ? " \u001B[31m●\u001B[0m " : " \u001B[34m●\u001B[0m ");
                System.out.print(symbole);
            }
            System.out.println("|");
        }
        
        // Ligne séparatrice basse
        System.out.print("  +");
        for (int j = 0; j < taille; j++) {
            System.out.print("---");
        }
        System.out.println("+");
    }
}