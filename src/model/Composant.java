package model;

public class Composant {
    TypeComposant typeComposant;
    private int x;
    private int y;

    public Composant(TypeComposant typeComposant, int x, int y) {
        this.typeComposant = typeComposant;
        this.x = x;
        this.y = y;
    }

    public TypeComposant getTypeComposant() {
        return typeComposant;
    }

    public void setTypeComposant(TypeComposant typeComposant) {
        this.typeComposant = typeComposant;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
