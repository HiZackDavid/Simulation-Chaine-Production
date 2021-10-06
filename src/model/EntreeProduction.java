package model;

public class EntreeProduction extends Entree {
    private int quantite;

    public EntreeProduction(String type, int quantite) {
        super(type);
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}
