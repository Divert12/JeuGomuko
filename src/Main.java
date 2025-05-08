import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import model.CoupInvalideException;
import model.Gomoku;
import model.Joueur;
import view.Affichage;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // Définition des paramètres par défaut
        int taille = 15; 
        int jetonsDepart = 60; 
        int alignementVictoire = 5; 

        // Si des arguments sont passés, on les utilise
        if (args.length >= 3) {
            try {
                taille = Integer.parseInt(args[0]); 
                jetonsDepart = Integer.parseInt(args[1]); 
                alignementVictoire = Integer.parseInt(args[2]); 
            } catch (NumberFormatException e) {
                System.out.println("Erreur dans les arguments. Utilisation des valeurs par défaut.");
            }
        }

        
        System.out.println("Joueur 1 (Rouge), entrez votre nom : ");
        Joueur j1 = new Joueur(sc.nextLine(), "Rouge", jetonsDepart);

        System.out.println("Joueur 2 (Bleu), entrez votre nom : ");
        Joueur j2 = new Joueur(sc.nextLine(), "Bleu", jetonsDepart);

        Gomoku jeu = new Gomoku(taille, jetonsDepart, alignementVictoire);
        boolean finJeu = false;

        
        while (!finJeu) {
            Affichage.afficherGrille(jeu.getGrille());
            Joueur courant = jeu.getJoueurActuel() == 0 ? j1 : j2;

            int l = -1, c = -1;
            boolean coupValide = false;

            while (!coupValide) {
                System.out.println(courant.getNom() + " (" + courant.getCouleur() + "), entrez ligne et colonne : ");
                
                
                if (!sc.hasNextInt()) {
                    System.out.println("Entrée invalide ! Veuillez entrer un entier.");
                    sc.next(); 
                    continue;
                }
                l = sc.nextInt();

                if (!sc.hasNextInt()) {
                    System.out.println("Entrée invalide ! Veuillez entrer un entier.");
                    sc.next(); 
                    continue;
                }
                c = sc.nextInt();

                
                if (l < 0 || l >= taille || c < 0 || c >= taille) {
                    System.out.println("Coordonnées hors de la grille ! Réessayez.");
                    continue;
                }

                try {
                    if (jeu.placerJeton(l, c, courant)) {
                        Affichage.afficherGrille(jeu.getGrille());
                        System.out.println(courant.getNom() + " a gagné !");
                        finJeu = true;
                    }
                    coupValide = true;
                    jeu.changerJoueur();
                } catch (CoupInvalideException e) {
                    System.out.println("Coup invalide : " + e.getMessage());
                }
            }
        }

        sc.close();

            try {
                Path savePath = Paths.get("sauvegarde.gomoku");
                jeu.save(savePath);
                System.out.println("Partie sauvegardée !");
            } catch (IOException e) {
                System.err.println("Erreur de sauvegarde : " + e.getMessage());
            }
            
            // Chargement
            try {
                Path loadPath = Paths.get("sauvegarde.gomoku");
                Gomoku jeuCharge = Gomoku.load(loadPath);
                System.out.println("Partie chargée !");
                // Continuer avec jeuCharge...
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Erreur de chargement : " + e.getMessage());
            }
        }
    }

