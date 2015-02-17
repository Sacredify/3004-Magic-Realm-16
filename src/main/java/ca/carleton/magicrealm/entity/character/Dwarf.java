package ca.carleton.magicrealm.entity.character;

import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.entity.Relationship;
import ca.carleton.magicrealm.entity.Vulnerability;
import ca.carleton.magicrealm.entity.natives.NativeType;
import ca.carleton.magicrealm.item.armor.Helmet;
import ca.carleton.magicrealm.item.weapon.GreatAxe;

/**
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 6:45 PM
 */
public class Dwarf extends AbstractCharacter {

    protected Dwarf() {
        this.vulnerability = Vulnerability.HEAVY;

        this.addItem(new GreatAxe());
        this.addItem(new Helmet());

        this.addRelationship(NativeType.COMPANY, Relationship.FRIENDLY);
        this.addRelationship(NativeType.GUARD, Relationship.FRIENDLY);
        this.addRelationship(NativeType.WOODFOLK, Relationship.UNFRIENDLY);
        this.addRelationship(NativeType.BASHKARS, Relationship.UNFRIENDLY);
    }

    @Override
    public EntityInformation getEntityInformation() {
        return EntityInformation.CHARACTER_DWARF;
    }
}
