package ca.carleton.magicrealm.item;

/**
 * File names mapping to the image in the image package items.
 * <p>
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 9:05 AM
 */
public enum ItemInformation {

    // Weapons
    MEDIUM_BOW(null, null, "Medium bow"),
    LIGHT_BOW("image/weapon/light_bow.gif", null, "Light bow"),
    CROSSBOW("image/weapon/crossbow.gif", null, "Crossbow"),
    SPEAR("image/weapon/spear.gif", null, "Spear"),
    STAFF("image/weapon/staff.gif", null, "Staff"),
    GREAT_SWORD("image/weapon/great_sword.gif", null, "Great sword"),
    BANE_SWORD(null, null, "Bane sword"),
    BROAD_SWORD("image/weapon/broadsword.gif", null, "Board sword"),
    DEVIL_SWORD("image/weapon/flame_sword.gif", null, "Devil sword"),
    TRUESTEEL_SWORD(null, null, "Truesteel sword"),
    MORNING_STAR("image/weapon/morning_star.gif", null, "Morning star"),
    GREAT_AXE("image/weapon/great_axe.gif", null, "Great axe"),
    THRUSTING_SWORD(null, null, "Thrusting sword"),
    LIVING_SWORD(null, null, "Living sword"),
    SHORT_SWORD("image/weapon/short_sword.gif", null, "Short sword"),
    AXE("image/weapon/axe.gif", null, "Axe"),
    MACE("image/weapon/mace.gif", null, "Mace"),

    // Armor
    SUIT_OF_ARMOR("image/armor/suitofarmor.gif", null, "Suit of armor"),
    BREASTPLATE("image/armor/breastplate.gif", null, "Breastplate"),
    HELMET("image/armor/cap.gif", null, "Helmet"),
    SHIELD("image/armor/buckler.gif", null, "Shield"),
    TREMENDOUS_ARMOR("image/armor/breastplate.gif", null, "Tremendous armor"),
    SILVER_BREASTPLATE("image/armor/cuirass.gif", null, "Silver breastplate"),
    GOLD_HELMET("image/armor/helmet.gif", null, "Gold helmet"),
    JADE_SHIELD("image/armor/shield.gif", null, "Jade shield"),
    NONE(null, null, "None"),

    // Items
    CLOVEN_HOOF(null, null, "Cloven Hoof"),
    DEFT_GLOVES(null, null, "Deft Gloves"),
    LUCKY_CHARM(null, null, "Lucky Charm"),
    MAP_OF_LOST_CITY(null, null, "Map of Lost City"),
    MAP_OF_LOST_CASTLE(null, null, "Map of Lost Castle"),
    MAP_OF_RUINS(null, null, "Map of Ruins"),
    SHOES_OF_STEALTH(null, null, "Shoes of Stealth"),
    CRYSTAL_BALL(null, null, "Crystal Ball"),
    ANCIENT_TELESCOPE(null, null, "Ancient Telescope"),
    CLOAK_OF_MIST(null, null, "Cloak of Mist"),
    MAGIC_SPECTACLES(null, null, "Magic Spectacles"),
    REGENT_OF_JEWELS(null, null, "Regent of Jewels"),
    ROYAL_SCEPTRE(null, null, "Royal Sceptre"),
    LEAGUE_BOOTS(null, null, "League Boots"),
    SHIELDED_LANTERN(null, null, "Shielded Lantern"),
    DRAGON_ESSENCE(null, null, "Dragon Essence"),
    FLOWERS_OF_REST(null, null, "Flowers of Rest"),
    POULTICE_OF_HEALTH(null, null, "Poultice of Health"),
    TIMELESS_JEWEL(null, null, "Timeless Jewel"),
    GOLD(null, null, "Extra Gold"),

    // HACK for BERSERKER alert phase
    BERSERKER_BERSERK(null, null, "Play berserk chit!");

    private final String activeImageFilePath;

    private final String inactiveImageFilePath;

    private final String itemName;

    ItemInformation(final String activeImageFilePath, final String inactiveImageFilePath, final String itemName) {
        this.activeImageFilePath = activeImageFilePath;
        this.inactiveImageFilePath = inactiveImageFilePath;
        this.itemName = itemName;
    }

    public String getActivePath() {
        return this.activeImageFilePath;
    }

    public String getInactivePath() {
        return this.inactiveImageFilePath;
    }

    public String getItemName() {
        return itemName;
    }
}
