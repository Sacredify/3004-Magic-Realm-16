package ca.carleton.magicrealm.entity.character;

import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.entity.Relationship;
import ca.carleton.magicrealm.entity.Vulnerability;
import ca.carleton.magicrealm.entity.natives.NativeFaction;
import ca.carleton.magicrealm.game.combat.chit.ActionChit;
import ca.carleton.magicrealm.game.combat.chit.ActionType;
import ca.carleton.magicrealm.item.weapon.LightBow;

/**
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 6:46 PM
 */
public class Elf extends AbstractCharacter {

    private static final long serialVersionUID = 5013470935952866905L;

    protected Elf() {
        this.vulnerability = Vulnerability.LIGHT;

        this.addItem(new LightBow());

        this.addGold(10);

        this.addRelationship(NativeFaction.WOODFOLK, Relationship.ALLY);
        this.addRelationship(NativeFaction.BASHKARS, Relationship.FRIENDLY);
        this.addRelationship(NativeFaction.ORDER, Relationship.UNFRIENDLY);
        this.addRelationship(NativeFaction.LANCERS, Relationship.ENEMY);

        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MAGIC).withFatigueAsterisks(1).withStrength("3").withTime(3).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MAGIC).withFatigueAsterisks(1).withStrength("3").withTime(4).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MAGIC).withFatigueAsterisks(1).withStrength("7").withTime(4).build());

        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MAGIC).withFatigueAsterisks(1).withStrength("7").withTime(3).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MAGIC).withFatigueAsterisks(1).withStrength("3").withTime(3).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MAGIC).withFatigueAsterisks(1).withStrength("3").withTime(2).build());

        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(1).withStrength("L").withTime(3).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(1).withStrength("L").withTime(3).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(1).withStrength("L").withTime(2).build());

        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(0).withStrength("M").withTime(4).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(1).withStrength("M").withTime(3).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(0).withStrength("M").withTime(4).build());

    }

    @Override
    public EntityInformation getEntityInformation() {
        return EntityInformation.CHARACTER_ELF;
    }
}
