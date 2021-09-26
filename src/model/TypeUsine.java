package model;

public enum TypeUsine {
    ASSEMBLAGE, AILE, MOTEUR, MATIERE, ENTREPOT;

    public static TypeUsine getType(String type) {
        switch (type) {
            case "usine-matiere" :
                return TypeUsine.MATIERE;
            case "usine-aile" :
                return TypeUsine.AILE;
            case "usine-moteur" :
                return TypeUsine.MOTEUR;
            case "usine-assemblage" :
                return TypeUsine.ASSEMBLAGE;
            case "entrepot" :
                return TypeUsine.ENTREPOT;
            default:
                return null;
        }
    }
}
