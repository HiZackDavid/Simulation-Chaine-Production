package model;

public abstract class Entree {
    private TypeComposant type;

    public Entree(TypeComposant type) {
        this.type = type;
    }

    public TypeComposant getType() {
        return type;
    }

    public void setType(TypeComposant type) {
        this.type = type;
    }
}
