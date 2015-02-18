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
    LIGHT_BOW("image/weapon/light_bow.gif", null),
    CROSSBOW("image/weapon/crossbow.gif", null),
    SPEAR("image/weapon/spear.gif", null),
    STAFF("image/weapon/staff.gif", null),
    GREAT_SWORD("image/weapon/great_sword.gif", null),
    BANE_SWORD(null, null),
    BROAD_SWORD("image/weapon/broadsword.gif", null),
    DEVIL_SWORD("image/weapon/flame_sword.gif", null),
    TRUESTEEL_SWORD(null, null),
    MORNING_STAR("image/weapon/morning_star.gif", null),
    GREAT_AXE("image/weapon/great_axe.gif", null),
    THRUSTING_SWORD(null, null),
    LIVING_SWORD(null, null),
    SHORT_SWORD("image/weapon/short_sword.gif", null),
    AXE("image/weapon/axe.gif", null),
    MACE("image/weapon/mace.gif", null),

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
