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

    public int getLimite() {
        return limite;
    }

    public void setLimite(int limite) {
        this.limite = limite;
    }

    public void incrementCompteur() {
        this.compteur++;
    }

    public boolean hasReachedLimit() {
        return (compteur == limite);
    }
}
