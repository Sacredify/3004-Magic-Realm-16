package ca.carleton.magicrealm.entity.character;

import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.entity.Relationship;
import ca.carleton.magicrealm.entity.natives.NativeFaction;
import ca.carleton.magicrealm.game.combat.Harm;
import ca.carleton.magicrealm.game.combat.chit.ActionChit;
import ca.carleton.magicrealm.game.combat.chit.ActionType;
import ca.carleton.magicrealm.item.armor.Shield;
import ca.carleton.magicrealm.item.armor.SuitOfArmor;
import ca.carleton.magicrealm.item.weapon.Mace;

/**
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 6:43 PM
 */
public class BlackKnight extends AbstractCharacter {

    private static final long serialVersionUID = 3288994592571894830L;

    protected BlackKnight() {
        this.vulnerability = Harm.MEDIUM;

        this.addItem(new Mace());
        this.addItem(new SuitOfArmor());
        this.addItem(new Shield());

        this.addGold(10);

        this.addRelationship(NativeFaction.COMPANY, Relationship.ALLY);
        this.addRelationship(NativeFaction.SOLDIERS, Relationship.FRIENDLY);
        this.addRelationship(NativeFaction.LANCERS, Relationship.UNFRIENDLY);
        this.addRelationship(NativeFaction.GUARD, Relationship.ENEMY);

        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(0).withStrength(Harm.MEDIUM).withTime(5).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(1).withStrength(Harm.HEAVY).withTime(5).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(1).withStrength(Harm.HEAVY).withTime(5).build());

        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(0).withStrength(Harm.HEAVY).withTime(6).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(1).withStrength(Harm.MEDIUM).withTime(4).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(0).withStrength(Harm.HEAVY).withTime(6).build());

        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(1).withStrength(Harm.MEDIUM).withTime(4).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(1).withStrength(Harm.MEDIUM).withTime(4).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(0).withStrength(Harm.MEDIUM).withTime(5).build());

        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(2).withStrength(Harm.HEAVY).withTime(4).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(2).withStrength(Harm.HEAVY).withTime(4).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(2).withStrength(Harm.MEDIUM).withTime(3).build());

    }

    @Override
    public EntityInformation getEntityInformation() {
        return EntityInformation.CHARACTER_BLACK_KNIGHT;
    }
}
