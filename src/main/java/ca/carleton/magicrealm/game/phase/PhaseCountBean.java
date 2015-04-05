package ca.carleton.magicrealm.game.phase;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean class for the number of phases user gets.
 * <p>
 * Created with IntelliJ IDEA.
 * Date: 01/04/2015
 * Time: 4:55 PM
 */
public class PhaseCountBean {

    private boolean refundExtraPhase;

    private PhaseType lastUsed;

    private int numberOfPhasesFOrDay;

    private final List<PhaseType> extraPhases = new ArrayList<>();

    public List<PhaseType> getExtraPhases() {
        return this.extraPhases;
    }

    public void addExtraPhase(final PhaseType extraPhase) {
        this.extraPhases.add(extraPhase);
    }

    public int getNumberOfPhasesFOrDay() {
        return this.numberOfPhasesFOrDay;
    }

    public void setNumberOfPhasesFOrDay(final int numberOfPhasesFOrDay) {
        this.numberOfPhasesFOrDay = numberOfPhasesFOrDay;
    }

    public boolean hasMorePhases() {
        return this.numberOfPhasesFOrDay + this.extraPhases.size() > 0;
    }

    public boolean canMakeMountainMove() {
        // Only 1 because we already remove one before this is called.
        return this.numberOfPhasesFOrDay + this.extraPhases.size() >= 1;
    }

    public void refundLast() {
        if (this.refundExtraPhase) {
            this.extraPhases.add(this.lastUsed);
        } else {
            this.numberOfPhasesFOrDay += 1;
        }
    }

    public void removeOne() {
        this.numberOfPhasesFOrDay -= 1;
        this.refundExtraPhase = false;
    }

    public void removeExtraPhase(final PhaseType selectedPhase) {
        this.extraPhases.remove(selectedPhase);
        this.lastUsed = selectedPhase;
        this.refundExtraPhase = true;
    }
}
