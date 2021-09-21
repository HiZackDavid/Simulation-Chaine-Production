package model;

import java.awt.*;

public class UsineMetal extends UsineProduction{
    UsineMetal(int id, TypeComposant type, Icone icone, Point position, Chemin chemin, int quantite, int intervalleProduction) {
        super(id, type, icone, position, chemin, quantite, intervalleProduction);
    }
}
