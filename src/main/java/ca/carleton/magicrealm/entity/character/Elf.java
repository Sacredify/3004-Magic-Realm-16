package ca.carleton.magicrealm.entity.character;

import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.entity.Relationship;
import ca.carleton.magicrealm.entity.Vulnerability;
import ca.carleton.magicrealm.entity.natives.NativeType;
import ca.carleton.magicrealm.item.weapon.LightBow;

/**
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 6:46 PM
 */
public class Elf extends AbstractCharacter {

    protected Elf() {
        this.vulnerability = Vulnerability.LIGHT;

        this.addItem(new LightBow());

        this.addRelationship(NativeType.WOODFOLK, Relationship.ALLY);
        this.addRelationship(NativeType.BASHKARS, Relationship.FRIENDLY);
        this.addRelationship(NativeType.ORDER, Relationship.UNFRIENDLY);
        this.addRelationship(NativeType.LANCERS, Relationship.ENEMY);

    }

    @Override
    public EntityInformation getEntityInformation() {
        return EntityInformation.CHARACTER_ELF;
    }
}
