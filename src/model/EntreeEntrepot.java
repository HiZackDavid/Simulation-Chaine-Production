package model;

public class EntreeEntrepot extends Entree {
    int capacite;

    public EntreeEntrepot(TypeComposant type, int capacite) {
        super(type);
        this.capacite = capacite;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }
}
