package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.file.Path;

// Gomoku class represents the game logic and state
public class Gomoku implements Serializable {
    private Grille grille;
    private Joueur[] joueurs;
    private int joueurActuel;
    private final int alignementGagnant;

    // Constructor to initialize the game with specified parameters
    public Gomoku(int tailleGrille, int jetonsDepart, int alignementGagnant, Joueur j1, Joueur j2) {
        this.grille = new Grille(tailleGrille);
        this.alignementGagnant = alignementGagnant;
        this.joueurs = new Joueur[]{j1, j2};
        this.joueurActuel = 0;
    }

    // Method to place a token on the board
    public boolean placerJeton(int ligne, int colonne, Joueur joueur) throws CoupInvalideException {
        Case c = grille.getCase(ligne, colonne);
        if (!c.estVide()) throw new CoupInvalideException("Case occupée !");
        
        if (grille.aDesJetons()) {
            if (!estAdjacenteOccupee(ligne, colonne)) {
                throw new CoupInvalideException("Case non adjacente !");
            }
        }
    
        c.placerJeton(joueur);
        joueur.utiliserJeton();
        return verifierVictoire(ligne, colonne);
    }
    
    // Check if there is an adjacent occupied cell
    private boolean estAdjacenteOccupee(int ligne, int colonne) {
        for (Direction dir : Direction.values()) {
            Case voisin = grille.getCase(ligne, colonne).getVoisin(dir);
            if (voisin != null && !voisin.estVide()) return true;
        }
        return false;
    }

    // Get the current game grid
    public Grille getGrille() {
        return this.grille;
    }

    // Get the current player index
    public int getJoueurActuel() {
        return this.joueurActuel;
    }
    
    // Switch to the next player
    public void changerJoueur() {
        this.joueurActuel = (this.joueurActuel + 1) % 2;
    }

    // Verify if the last move resulted in a victory
    private boolean verifierVictoire(int ligne, int colonne) {
        return grille.getCase(ligne, colonne).estDansAlignement(alignementGagnant);
    }
    
    // Count alignment in a specific direction
    private int compterAlignement(int ligne, int colonne, Direction dir, Joueur j) {
        int count = 0;
        Case current = grille.getCase(ligne, colonne).getVoisin(dir);
        while (current != null && current.getJoueur() == j) {
            count++;
            current = current.getVoisin(dir);
        }
        return count;
    }

    // Save the game state to a file
    public void save(Path path, Joueur joueur1, Joueur joueur2) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(path.toFile()))) {
            // Sauvegarde des paramètres de base
            writer.println(this.grille.getTaille());
            writer.println(this.alignementGagnant);
            
            // Sauvegarde des joueurs (passés en paramètres)
            writer.println(joueur1.getNom() + "," + joueur1.getCouleur() + "," + joueur1.getJetonsRestants());
            writer.println(joueur2.getNom() + "," + joueur2.getCouleur() + "," + joueur2.getJetonsRestants());
            
            // Sauvegarde de la grille
            for (int i = 0; i < grille.getTaille(); i++) {
                for (int j = 0; j < grille.getTaille(); j++) {
                    Case c = grille.getCase(i, j);
                    writer.print(c.getJoueur() != null ? c.getJoueur().getCouleur().charAt(0) : ".");
                }
                writer.println();
            }
        }
    }

    // Load the game state from a file
    public static Gomoku load(Path path, Joueur[] joueurs) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(path.toFile()))) {
        int taille = Integer.parseInt(reader.readLine());
        int alignement = Integer.parseInt(reader.readLine());
        
        // Lecture des joueurs (ignorée car passés en paramètre)
        reader.readLine();
        reader.readLine();
        
        Gomoku jeu = new Gomoku(taille, 60, alignement, null, null); // 60 est une valeur par défaut
        
        // Lecture de la grille
        for (int i = 0; i < taille; i++) {
            String ligne = reader.readLine();
            for (int j = 0; j < taille; j++) {
                char c = ligne.charAt(j);
                if (c != '.') {
                    jeu.getGrille().getCase(i, j).placerJeton(c == 'R' ? joueurs[0] : joueurs[1]);
                }
            }
        }
        return jeu;
    }
}
}