package javatro.core;

/**
 * Represents the types of bosses available in the game. Each boss type modifies gameplay rules in
 * specific ways.
 */
public enum BossType {
    NONE("", ""),
    THE_NEEDLE("The Needle", "Play only 1 hand"),
    THE_WATER("The Water", "Start with 0 discards"),
    THE_CLUB("The Club", "All Club cards cannot score"),
    THE_WINDOW("The Window", "All Diamond cards cannot score"),
    THE_HEAD("The Head", "All Heart cards cannot score"),
    THE_GOAD("The Goad", "All Spade cards cannot score"),
    THE_PLANT("The Plant", "All face cards cannot score"),
    THE_PSYCHIC("The Psychic", "Must play 5 cards (not all cards need to score)");

    private final String name;
    private final String description;

    /**
     * Constructs a BossType with the given description.
     *
     * @param name The name of the BossType.
     * @param description The description of the BossType.
     */
    BossType(String name, String description) {
        this.name = name;
        this.description = description;
    }

    /**
     * Returns the name of the BossType.
     *
     * @return The name of the BossType.
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the description of the BossType.
     *
     * @return The description of the BossType.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns a random boss type (excluding NONE).
     *
     * @return A randomly selected boss type
     */
    public static BossType getRandomBossType() {
        // skip the first value (NONE) to ensure a valid boss type is selected
        int randomIndex = (int) (Math.random() * (values().length - 1));
        assert randomIndex >= 0 && randomIndex < values().length - 1 : "Random index out of bounds";
        return values()[randomIndex + 1];
    }
}
