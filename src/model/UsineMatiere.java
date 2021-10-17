package model;

import java.awt.*;
import java.util.ArrayList;

public class UsineMatiere extends UsineProduction{
    public UsineMatiere(int id, TypeUsine type, Icone icone, Point position, ArrayList<Chemin> chemins, ArrayList<Entree> entrees, ArrayList<Sortie> sorties, int intervalleProduction) {
        super(id, type, icone, position, chemins, entrees, sorties, intervalleProduction);
    }

    @Override
    public Composant produce() {
        Composant composant = new Composant(TypeComposant.METAL, new Point(getPosition().x, getPosition().y));

        for (int index = 0; index < getEntrees().size(); index++) {
            getEntrees().get(index).takeRequiredAmount();
        }

        return composant;
    }

    @Override
    public void update() {

    }
}
