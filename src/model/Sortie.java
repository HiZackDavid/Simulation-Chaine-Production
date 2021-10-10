package model;

public class Sortie {
    private TypeComposant type;

    public Sortie(TypeComposant type) {
        this.type = type;
    }

    public TypeComposant getType() {
        return type;
    }

    public void setType(TypeComposant type) {
        this.type = type;
    }
}
