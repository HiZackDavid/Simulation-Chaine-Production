package model;

public enum TypeUsine {
    ASSEMBLAGE, AILE, MOTEUR, MATIERE, ENTREPOT;

    public static TypeUsine getType(String type) {
        switch (type) {
            case "usine-assemblage" :
                return TypeUsine.ASSEMBLAGE;
            case "usine-aile" :
                return TypeUsine.AILE;
            case "usine-moteur" :
                return TypeUsine.MOTEUR;
            case "usine-matiere" :
                return TypeUsine.MATIERE;
            case "entrepot" :
                return TypeUsine.ENTREPOT;
            default:
                return null;
        }
    }

    public static String getType(TypeUsine type) {
        switch (type) {
            case ASSEMBLAGE: return "usine-assemblage";
            case AILE: return "usine-aile";
            case MOTEUR: return "usine-moteur";
            case MATIERE: return "usine-matiere";
            case ENTREPOT: return "entrepot";
            default:
                return null;
        }
    }
}
