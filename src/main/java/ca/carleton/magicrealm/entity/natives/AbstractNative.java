package ca.carleton.magicrealm.entity.natives;

import ca.carleton.magicrealm.entity.Entity;

/**
 * Represents one of the many natives within the magic Realm.
 * <p/>
 * Created with IntelliJ IDEA.
 * Date: 10/02/15
 * Time: 8:42 AM
 */
public abstract class AbstractNative extends Entity {

    public abstract NativeType getNativeType();

}
