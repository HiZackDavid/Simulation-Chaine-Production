package model;

import java.awt.Point;

public abstract class UsineProduction extends Usine {
    private int quantite;
    private int intervalleProduction;

    UsineProduction(int id, TypeUsine type, Icone icone, Point position, Chemin chemin, int quantite, int intervalleProduction) {
        super(id, type, icone, position, chemin);
        this.quantite = quantite;
        this.intervalleProduction = intervalleProduction;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getIntervalleProduction() {
        return intervalleProduction;
    }

    public void setIntervalleProduction(int intervalleProduction) {
        this.intervalleProduction = intervalleProduction;
    }
}
