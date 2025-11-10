package fr.efrei;

public class Jeu {
    private int id;
    private String nom;
    private String genre;

    public Jeu(int id, String nom, String genre) {
        this.id = id;
        this.nom = nom;
        this.genre = genre;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return nom + " [" + genre + "]";
    }
}

