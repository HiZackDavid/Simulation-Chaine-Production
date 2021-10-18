package model;

import java.awt.Point;
import java.util.ArrayList;

public class UsineAssemblage extends UsineProduction {
    public UsineAssemblage(int id, TypeUsine type, Icone icone, Point position, ArrayList<Chemin> chemins, ArrayList<Entree> entrees, ArrayList<Sortie> sorties, int intervalleProduction) {
        super(id, type, icone, position, chemins, entrees, sorties, intervalleProduction);
    }

    @Override
    public Component produce() {
        Component component = new Component(TypeComposant.AVION, new Point(getPosition().x, getPosition().y));

        for (int index = 0; index < getEntrees().size(); index++) {
            getEntrees().get(index).takeRequiredAmount();
        }

        return component;
    }

    @Override
    public void update() {
        System.out.println("Update Assemblage");
    }
}
