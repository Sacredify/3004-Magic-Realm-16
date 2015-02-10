package ca.carleton.magicrealm.entity.character;

import ca.carleton.magicrealm.entity.Entity;
import ca.carleton.magicrealm.entity.EntityType;
import ca.carleton.magicrealm.item.Item;

import java.util.List;

/**
 * Represents a playable character within the Magic Realm.
 * <p/>
 * Created with IntelliJ IDEA.
 * Date: 10/02/15
 * Time: 8:25 AM
 */
public abstract class Character extends Entity {

    public static final EntityType entityType = EntityType.CHARACTER;

    private List<Item> items;

    @Override
    public EntityType getEntityType() {
        return entityType;
    }
}
