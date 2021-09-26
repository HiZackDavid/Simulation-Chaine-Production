package model;

import java.awt.*;
import java.util.ArrayList;

public class UsineMoteur extends UsineProduction{
    public UsineMoteur(int id, TypeUsine type, Icone icone, Point position, ArrayList<Chemin> chemins, int intervalleProduction) {
        super(id, type, icone, position, chemins, intervalleProduction);
    }
}
