package ca.carleton.magicrealm.entity.character;

import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.entity.Vulnerability;
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

    protected static Amazon createAmazon() {

        final Amazon amazon = new Amazon();

        amazon.vulnerability = Vulnerability.MEDIUM;
        amazon.addItem(new ShortSword());
        amazon.addItem(new Helmet());
        amazon.addItem(new BreastPlate());
        amazon.addItem(new Shield());

        return amazon;
    }

    private Amazon() {
    }

    @Override
    public EntityInformation getEntityInformation() {
        return EntityInformation.AMAZON;
    }
}
