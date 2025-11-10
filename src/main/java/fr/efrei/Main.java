package fr.efrei;

public class Main {
    public static void main(String[] args) {
        Jeu jeu = new Jeu(1, "Rocket Arena", "Action");
        Organisateur org = new Organisateur("Alice", "alice@example.com");

        Tournoi tournoi = org.creerTournoi("Championnat 2025", jeu);

        tournoi.inscrireJoueur(new Joueur(1, "PlayerOne", "p1@example.com"));
        tournoi.inscrireJoueur(new Joueur(2, "GamerX", "gx@example.com"));
        tournoi.inscrireJoueur(new Joueur(3, "NoobMaster", "nm@example.com"));
        tournoi.inscrireJoueur(new Joueur(4, "ProGamer", "pg@example.com"));
        tournoi.inscrireJoueur(new Joueur(5, "LuckyPlayer", "lp@example.com"));

        System.out.println("Début du " + tournoi);
        org.gererTournoi(tournoi);

        System.out.println("Matches joués :");
        for (Match m : tournoi.getListeMatchs()) {
            System.out.println(m);
        }

        Joueur vainqueur = tournoi.getVainqueur();
        if (vainqueur != null) {
            System.out.println("Vainqueur du tournoi : " + vainqueur);
        } else {
            System.out.println("Aucun vainqueur déterminé.");
        }
    }
}
