package model;

import java.awt.*;

public class UsineMatiere extends UsineProduction{
    UsineMatiere(int id, TypeUsine type, Icone icone, Point position, Chemin chemin, int quantite, int intervalleProduction) {
        super(id, type, icone, position, chemin, quantite, intervalleProduction);
    }
}
