package model;

import java.awt.Point;

public class UsineAile extends UsineProduction {
    UsineAile(int id, TypeComposant type, Icone icone, Point position, Chemin chemin, int quantite, int intervalleProduction) {
        super(id, type, icone, position, chemin, quantite, intervalleProduction);
    }
}
