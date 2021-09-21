package model;

import java.awt.*;

public class UsineMoteur extends UsineProduction{
    UsineMoteur(int id, TypeComposant type, Icone icone, Point position, Chemin chemin, int quantite, int intervalleProduction) {
        super(id, type, icone, position, chemin, quantite, intervalleProduction);
    }
}
