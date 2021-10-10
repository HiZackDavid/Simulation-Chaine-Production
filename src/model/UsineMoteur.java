package model;

import java.awt.*;
import java.util.ArrayList;

public class UsineMoteur extends UsineProduction{
    public UsineMoteur(int id, TypeUsine type, Icone icone, Point position, ArrayList<Chemin> chemins, ArrayList<Entree> entrees, ArrayList<Sortie> sorties, int intervalleProduction) {
        super(id, type, icone, position, chemins, entrees, sorties, intervalleProduction);
    }
}
