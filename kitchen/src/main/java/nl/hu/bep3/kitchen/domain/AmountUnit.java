package nl.hu.bep3.kitchen.domain;

public enum AmountUnit {
    TEASPOON("tsp"),
    TABLESPOON("tbsp"),
    CUP("c"),
    LITER("l"),
    MILLILITER("ml"),
    KILOGRAM("kg"),
    GRAM("g"),
    MILLIGRAM("mg"),
    OUNCE("oz"),
    POUNDS("lb");

    private final String abbreviation;

    AmountUnit(final String abbreviation) {
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation() {
        return this.abbreviation;
    }
}
