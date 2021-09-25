package model;

import java.awt.*;

public class Entrepot extends Usine{
    private int capacite;

    Entrepot(int id, TypeUsine type, Icone icone, Point position, Chemin chemin, int capacite) {
        super(id, type, icone, position, chemin);
        this.capacite = capacite;
    }
}
