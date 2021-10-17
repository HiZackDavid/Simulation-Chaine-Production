package model;

public class Entree {
    private TypeComposant type;
    private int requiredAmount;
    private int compteur;

    public Entree(TypeComposant type, int requiredAmount) {
        this.type = type;
        this.requiredAmount = requiredAmount;
        this.compteur = 0;
    }

    public TypeComposant getType() {
        return type;
    }

    public void setType(TypeComposant type) {
        this.type = type;
    }

    public void incrementCompteur() {
        this.compteur++;
    }

    public void takeRequiredAmount() {
        this.compteur -= requiredAmount;
    }

    public boolean hasReachedRequiredAmount() {
        return (compteur >= requiredAmount);
    }
}
