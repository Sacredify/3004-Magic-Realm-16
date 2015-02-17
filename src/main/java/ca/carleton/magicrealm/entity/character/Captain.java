package ca.carleton.magicrealm.entity.character;

import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.entity.Relationship;
import ca.carleton.magicrealm.entity.Vulnerability;
import ca.carleton.magicrealm.entity.natives.NativeFaction;
import ca.carleton.magicrealm.item.armor.BreastPlate;
import ca.carleton.magicrealm.item.armor.Helmet;
import ca.carleton.magicrealm.item.armor.Shield;
import ca.carleton.magicrealm.item.weapon.ShortSword;

/**
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 6:44 PM
 */
public class Captain extends AbstractCharacter {

    protected Captain() {
        this.vulnerability = Vulnerability.MEDIUM;

        this.addItem(new ShortSword());
        this.addItem(new Helmet());
        this.addItem(new BreastPlate());
        this.addItem(new Shield());

        this.addRelationship(NativeFaction.PATROL, Relationship.FRIENDLY);
        this.addRelationship(NativeFaction.SOLDIERS, Relationship.FRIENDLY);
        this.addRelationship(NativeFaction.GUARD, Relationship.FRIENDLY);
        this.addRelationship(NativeFaction.WOODFOLK, Relationship.UNFRIENDLY);
        this.addRelationship(NativeFaction.BASHKARS, Relationship.ENEMY);
    }

    @Override
    public EntityInformation getEntityInformation() {
        return EntityInformation.CHARACTER_CAPTAIN;
    }
}
