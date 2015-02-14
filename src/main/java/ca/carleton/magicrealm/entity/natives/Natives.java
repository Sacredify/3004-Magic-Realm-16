package ca.carleton.magicrealm.entity.natives;

import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.entity.EntityType;

/**
 * Represents one of the many natives within the magic Realm.
 * <p/>
 * Created with IntelliJ IDEA.
 * Date: 10/02/15
 * Time: 8:42 AM
 */
public class Natives extends Entity {

    public static EntityType entitytype = EntityType.NATIVE;

    private NativeType nativeType;

    public NativeType getNativeType() {
        return this.nativeType;
    }

    @Override
    public EntityType getEntityType() {
        return entitytype;
    }


    public void setNativeType(final NativeType nativeType) {
        this.nativeType = nativeType;
    }

}
