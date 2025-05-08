package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Path;

// Gomoku class represents the game logic and state
public class Gomoku implements Serializable {
    private Grille grille;
    private Joueur[] joueurs;
    private int joueurActuel;
    private final int alignementGagnant;

    // Constructor to initialize the game with specified parameters
    public Gomoku(int tailleGrille, int jetonsDepart, int alignementGagnant) {
        this.grille = new Grille(tailleGrille);
        this.alignementGagnant = alignementGagnant;
        this.joueurs = new Joueur[2];
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
    public void save(Path path) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(path.toFile()))) {
            oos.writeObject(this);
        }
    }

    // Load the game state from a file
    public static Gomoku load(Path path) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(path.toFile()))) {
            return (Gomoku) ois.readObject();
        }
    }
}