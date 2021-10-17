package model;

import controller.Observateur;
import controller.Sujet;

import java.awt.*;
import java.util.ArrayList;

public class Entrepot extends Usine implements Sujet {
    private ArrayList<Observateur> observateurs;

    public Entrepot(int id, TypeUsine type, Icone icone, Point position, ArrayList<Chemin> chemins, ArrayList<Entree> entrees, ArrayList<Sortie> sorties) {
        super(id, type, icone, position, chemins, entrees, sorties);
        this.observateurs = new ArrayList<>();
    }

    public void incrementerQuantite() {
        for (Entree entree : getEntrees()) {
            if (entree.hasReachedRequiredAmount()) {
                notifierObservateurs();
            }
        }
    }

    @Override
    public void ajouterObservateur(Observateur observateur) {
        this.observateurs.add(observateur);
    }

    @Override
    public void retirerObservateur(Observateur observateur) {
        this.observateurs.remove(observateur);
    }

    @Override
    public void notifierObservateurs() {
        for (Observateur observateur : this.observateurs) {
            observateur.update();
        }
    }
}
