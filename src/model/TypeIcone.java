package model;

public enum TypeIcone {
    VIDE, UN_TIERS, DEUX_TIERS, PLEIN;

    public static String getType(TypeIcone type) {
        return switch (type) {
            case VIDE -> "vide";
            case UN_TIERS -> "un-tiers";
            case DEUX_TIERS -> "deux-tiers";
            case PLEIN -> "plein";
        };
    }
}
