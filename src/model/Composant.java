package model;

import java.awt.Point;

public class Composant {
    private TypeComposant typeComposant;
    private Point position;

    public Composant(TypeComposant typeComposant, Point position) {
        this.typeComposant = typeComposant;
        this.position = position;
    }

    public TypeComposant getTypeComposant() {
        return typeComposant;
    }

    public void setTypeComposant(TypeComposant typeComposant) {
        this.typeComposant = typeComposant;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }
}
