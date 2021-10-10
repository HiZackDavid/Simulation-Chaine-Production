package model;

import java.awt.Point;
import java.util.ArrayList;

public abstract class UsineProduction extends Usine {
    private int intervalleProduction;
    private ArrayList<Composant> composants;

    public UsineProduction(int id, TypeUsine type, Icone icone, Point position, ArrayList<Chemin> chemins, ArrayList<Entree> entrees, ArrayList<Sortie> sorties, int intervalleProduction) {
        super(id, type, icone, position, chemins, entrees, sorties);
        this.intervalleProduction = intervalleProduction;
        this.composants = new ArrayList<>();
    }

    public void addComponent(Composant composant) {
        this.composants.add(composant);
    }
}
