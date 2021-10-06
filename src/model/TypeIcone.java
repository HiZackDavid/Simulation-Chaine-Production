package model;

public enum TypeIcone {
    VIDE, UN_TIERS, DEUX_TIERS, PLEIN;

    public static String getType(TypeIcone type) {
        switch (type) {
            case VIDE :  return "vide";
            case UN_TIERS : return "un-tiers";
            case DEUX_TIERS : return "deux-tiers";
            case PLEIN : return "plein";
            default : return null;
        }
    }
}
