package ca.carleton.magicrealm.item;

/**
 * File names mapping to the image in the image package items.
 * <p/>
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 9:05 AM
 */
public enum ItemInformation {

    // Weapons
    MEDIUM_BOW(null, null),
    LIGHT_BOW(null, null),
    CROSSBOW(null, null),
    SPEAR(null, null),
    STAFF(null, null),
    GREAT_SWORD(null, null),
    BANE_SWORD(null, null),
    BROAD_SWORD(null, null),
    DEVIL_SWORD(null, null),
    TRUESTEEL_SWORD(null, null),
    MORNING_STAR(null, null),
    GREAT_AXE(null, null),
    THRUSTING_SWORD(null, null),
    LIVING_SWORD(null, null),
    SHORT_SWORD(null, null),
    AXE(null, null),
    MACE(null, null),

    // Armor
    SUIT_OF_ARMOR("image/armor/suitofarmor.gif", null),
    BREASTPLATE("image/armor/breastplate.gif", null),
    HELMET("image/armor/cap.gif", null),
    SHIELD("image/armor/buckler.gif", null),
    TREMENDOUS_ARMOR("image/armor/breastplate.gif", null),
    SILVER_BREASTPLATE("image/armor/cuirass.gif", null),
    GOLD_HELMET("image/armor/helmet.gif", null),
    JADE_SHIELD("image/armor/shield.gif", null),
    NONE(null, null);

    private final String activeImageFilePath;

    private final String inactiveImageFilePath;

    ItemInformation(final String activeImageFilePath, final String inactiveImageFilePath) {
        this.activeImageFilePath = activeImageFilePath;
        this.inactiveImageFilePath = inactiveImageFilePath;
    }

    public String getActivePath() {
        return this.activeImageFilePath;
    }

    public String getInactivePath() {
        return this.inactiveImageFilePath;
    }
}
