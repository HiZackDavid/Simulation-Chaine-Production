package model;

import java.awt.*;
import java.util.ArrayList;

public class UsineMatiere extends UsineProduction{
    public UsineMatiere(int id, TypeUsine type, Icone icone, Point position, ArrayList<Chemin> chemins, ArrayList<Entree> entrees, ArrayList<Sortie> sorties, int intervalleProduction) {
        super(id, type, icone, position, chemins, entrees, sorties, intervalleProduction);
    }

    @Override
    public Composant produce() {
        return new Composant(TypeComposant.METAL, getPosition());
    }
}
