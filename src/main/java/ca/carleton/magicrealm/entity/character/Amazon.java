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
 * The amazon character.
 * <p/>
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 10:16 AM
 */
public class Amazon extends AbstractCharacter {

    protected Amazon() {
        this.vulnerability = Vulnerability.MEDIUM;

        this.addItem(new ShortSword());
        this.addItem(new Helmet());
        this.addItem(new BreastPlate());
        this.addItem(new Shield());

        this.addRelationship(NativeFaction.LANCERS, Relationship.FRIENDLY);
        this.addRelationship(NativeFaction.PATROL, Relationship.FRIENDLY);
        this.addRelationship(NativeFaction.COMPANY, Relationship.UNFRIENDLY);
        this.addRelationship(NativeFaction.BASHKARS, Relationship.UNFRIENDLY);

    }

    @Override
    public EntityInformation getEntityInformation() {
        return EntityInformation.CHARACTER_AMAZON;
    }
}
