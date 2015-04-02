package ca.carleton.magicrealm.game.combat;

import ca.carleton.magicrealm.entity.Denizen;
import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.combat.chit.ActionChit;
import ca.carleton.magicrealm.game.combat.chit.ActionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Date: 24/03/2015
 * Time: 5:55 PM
 */
public class MeleeSheets implements Serializable {

    private static final Logger LOG = LoggerFactory.getLogger(MeleeSheets.class);

    private static final long serialVersionUID = 1945181772117961369L;

    private static final Random RANDOM = new Random();

    private final Map<Entity, MeleeSheet> meleeSheets = new HashMap<Entity, MeleeSheet>();

    /**
     * Create a new MeleeSheet used during combat for the given player.
     *
     * @param player the player.
     */
    public void createNewMeleeSheetForPlayer(final Player player) {
        this.meleeSheets.putIfAbsent(player.getCharacter(), new MeleeSheet(player.getCharacter(), player));
    }

    /**
     * Create a new MeleeSheet used during combat for the given denizen.
     *
     * @param denizen the denizen (native, monster).
     */
    public void createNewMeleeSheetForDenizen(final Denizen denizen) {
        final MeleeSheet denizenSheet = new MeleeSheet(denizen, null);

        // Lets just randomly assign their maneuvers and attacks here, since the logic gets a lot more complicated.
        final int roll = RANDOM.nextInt(3);
        if (roll == 0) {
            denizenSheet.setManeuver(Maneuver.CHARGE);
            denizenSheet.setAttackDirection(AttackDirection.THRUST);
        } else if (roll == 1) {
            denizenSheet.setManeuver(Maneuver.DODGE);
            denizenSheet.setAttackDirection(AttackDirection.SWING);
        } else {
            denizenSheet.setManeuver(Maneuver.DUCK);
            denizenSheet.setAttackDirection(AttackDirection.SMASH);
        }

        // Note, for denizens and monsters, we don't use action chits, but they have their own values in a table.
        // For simplicity, I think itl be easier to just randomly assign sharpness starts (0-2) and timings (2-5).
        // Setup for a denizen.
        denizenSheet.setAttackWeapon(denizen.getWeapon());

        denizenSheet.setAttackChit(
                new ActionChit.ActionChitBuilder(ActionType.FIGHT)
                        .withStrength(denizen.getVulnerability())
                        .withFatigueAsterisks(RANDOM.nextInt(3))
                        .withTime(RANDOM.nextInt(4) + 2)
                        .build()
        );

        denizenSheet.setManeuverChit(
                new ActionChit.ActionChitBuilder(ActionType.MOVE)
                        .withStrength(denizen.getMoveStrength())
                        .withFatigueAsterisks(RANDOM.nextInt(3))
                        .withTime(RANDOM.nextInt(4) + 2)
                        .build()
        );

        this.meleeSheets.putIfAbsent(denizen, denizenSheet);
        LOG.info("Created new melee sheet for denizen {}.", denizen);
    }

    /**
     * Return the melee sheet used by the player.
     *
     * @param player the player.
     * @return the melee sheet.
     */
    public MeleeSheet getMeleeSheetForPlayer(final Player player) {
        return this.meleeSheets.get(player.getCharacter());
    }

    public MeleeSheet getMeleeSheet(final Entity entity) {
        return this.meleeSheets.get(entity);
    }

    public List<MeleeSheet> getAllSheets() {
        return this.meleeSheets.values().stream().collect(Collectors.toList());
    }

}
