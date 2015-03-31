package ca.carleton.magicrealm.game.combat;

import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.entity.Denizen;
import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.combat.chit.ActionChit;
import ca.carleton.magicrealm.item.armor.AbstractArmor;
import ca.carleton.magicrealm.item.weapon.AbstractWeapon;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Date: 16/03/15
 * Time: 3:52 PM
 */
public class MeleeSheet implements Serializable {

    private static final long serialVersionUID = 2502936258541118790L;

    private static final int NUMBER_ATTACKERS = 3;

    private Entity entity;

    private Entity target;

    private Player player;

    // The move or fight chit used during the encounter step. If encounter, allow the player to alert a weapon. If move, run away to another clearing.
    private ActionChit encounterStepChit;

    // Chits used when planning an "Attack" are the weapon, a FIGHT chit for the timing, and a direction.
    private AbstractWeapon attackWeapon;

    private ActionChit attackChit;

    private AttackDirection attackDirection;

    // Chits used when planning a "Maneuver" are the move chit used for the timing, and the maneuver selected.
    private Maneuver maneuver;

    private ActionChit maneuverChit;

    // Armor. Can only use "active" armor.
    private AbstractArmor armor;

    /**
     * Creates a melee sheet for an entity, and optionally a related player.
     *
     * @param entity the entity.
     * @param player the player.
     */
    MeleeSheet(final Entity entity, final Player player) {
        this.entity = entity;
        this.player = player;
    }

    /**
     * Reset the sheet if this sheet is for a player. If its for a denizen, we'll just always use the same stuff for simplicity.
     */
    public void resetSheet() {
        if (this.player != null) {
            this.attackChit = null;
            this.encounterStepChit = null;
            this.attackWeapon = null;
            this.attackDirection = null;
            this.maneuver = null;
        }
        this.target = null;
    }

    /**
     * Update applicable references after being sent to and from the server.
     */
    public void updateFromServer(final BoardModel boardModel) {

        // Update player object if needed
        if (this.player != null) {
            boardModel.getPlayers().stream().filter(player -> player.getCharacter().equals(this.player.getCharacter())).forEach(player -> {
                this.player = player;
                this.entity = this.player.getCharacter();
            });

            // Only need to update player chits, since it really doesn't matter with denizens, we only want their values. We don't wound them.
            if (this.encounterStepChit != null) {
                this.player.getCharacter().getActionChits().stream().filter(this.encounterStepChit::equals).forEach(actionChit -> this.encounterStepChit = actionChit);
            }
            if (this.attackChit != null) {
                this.player.getCharacter().getActionChits().stream().filter(this.attackChit::equals).forEach(actionChit -> this.attackChit = actionChit);
            }
            if (this.maneuverChit != null) {
                this.player.getCharacter().getActionChits().stream().filter(this.maneuverChit::equals).forEach(actionChit -> this.maneuverChit = actionChit);
            }

            // update player and armor from items
            if (this.attackWeapon != null) {
                this.player.getCharacter().getItems().stream().filter(this.attackWeapon::equals).forEach(weapon -> this.attackWeapon = (AbstractWeapon) weapon);
            }

            if (this.armor != null) {
                this.player.getCharacter().getItems().stream().filter(this.armor::equals).forEach(armor -> this.armor = (AbstractArmor) armor);
            }

        } else {
            // Update denizens (owner)
            boardModel.getMonsters().stream().filter(this.entity::equals).forEach(entity -> this.entity = entity);
            this.attackWeapon =  ((Denizen)this.entity).getWeapon();
        }

    }

    public Entity getOwner() {
        return this.entity;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setEncounterStepChit(final ActionChit chit) {
        this.encounterStepChit = chit;
    }

    public void setAttackChit(final ActionChit chit) {
        this.attackChit = chit;
    }

    public void setManeuverChit(final ActionChit chit) {
        this.maneuverChit = chit;
    }

    public ActionChit getEncounterStepChit() {
        return this.encounterStepChit;
    }

    public ActionChit getAttackChit() {
        return this.attackChit;
    }

    public ActionChit getManeuverChit() {
        return this.maneuverChit;
    }

    public AbstractWeapon getAttackWeapon() {
        return this.attackWeapon;
    }

    public void setAttackWeapon(final AbstractWeapon attackWeapon) {
        this.attackWeapon = attackWeapon;
    }

    public AttackDirection getAttackDirection() {
        return this.attackDirection;
    }

    public void setAttackDirection(final AttackDirection attackDirection) {
        this.attackDirection = attackDirection;
    }

    public Maneuver getManeuver() {
        return this.maneuver;
    }

    public void setManeuver(final Maneuver maneuver) {
        this.maneuver = maneuver;
    }

    public AbstractArmor getArmor() {
        return this.armor;
    }

    public void setArmor(final AbstractArmor armor) {
        this.armor = armor;
    }

    public Entity getTarget() {
        return this.target;
    }

    public void setTarget(final Entity target) {
        this.target = target;
    }
}
