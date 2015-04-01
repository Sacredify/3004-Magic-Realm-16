package ca.carleton.magicrealm.entity;

/**
 * The different type of relationships a character may have.
 * <p/>
 * Created with IntelliJ IDEA.
 * Date: 10/02/15
 * Time: 8:30 AM
 */
public enum Relationship {

    ENEMY,
    UNFRIENDLY,
    NEUTRAL,
    FRIENDLY,
    ALLY {
        @Override
        public Relationship getRelationshipAfterDrinksBought() {
            return ALLY;
        }
    };

    /**
     * Return the level of friendship after someone buys drinks.
     *
     * @return the new relationship.
     */
    public Relationship getRelationshipAfterDrinksBought() {
        return values()[this.ordinal() + 1];
    }
}
