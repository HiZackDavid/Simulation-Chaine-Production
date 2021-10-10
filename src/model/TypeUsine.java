package model;

public enum TypeUsine {
    ASSEMBLAGE, AILE, MOTEUR, MATIERE, ENTREPOT;

    public static TypeUsine getType(String type) {
        return switch (type) {
            case "usine-assemblage" -> TypeUsine.ASSEMBLAGE;
            case "usine-aile" -> TypeUsine.AILE;
            case "usine-moteur" -> TypeUsine.MOTEUR;
            case "usine-matiere" -> TypeUsine.MATIERE;
            case "entrepot" -> TypeUsine.ENTREPOT;
            default -> null;
        };
    }

    public static String getType(TypeUsine type) {
        return switch (type) {
            case ASSEMBLAGE -> "usine-assemblage";
            case AILE -> "usine-aile";
            case MOTEUR -> "usine-moteur";
            case MATIERE -> "usine-matiere";
            case ENTREPOT -> "entrepot";
        };
    }
}
