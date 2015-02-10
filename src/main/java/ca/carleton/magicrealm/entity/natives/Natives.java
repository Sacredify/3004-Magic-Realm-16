package ca.carleton.magicrealm.entity.natives;

import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.entity.EntityType;

/**
 * Represents one of the many natives within the magic Realm.
 * Created with IntelliJ IDEA.
 * Date: 10/02/15
 * Time: 8:42 AM
 */
public class Natives extends Entity {

    public static EntityType entitytype = EntityType.NATIVE;

    @Override
    public EntityType getEntityType() {
        return entitytype;
    }

}
