package fr.efrei;

public class Joueur {
    // Attributs
    private int id;
    private String pseudo;
    private String email;
    private int score;

    // Constructeur
    public Joueur(int id, String pseudo, String email) {
        this.id = id;
        this.pseudo = pseudo;
        this.email = email;
        this.score = 0;
    }

    // Getter pour le pseudo
    public String getPseudo() {
        return pseudo;
    }

    // Getter pour le score
    public int getScore() {
        return score;
    }

    // Ajouter des points au score
    public void ajouterScore(int points) {
        if (points > 0) {
            this.score += points;
        }
    }

    @Override
    public String toString() {
        return pseudo + " (id=" + id + ", score=" + score + ")";
    }
}

