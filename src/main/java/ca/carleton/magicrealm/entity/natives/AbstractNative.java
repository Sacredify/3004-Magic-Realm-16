package ca.carleton.magicrealm.entity.natives;

import ca.carleton.magicrealm.entity.BountyCarrier;
import ca.carleton.magicrealm.entity.Denizen;

/**
 * Represents one of the many natives within the magic Realm.
 * <p/>
 * Created with IntelliJ IDEA.
 * Date: 10/02/15
 * Time: 8:42 AM
 */
public abstract class AbstractNative extends Denizen implements BountyCarrier {

    protected NativeFaction faction;

    protected int basicGoldWage;

    protected boolean leader = false;

    /**
     * The faction this native belongs to.
     *
     * @return their faction.
     */
    public NativeFaction getFaction() {
        return this.faction;
    }

    /**
     * The basic amount of gold to hire the native. Multiply by the price result to get the final value.
     *
     * @return the gold amount.
     */
    public int getBasicGoldWage() {
        return this.basicGoldWage;
    }

    /**
     * Mark the native as the leader.
     */
    public void markLeader() {
        this.leader = true;
    }

    /**
     * Determine if the native is a leader.
     *
     * @return true if yes.
     */
    public boolean isLeader() {
        return this.leader;
    }

    @Override
    public String toString() {
        return super.toString() + " [" + this.faction + "]" + this.isLeaderString();
    }

    private String isLeaderString() {
        if (this.leader) {
            return " - LEADER";
        } else {
            return "";
        }
    }
}
