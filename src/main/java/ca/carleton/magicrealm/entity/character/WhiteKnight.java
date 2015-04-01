package ca.carleton.magicrealm.entity.character;

import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.entity.Relationship;
import ca.carleton.magicrealm.entity.natives.NativeFaction;
import ca.carleton.magicrealm.game.combat.Harm;
import ca.carleton.magicrealm.game.combat.chit.ActionChit;
import ca.carleton.magicrealm.game.combat.chit.ActionType;
import ca.carleton.magicrealm.item.armor.SuitOfArmor;
import ca.carleton.magicrealm.item.weapon.GreatSword;

/**
 * Created with IntelliJ IDEA.
 * Date: 01/04/2015
 * Time: 4:06 PM
 */
public class WhiteKnight extends AbstractCharacter {

    private static final long serialVersionUID = 1498082417431469720L;

    protected WhiteKnight() {
        this.vulnerability = Harm.HEAVY;

        this.addItem(new GreatSword());
        this.addItem(new SuitOfArmor());

        this.addGold(10);

        this.addRelationship(NativeFaction.ORDER, Relationship.ALLY);
        this.addRelationship(NativeFaction.LANCERS, Relationship.FRIENDLY);
        this.addRelationship(NativeFaction.BASHKARS, Relationship.UNFRIENDLY);
        this.addRelationship(NativeFaction.COMPANY, Relationship.ENEMY);

        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(1).withStrength(Harm.HEAVY).withTime(5).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(0).withStrength(Harm.HEAVY).withTime(6).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(1).withStrength(Harm.HEAVY).withTime(5).build());

        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(2).withStrength(Harm.HEAVY).withTime(4).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(1).withStrength(Harm.HEAVY).withTime(6).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(2).withStrength(Harm.HEAVY).withTime(4).build());

        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(2).withStrength(Harm.HEAVY).withTime(4).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(1).withStrength(Harm.HEAVY).withTime(5).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MAGIC).withFatigueAsterisks(2).withStrength(Harm.HEAVY).withTime(5).build());

        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(1).withStrength(Harm.TREMENDOUS).withTime(6).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(2).withStrength(Harm.TREMENDOUS).withTime(4).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(1).withStrength(Harm.TREMENDOUS).withTime(5).build());

    }

    @Override
    public EntityInformation getEntityInformation() {
        return EntityInformation.CHARACTER_WHITE_KNIGHT;
    }

}
