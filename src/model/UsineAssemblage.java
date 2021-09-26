package model;

import java.awt.Point;
import java.util.ArrayList;

public class UsineAssemblage extends UsineProduction {
    public UsineAssemblage(int id, TypeUsine type, Icone icone, Point position, ArrayList<Chemin> chemins, int intervalleProduction) {
        super(id, type, icone, position, chemins, intervalleProduction);
    }
}
