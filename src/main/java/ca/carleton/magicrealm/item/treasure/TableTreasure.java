package ca.carleton.magicrealm.item.treasure;

/**
 * Created by anvargazizov on 2015-03-19.
 */
public class TableTreasure extends Treasure {

    private static final long serialVersionUID = -4891037650972296410L;

    String affectedTable;
    String specialAttribute;
    String effect;
    int notoriety;
    int fame;

    public TableTreasure(String affectedTable, String specialAttribute, String effect, String name) {
        super(name);
        this.affectedTable = affectedTable;
        this.specialAttribute = specialAttribute;
        this.effect = effect;
        this.notoriety = 1 + random.nextInt(20);
        this.fame = -5 + random.nextInt(36);
    }
}
