package model;

import java.awt.*;
import java.util.ArrayList;

public class Entrepot extends Usine{
    public Entrepot(int id, TypeUsine type, Icone icone, Point position, ArrayList<Chemin> chemins) {
        super(id, type, icone, position, chemins);
    }
}
