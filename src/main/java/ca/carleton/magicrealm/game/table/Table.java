package ca.carleton.magicrealm.game.table;

import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.GUI.tile.TileType;
import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.entity.Relationship;
import ca.carleton.magicrealm.entity.character.*;
import ca.carleton.magicrealm.game.DiceRoller;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.combat.Harm;
import ca.carleton.magicrealm.item.ItemInformation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An attempt to separate dice rolling utility from game logic for rolling on the tables. Here we can check character special
 * values, as well as treasures that may modify these outcomes.
 * <p>
 * Created with IntelliJ IDEA.
 * Date: 16/03/15
 * Time: 5:14 PM
 */
public abstract class Table {

    private static final Logger LOG = LoggerFactory.getLogger(Table.class);

    public static class MeetingTable {

        /**
         * Rolls on the meeting table.
         * TODO Return meeting table results instead of the int (move logic to the meeting table).
         *
         * @param player   the player rolling.
         * @param location the location of the player.
         * @return the roll value.
         */
        public static int roll(final Player player, final Clearing location) {

            // If the character is the black knight or swordsman, they only roll once.
            if (player.getCharacter() instanceof BlackKnight || player.getCharacter() instanceof Swordsman) {
                LOG.info("Black knight/swordsman rolled once on the meeting table.");
                return DiceRoller.rollOnce();
            }

            // If the character is a dwarf and is in a cave, they only roll once.
            if (player.getCharacter() instanceof Dwarf && location.getParentTile().getTileType() == TileType.CAVE) {
                LOG.info("Dwarf was in a cave and rolled once on the meeting table.");
                return DiceRoller.rollOnce();
            }

            // If the character is a white knight, subtract 1.
            if (player.getCharacter() instanceof WhiteKnight) {
                LOG.info("White knight subtracted one from the meeting table.");
                return Math.max(DiceRoller.rollTwiceTakeHigher() - 1, 1);
            }

            LOG.info("Rolled twice on the meeting table.");
            // The default is to roll twice for everything else.
            return DiceRoller.rollTwiceTakeHigher();
        }

        /**
         * Gets the relationship + 1, since the player bought drinks.
         *
         * @param relationship the current relationship.
         * @return the next highest, if applicable.
         */
        public static Relationship getBoughtDrinksRelationship(final Relationship relationship) {
            LOG.info("Old relationship: {}.", relationship);
            final Relationship toReturn = relationship.getRelationshipAfterDrinksBought();
            LOG.info("New relationship (because of drinks): {}.", toReturn);
            return toReturn;
        }
    }

    public static class MissileTable {

        /**
         * Rolls on the missile table to modify ranged attacks.
         *
         * @param player       the player rolling
         * @param baseStrength the base harm of the weapon.
         */
        public static Harm roll(final Player player, final Harm baseStrength) {

            int roll;

            // Denizens can roll too, so we'll have to take that into account.
            if (player != null) {

                // ELF special, ARCHER. only roll once.
                if (player.getCharacter().getEntityInformation() == EntityInformation.CHARACTER_ELF) {
                    roll = DiceRoller.rollOnce();
                } else {
                    roll = DiceRoller.rollTwiceTakeHigher();
                }

                // Amazon, Black Knight, Captain special, AIM. Subtract one.
                if ((player.getCharacter() instanceof Amazon || player.getCharacter() instanceof BlackKnight
                        || player.getCharacter() instanceof Captain) && roll != 1) {
                    LOG.info("{}'s AIM ability used: reduced roll value by one.", player.getCharacter());
                    roll = roll - 1;
                }
            }  else {
                roll = DiceRoller.rollTwiceTakeHigher();
            }

            LOG.info("Rolled a {} on the roll table.", roll);

            switch (roll) {
                case 1:
                    switch (baseStrength) {
                        case NEGLIGIBLE:
                            return baseStrength;
                        case LIGHT:
                            return Harm.HEAVY;
                        case MEDIUM:
                            return Harm.TREMENDOUS;
                        case HEAVY:
                        case TREMENDOUS:
                            return Harm.LETHAL;
                        default:
                            return Harm.NEGLIGIBLE;
                    }
                case 2:
                    switch (baseStrength) {
                        case NEGLIGIBLE:
                            return baseStrength;
                        case LIGHT:
                            return Harm.MEDIUM;
                        case MEDIUM:
                            return Harm.HEAVY;
                        case HEAVY:
                            return Harm.TREMENDOUS;
                        case TREMENDOUS:
                            return Harm.LETHAL;
                        default:
                            return Harm.NEGLIGIBLE;
                    }
                case 3:
                    return baseStrength;
                case 4:
                    switch (baseStrength) {
                        case NEGLIGIBLE:
                        case LIGHT:
                            return baseStrength;
                        case MEDIUM:
                            return Harm.LIGHT;
                        case HEAVY:
                            return Harm.MEDIUM;
                        case TREMENDOUS:
                            return Harm.HEAVY;
                        default:
                            return Harm.NEGLIGIBLE;
                    }
                case 5:
                    switch (baseStrength) {
                        case NEGLIGIBLE:
                        case LIGHT:
                        case MEDIUM:
                            return baseStrength;
                        case HEAVY:
                            return Harm.LIGHT;
                        case TREMENDOUS:
                            return Harm.MEDIUM;
                        default:
                            return Harm.NEGLIGIBLE;
                    }
                case 6:
                    switch (baseStrength) {
                        case NEGLIGIBLE:
                        case LIGHT:
                        case MEDIUM:
                        case HEAVY:
                            return baseStrength;
                        case TREMENDOUS:
                            return Harm.LIGHT;
                        default:
                            return Harm.NEGLIGIBLE;
                    }
                default:
                    return baseStrength;
            }
        }
    }

    public static class LocateTable {

        /**
         * Rolls on the locate table to try and find hidden things.
         *
         * @param player   the player rolling.
         * @param location the location of the player.
         * @return the roll value.
         */
        public static int roll(final Player player, final Clearing location) {

            int roll;

            // If the character is a dwarf and in a cave, only roll once.
            if (player.getCharacter().getEntityInformation() == EntityInformation.CHARACTER_DWARF && location.getParentTile().getTileType() == TileType.CAVE) {
                LOG.info("Dwarf rolled on the locate table and only had to roll once.");
                roll = DiceRoller.rollOnce();
            } else {
                roll = DiceRoller.rollTwiceTakeHigher();
            }

            if (player.getCharacter().hasItem(ItemInformation.MAP_OF_LOST_CASTLE)) {
                LOG.info("Player has the map of the lost castle. Roll reduced by 1.");
                roll -= 1;
            }

            if (player.getCharacter().hasItem(ItemInformation.MAP_OF_LOST_CITY)) {
                LOG.info("Player has the map of the lost city. Roll reduced by 1.");
                roll -= 1;
            }

            if (player.getCharacter().hasItem(ItemInformation.MAP_OF_RUINS)) {
                LOG.info("Player has the map of the ruins. Roll reduced by 1.");
                roll -= 1;
            }

            return Math.max(roll, 1);
        }

    }

    public static class HideTable {

        /**
         * Rolls on the hide table to see if they are hidden or not.
         *
         * @param player the player.
         * @return the int result.
         */
        public static int roll(final Player player) {

            int roll;

            // If the character is a dwarf or if they have the shoes of stealth, only roll once.
            if (player.getCharacter().getEntityInformation() == EntityInformation.CHARACTER_DWARF
                    || player.getCharacter().hasItem(ItemInformation.SHOES_OF_STEALTH)) {
                LOG.info("Either the player is a dwarf or has the shoes of stealth and rolled on the hide table and only had to roll once.");
                roll = DiceRoller.rollOnce();
            } else {
                roll = DiceRoller.rollTwiceTakeHigher();
            }

            return roll;
        }

    }

    public static class LootTable {

        /**
         * Rolls on the loot table to see what loot they can get.
         *
         * @param player the player.
         * @return the roll value.
         */
        public static int roll(final Player player) {

            int roll;

            // If the character is a dwarf or if they have the shoes of stealth, only roll once.
            if (player.getCharacter().getEntityInformation() == EntityInformation.CHARACTER_DWARF
                    || player.getCharacter().hasItem(ItemInformation.DEFT_GLOVES)) {
                LOG.info("Either the player is a dwarf or has deft gloves and rolled on the loot table and only had to roll once.");
                roll = DiceRoller.rollOnce();
            } else {
                roll = DiceRoller.rollTwiceTakeHigher();
            }

            return roll;
        }
    }
}

