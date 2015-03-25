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
 * Date: 14/02/15
 * Time: 6:45 PM
 */
public class Dwarf extends AbstractCharacter {

    protected Dwarf() {
        this.vulnerability = Harm.HEAVY;

        this.addItem(new GreatAxe());
        this.addItem(new Helmet());

        this.addGold(10);

        this.addRelationship(NativeFaction.COMPANY, Relationship.FRIENDLY);
        this.addRelationship(NativeFaction.GUARD, Relationship.FRIENDLY);
        this.addRelationship(NativeFaction.WOODFOLK, Relationship.UNFRIENDLY);
        this.addRelationship(NativeFaction.BASHKARS, Relationship.UNFRIENDLY);

        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.DUCK).withFatigueAsterisks(1).withStrength(Harm.TREMENDOUS).withTime(3).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(0).withStrength(Harm.HEAVY).withTime(6).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(1).withStrength(Harm.HEAVY).withTime(5).build());

        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(1).withStrength(Harm.TREMENDOUS).withTime(6).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(0).withStrength(Harm.HEAVY).withTime(6).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(2).withStrength(Harm.HEAVY).withTime(4).build());

        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(1).withStrength(Harm.HEAVY).withTime(5).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(1).withStrength(Harm.TREMENDOUS).withTime(6).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(2).withStrength(Harm.HEAVY).withTime(4).build());

        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(2).withStrength(Harm.TREMENDOUS).withTime(5).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(2).withStrength(Harm.TREMENDOUS).withTime(5).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(2).withStrength(Harm.TREMENDOUS).withTime(5).build());

    }

    @Override
    public EntityInformation getEntityInformation() {
        return EntityInformation.CHARACTER_DWARF;
    }
}
