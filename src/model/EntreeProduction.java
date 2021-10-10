package model;

public class EntreeProduction extends Entree {
    private int quantite;

    public EntreeProduction(TypeComposant type, int quantite) {
        super(type);
        this.quantite = quantite;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}
