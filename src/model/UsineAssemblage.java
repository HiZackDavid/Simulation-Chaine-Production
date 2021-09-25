package model;

import java.awt.Point;

public class UsineAssemblage extends UsineProduction {
    UsineAssemblage(int id, TypeUsine type, Icone icone, Point position, Chemin chemin, int quantite, int intervalleProduction) {
        super(id, type, icone, position, chemin, quantite, intervalleProduction);
    }
}
