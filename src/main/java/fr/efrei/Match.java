package fr.efrei;

import java.util.Random;

public class Match {
    private int id;
    private Joueur joueur1;
    private Joueur joueur2;
    private int scoreJoueur1;
    private int scoreJoueur2;
    private Jeu jeu;
    private static final Random RNG = new Random();

    public Match(int id, Joueur j1, Joueur j2, Jeu jeu) {
        this.id = id;
        this.joueur1 = j1;
        this.joueur2 = j2;
        this.jeu = jeu;
    }

    public void jouerMatch() {
        scoreJoueur1 = RNG.nextInt(11);
        scoreJoueur2 = RNG.nextInt(11);
        if (scoreJoueur1 > scoreJoueur2) {
            joueur1.ajouterScore(3);
        } else if (scoreJoueur2 > scoreJoueur1) {
            joueur2.ajouterScore(3);
        } else {
            joueur1.ajouterScore(1);
            joueur2.ajouterScore(1);
        }
    }

    public Joueur getVainqueur() {
        if (scoreJoueur1 > scoreJoueur2) return joueur1;
        if (scoreJoueur2 > scoreJoueur1) return joueur2;
        return null;
    }

    @Override
    public String toString() {
        return "Match " + id + ": " + joueur1.getPseudo() + " (" + scoreJoueur1 + ") vs " + joueur2.getPseudo() + " (" + scoreJoueur2 + ") sur " + jeu.getNom();
    }
}
