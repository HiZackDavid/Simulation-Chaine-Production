package model;

public abstract class Entree {
    private String type;

    public Entree(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
