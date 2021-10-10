package model;

public enum TypeComposant {
    METAL, AILE, MOTEUR, AVION;

    public static TypeComposant getType(String type) {
        return switch (type) {
            case "metal" -> TypeComposant.METAL;
            case "aile" -> TypeComposant.AILE;
            case "moteur" -> TypeComposant.MOTEUR;
            case "avion" -> TypeComposant.AVION;
            default -> null;
        };
    }

    public static String getType(TypeComposant type) {
        return switch (type) {
            case METAL -> "metal";
            case AILE -> "aile";
            case MOTEUR -> "moteur";
            case AVION -> "avion";
        };
    }
}
