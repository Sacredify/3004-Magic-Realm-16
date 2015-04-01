package ca.carleton.magicrealm.game.phase;

import java.util.ArrayList;
import java.util.List;

/**
 * Bean class for the number of phases user gets.
 *
 * Created with IntelliJ IDEA.
 * Date: 01/04/2015
 * Time: 4:55 PM
 */
public class PhaseCountBean {

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

}
