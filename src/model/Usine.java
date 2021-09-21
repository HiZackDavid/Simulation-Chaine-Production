package model;

import java.awt.Point;

public abstract class Usine {
    private int id;
    private TypeComposant type;
    private Icone icone;
    private Point position;
    private Chemin chemin;

    Usine(int id, TypeComposant type, Icone icone, Point position, Chemin chemin) {
        this.id = id;
        this.type = type;
        this.icone = icone;
        this.position = position;
        this.chemin = chemin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TypeComposant getType() {
        return type;
    }

    public void setType(TypeComposant type) {
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

    public Chemin getChemin() {
        return chemin;
    }

    public void setChemin(Chemin chemin) {
        this.chemin = chemin;
    }
}
