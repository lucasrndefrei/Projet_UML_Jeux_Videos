package fr.efrei;

public class Organisateur {
    private int id;
    private String nom;
    private String email;
    private static int idCounter = 1;

    public Organisateur(String nom, String email) {
        this.id = idCounter++;
        this.nom = nom;
        this.email = email;
    }

    public Tournoi creerTournoi(String nomTournoi, Jeu jeu) {
        return new Tournoi(nomTournoi, jeu);
    }

    public void gererTournoi(Tournoi t) {
        if (t != null) {
            t.demarrerTournoi();
        }
    }

    @Override
    public String toString() {
        return nom + " (organisateur)";
    }
}

