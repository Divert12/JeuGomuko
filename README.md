# Gomoku - Jeu de plateau

## Description
Implémentation du jeu Gomoku en Java avec :
- Grille 15x15 extensible par défaut
- Sauvegarde/chargement des parties
- Interface en ligne de commande
- Système de tours et vérification des victoires

## Fonctionnalités
- [x] Jeu à 2 joueurs
- [x] Numérotation des lignes/colonnes
- [x] Vérification des alignements
- [x] Sauvegarde texte des parties
- [x] Paramétrage des règles

## Installation
```bash
git clone https://github.com/votre-utilisateur/gomoku.git
cd gomoku

# Compilation
javac -d compiled/ src/*/*.java src/Main.java

# Exécution avec paramètres par défaut (15x15, 60 jetons, alignement de 5)
java -cp compiled/ Main

# Avec paramètres personnalisés
java -cp compiled/ Main [taille] [jetons] [alignement]
