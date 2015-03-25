package ca.carleton.magicrealm.entity.character;

import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.entity.Relationship;
import ca.carleton.magicrealm.entity.natives.NativeFaction;
import ca.carleton.magicrealm.game.combat.Harm;
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
        this.vulnerability = Harm.LIGHT;

        this.addItem(new LightBow());

        this.addGold(10);

        this.addRelationship(NativeFaction.WOODFOLK, Relationship.ALLY);
        this.addRelationship(NativeFaction.BASHKARS, Relationship.FRIENDLY);
        this.addRelationship(NativeFaction.ORDER, Relationship.UNFRIENDLY);
        this.addRelationship(NativeFaction.LANCERS, Relationship.ENEMY);

        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MAGIC).withFatigueAsterisks(1).withStrength(Harm.NEGLIGIBLE).withTime(3).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MAGIC).withFatigueAsterisks(1).withStrength(Harm.NEGLIGIBLE).withTime(4).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MAGIC).withFatigueAsterisks(1).withStrength(Harm.NEGLIGIBLE).withTime(4).build());

        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MAGIC).withFatigueAsterisks(1).withStrength(Harm.NEGLIGIBLE).withTime(3).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MAGIC).withFatigueAsterisks(1).withStrength(Harm.NEGLIGIBLE).withTime(3).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MAGIC).withFatigueAsterisks(1).withStrength(Harm.NEGLIGIBLE).withTime(2).build());

        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(1).withStrength(Harm.LIGHT).withTime(3).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(1).withStrength(Harm.LIGHT).withTime(3).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(1).withStrength(Harm.LIGHT).withTime(2).build());

        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.MOVE).withFatigueAsterisks(0).withStrength(Harm.MEDIUM).withTime(4).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(1).withStrength(Harm.MEDIUM).withTime(3).build());
        this.addActionChit(new ActionChit.ActionChitBuilder(ActionType.FIGHT).withFatigueAsterisks(0).withStrength(Harm.MEDIUM).withTime(4).build());

    }

    @Override
    public EntityInformation getEntityInformation() {
        return EntityInformation.CHARACTER_ELF;
    }
}
