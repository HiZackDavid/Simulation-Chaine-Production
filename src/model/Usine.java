package model;

import java.awt.Point;
import java.util.ArrayList;

public abstract class Usine {
    public static final int WIDTH = 32;
    public static final int HEIGTH = 30;

    private int id;
    private TypeUsine type;
    private Icone icone;
    private Point position;
    private ArrayList<Chemin> chemins;
    private ArrayList<Entree> entrees;
    private ArrayList<Sortie> sorties;

    public Usine(int id, TypeUsine type, Icone icone, Point position, ArrayList<Chemin> chemins, ArrayList<Entree> entrees, ArrayList<Sortie> sorties) {
        this.id = id;
        this.type = type;
        this.icone = icone;
        this.position = position;
        this.chemins = chemins;
        this.entrees = entrees;
        this.sorties = sorties;
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

    public void setChemins(ArrayList<Chemin> chemins) {
        this.chemins = chemins;
    }

    public ArrayList<Entree> getEntrees() {
        return entrees;
    }

    public void setEntrees(ArrayList<Entree> entrees) {
        this.entrees = entrees;
    }

    public ArrayList<Sortie> getSorties() {
        return sorties;
    }

    public void setSorties(ArrayList<Sortie> sorties) {
        this.sorties = sorties;
    }
}
