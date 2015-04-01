package ca.carleton.magicrealm.entity.character;

import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.entity.Relationship;
import ca.carleton.magicrealm.entity.natives.NativeFaction;
import ca.carleton.magicrealm.game.combat.Harm;
import ca.carleton.magicrealm.game.combat.chit.ActionChit;
import ca.carleton.magicrealm.game.combat.chit.ActionType;
import ca.carleton.magicrealm.item.armor.Helmet;
import ca.carleton.magicrealm.item.weapon.GreatAxe;

/**
 * Created with IntelliJ IDEA.
 * Date: 01/04/2015
 * Time: 4:06 PM
 */
public class Berserker extends AbstractCharacter {

    private static final long serialVersionUID = -1907599703877646935L;

    protected Berserker() {
        this.vulnerability = Harm.HEAVY;

        this.addItem(new GreatAxe());
        this.addItem(new Helmet());

        this.addGold(10);

        this.addRelationship(NativeFaction.LANCERS, Relationship.FRIENDLY);
        this.addRelationship(NativeFaction.ROGUES, Relationship.FRIENDLY);
        this.addRelationship(NativeFaction.PATROL, Relationship.UNFRIENDLY);
        this.addRelationship(NativeFaction.GUARD, Relationship.UNFRIENDLY);

        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(1).withStrength(Harm.TREMENDOUS).withTime(6).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(2).withStrength(Harm.HEAVY).withTime(4).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(0).withStrength(Harm.HEAVY).withTime(5).build());

        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(1).withStrength(Harm.TREMENDOUS).withTime(6).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(2).withStrength(Harm.HEAVY).withTime(4).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(0).withStrength(Harm.HEAVY).withTime(5).build());

        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(2).withStrength(Harm.HEAVY).withTime(4).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(1).withStrength(Harm.TREMENDOUS).withTime(6).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(2).withStrength(Harm.TREMENDOUS).withTime(4).build());

        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.BERSERK).withFatigueAsterisks(2).withStrength(Harm.TREMENDOUS).withTime(4).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(1).withStrength(Harm.TREMENDOUS).withTime(5).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(2).withStrength(Harm.TREMENDOUS).withTime(4).build());

    }

    /**
     * The berserker's vulnerability is TREMENDOUS if they have played a fatigued berserk chit.
     *
     * @return their health.
     */
    @Override
    public Harm getVulnerability() {
        if (this.actionChits.stream().filter(chit -> chit.getAction() == ActionType.BERSERK && chit.isFatigued()).count() > 0) {
            return Harm.TREMENDOUS;
        } else {
            return this.vulnerability;
        }
    }

    @Override
    public EntityInformation getEntityInformation() {
        return EntityInformation.CHARACTER_BERSERKER;
    }
}
