package model;

import java.awt.Point;
import java.util.ArrayList;

public abstract class UsineProduction extends Usine {
    private int intervalleProduction;

    UsineProduction(int id, TypeUsine type, Icone icone, Point position, ArrayList<Chemin> chemins, int intervalleProduction) {
        super(id, type, icone, position, chemins);
        this.intervalleProduction = intervalleProduction;
    }

    public int getIntervalleProduction() {
        return intervalleProduction;
    }

    public void setIntervalleProduction(int intervalleProduction) {
        this.intervalleProduction = intervalleProduction;
    }
}
