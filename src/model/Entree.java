package model;

public class Entree {
    private TypeComposant type;
    private int limite;
    private int compteur;

    public Entree(TypeComposant type, int limite) {
        this.type = type;
        this.limite = limite;
        this.compteur = 0;
    }

    public TypeComposant getType() {
        return type;
    }

    public void setType(TypeComposant type) {
        this.type = type;
    }

    public void incrementCompteur() {
        if (compteur == limite) {
            compteur = 0;
        }

        this.compteur++;
    }

    public void resetCompteur() {
        this.compteur = 0;
    }

    public boolean hasReachedLimit() {
        return (compteur == limite);
    }
}
