package model;

public class Icone {
    private TypeIcone type;
    private String path;

    public Icone(TypeIcone type, String path) {
        this.type = type;
        this.path = path;
    }

    public TypeIcone getType() {
        return type;
    }

    public void setType(TypeIcone type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
