package model.entity.enums;

public enum EditionTheme {
    SPORT, CULTURE, NEWS, POLITIC, COOKING;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    public static EditionTheme setTheme(String theme) {
        switch (theme) {
            case "sport":
                return SPORT;
            case "culture":
                return CULTURE;
            case "news":
                return NEWS;
            case "politic":
                return POLITIC;
            case "cooking":
                return COOKING;
            default:
                return null;
        }
    }
}
