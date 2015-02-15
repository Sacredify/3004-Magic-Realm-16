package ca.carleton.magicrealm.game;

/**
 * A player's individual victory condition data.
 * <p/>
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 10:27 AM
 */
public class VictoryCondition {

    private int gold;

    private int notoriety;

    private int fame;

    private int spellsCount;

    private int greatTreasuresCount;

    public void addGold(final int gold) {
        this.gold += gold;
    }

    public void addNotoriety(final int notoriety) {
        this.notoriety += notoriety;
    }

    public void addFame(final int fame) {
        this.fame += fame;
    }

    public void increaseSpellCount() {
        this.spellsCount++;
    }

    public void increaseGreatTreasuresCount() {
        this.greatTreasuresCount++;
    }

}
