package model;

import java.awt.Point;
import java.util.ArrayList;

public abstract class UsineProduction extends Usine {
    private int intervalleProduction;

    public UsineProduction(int id, TypeUsine type, Icone icone, Point position, ArrayList<Chemin> chemins, ArrayList<Entree> entrees, ArrayList<Sortie> sorties, int intervalleProduction) {
        super(id, type, icone, position, chemins, entrees, sorties);
        this.intervalleProduction = intervalleProduction;
    }

    public boolean canProduce() {
        boolean canProduce = true; // On assume qu'un usine peut produire

        // Dans le cas où une usine a plusieurs entrées.
        if (getEntrees().size() > 0) {
            // On parcoure chacune des entrées de l'usine
            for (Entree entree : getEntrees()) {
                /*
                 Si au moins l'une d'entre elles n'a pas atteint sa limite, ça veut dire qu'elle n'a pas le
                 nécessaire pour produire.
                 */
                if (!entree.hasReachedLimit()) {
                    canProduce = false;
                };
            }
        }

        return canProduce;
    }

    public abstract Composant produce();
}
