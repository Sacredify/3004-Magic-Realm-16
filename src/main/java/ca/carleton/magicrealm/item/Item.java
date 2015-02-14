package ca.carleton.magicrealm.item;

/**
 * Represents an item (weapon, armor, etc.) within the Magic Realm.
 * <p/>
 * Created with IntelliJ IDEA.
 * Date: 10/02/15
 * Time: 8:26 AM
 */
public abstract class Item {

    protected int goldValue;

    protected boolean active = false;

    public int getGoldValue() {
        return this.goldValue;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public abstract ItemInformation getItemInformation();

}
