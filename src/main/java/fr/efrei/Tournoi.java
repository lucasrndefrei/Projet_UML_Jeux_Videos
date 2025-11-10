package fr.efrei;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tournoi {
    private int id;
    private String nom;
    private List<Joueur> listeJoueurs = new ArrayList<>();
    private List<Match> listeMatchs = new ArrayList<>();
    private Jeu jeu;
    private Joueur vainqueur;
    private static int idCounter = 1;

    public Tournoi(int id, String nom, Jeu jeu) {
        this.id = id;
        this.nom = nom;
        this.jeu = jeu;
    }

    public Tournoi(String nom, Jeu jeu) {
        this(idCounter++, nom, jeu);
    }

    public void inscrireJoueur(Joueur j) {
        if (j != null) {
            listeJoueurs.add(j);
        }
    }

    public void demarrerTournoi() {
        if (listeJoueurs.isEmpty()) {
            vainqueur = null;
            return;
        }
        List<Joueur> participants = new ArrayList<>(listeJoueurs);
        Collections.shuffle(participants);
        int matchIdCounter = 1;

        while (participants.size() > 1) {
            List<Joueur> nextRound = new ArrayList<>();
            for (int i = 0; i < participants.size(); i += 2) {
                if (i + 1 >= participants.size()) {
                    nextRound.add(participants.get(i));
                } else {
                    Joueur p1 = participants.get(i);
                    Joueur p2 = participants.get(i + 1);
                    Match m = new Match(matchIdCounter++, p1, p2, jeu);
                    m.jouerMatch();
                    listeMatchs.add(m);
                    Joueur win = m.getVainqueur();
                    if (win != null) nextRound.add(win);
                    else nextRound.add(Math.random() < 0.5 ? p1 : p2);
                }
            }
            participants = nextRound;
        }
        vainqueur = participants.isEmpty() ? null : participants.get(0);
    }

    public Joueur getVainqueur() {
        return vainqueur;
    }

    public List<Match> getListeMatchs() {
        return Collections.unmodifiableList(listeMatchs);
    }

    @Override
    public String toString() {
        return "Tournoi " + id + ": " + nom + " sur " + jeu.getNom();
    }
}

