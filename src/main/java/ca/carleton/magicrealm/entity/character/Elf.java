package ca.carleton.magicrealm.entity.character;

import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.entity.Vulnerability;
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
    }

    @Override
    public EntityInformation getEntityInformation() {
        return EntityInformation.CHARACTER_ELF;
    }
}
