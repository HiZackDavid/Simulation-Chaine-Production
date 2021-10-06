package model;

import java.awt.Point;
import java.util.ArrayList;

public abstract class Usine {
    private int id;
    private TypeUsine type;
    private Icone icone;
    private Point position;
    private ArrayList<Chemin> chemins;

    Usine(int id, TypeUsine type, Icone icone, Point position, ArrayList<Chemin> chemins) {
        this.id = id;
        this.type = type;
        this.icone = icone;
        this.position = position;
        this.chemins = chemins;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TypeUsine getType() {
        return type;
    }

    public void setType(TypeUsine type) {
        this.type = type;
    }

    public Icone getIcone() {
        return icone;
    }

    public void setIcone(Icone icone) {
        this.icone = icone;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public ArrayList<Chemin> getChemins() {
        return chemins;
    }

    public void addChemin(Chemin chemin) {
        this.chemins.add(chemin);
    }
}
