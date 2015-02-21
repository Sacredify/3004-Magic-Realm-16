package ca.carleton.magicrealm.entity.monster;

import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.entity.Vulnerability;

/**
 * Created with IntelliJ IDEA.
 * Date: 21/02/15
 * Time: 5:26 PM
 */
public class Ghost extends Entity {

    public Ghost() {
        this.vulnerability = Vulnerability.LIGHT;
    }

    @Override
    public EntityInformation getEntityInformation() {
        return EntityInformation.GHOST;
    }
}
