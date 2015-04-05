package ca.carleton.magicrealm.game.phase;

import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.GUI.tile.TileType;
import ca.carleton.magicrealm.entity.EntityInformation;
import ca.carleton.magicrealm.entity.character.AbstractCharacter;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.item.ItemInformation;
import ca.carleton.magicrealm.item.treasure.PhaseTreasure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * Date: 01/04/2015
 * Time: 4:59 PM
 */
public class PhaseUtils {

    private static final Logger LOG = LoggerFactory.getLogger(PhaseUtils.class);

    /**
     * Return the bean with info on how many phases the user has.
     *
     * @param player the player.
     * @param board  the board.
     * @return the bean.
     */
    public static PhaseCountBean getNumberOfPhasesForPlayer(final Player player, final BoardModel board) {

        final PhaseCountBean count = new PhaseCountBean();

        // Everyone gets two as a default.
        int numberOfPhases = 2;

        LOG.info("Added base number of 2 phases.");

        // If the player isn't currently in a cave, and isn't a dwarf, they get their sunlight phase.
        if (board.getClearingForPlayer(player).getParentTile().getTileType() != TileType.CAVE &&
                player.getCharacter().getEntityInformation() != EntityInformation.CHARACTER_DWARF) {
            numberOfPhases += 2;
            LOG.info("Added 2 sunlight phases.");
        }    else {
            LOG.info("In a cave - skipping sunlight phases.");
        }

        LOG.info("Starting character and treasure modifications check...");

        // Begin checking for extra phases by treasures, or character traits.
        final AbstractCharacter character = player.getCharacter();

        // STAMINA - Amazon gets an extra MOVE phase.
        if (character.getEntityInformation() == EntityInformation.CHARACTER_AMAZON) {
            count.addExtraPhase(PhaseType.MOVE);
            LOG.info("Added extra move phase for amazon STAMINA.");
        }
        // ROBUST - Berserker gets an extra REST phase.
        if (character.getEntityInformation() == EntityInformation.CHARACTER_BERSERKER) {
            count.addExtraPhase(PhaseType.REST);
            LOG.info("Added extra rest phase for berserker ROBUST..");
        }
        // REPUTATION - Captain gets an extra phase if they are at a dwelling
        if (character.getEntityInformation() == EntityInformation.CHARACTER_CAPTAIN && board.getClearingForPlayer(player).getDwelling() != null) {
            numberOfPhases += 1;
            LOG.info("Added extra phase for captain's REPUTATION at a DWELLING..");
        }
        // ELUSIVENESS - Elf gets an extra HIDE phase.
        if (character.getEntityInformation() == EntityInformation.CHARACTER_ELF) {
            count.addExtraPhase(PhaseType.HIDE);
            LOG.info("Added extra hide phase for elf ELUSIVENESS.");
        }
        // HEALTH - White knight gets an extra REST phase.
        if (character.getEntityInformation() == EntityInformation.CHARACTER_WHITE_KNIGHT) {
            count.addExtraPhase(PhaseType.REST);
            LOG.info("Added extra move phase for white knight HEALTH.");
        }

        // Add extra phase treasures for specific ones. Filter out the ones with conditions and ones aren't specific to a phase (null phase).
        player.getCharacter().getItems().stream()
                .filter(item -> item instanceof PhaseTreasure)
                .filter(treasure -> treasure.getItemInformation() != ItemInformation.ANCIENT_TELESCOPE && treasure.getItemInformation() != ItemInformation.SHIELDED_LANTERN)
                .filter(treasure -> ((PhaseTreasure) treasure).getPhaseAffected() != null)
                .forEach(treasure ->
                {
                    count.addExtraPhase(((PhaseTreasure) treasure).getPhaseAffected());
                    LOG.info("Added extra {} for having treasure {}.", ((PhaseTreasure) treasure).getPhaseAffected(), treasure);
                });

        // Ancient telescope allows an extra search phase if in mountain clearing.
        if (hasItem(player, ItemInformation.ANCIENT_TELESCOPE)
                && board.getClearingForPlayer(player).getParentTile().getTileType() == TileType.MOUNTAIN) {
            count.addExtraPhase(PhaseType.SEARCH);
            LOG.info("Added extra search phase for ancient telescope.");
        }
        // Shielded lantern adds an extra phase if they are in a cave.
        if (hasItem(player, ItemInformation.SHIELDED_LANTERN)
                && board.getClearingForPlayer(player).getParentTile().getTileType() == TileType.CAVE) {
            numberOfPhases += 1;
            LOG.info("Added extra phase for shielded lantern.");
        }

        // ASSUMPTION: These have special effects, but we don't support them. Instead, just give the user another phase.
        if (hasItem(player, ItemInformation.ROYAL_SCEPTRE)) {
            numberOfPhases += 1;
            LOG.info("Added extra move phase for royal sceptre.");
        }
        if (hasItem(player, ItemInformation.FLOWERS_OF_REST)) {
            numberOfPhases += 1;
            LOG.info("Added extra move phase for flowers of rest.");
        }
        if (hasItem(player, ItemInformation.DRAGON_ESSENCE)) {
            numberOfPhases += 1;
            LOG.info("Added extra move phase for dragon essence.");
        }

        count.setNumberOfPhasesFOrDay(numberOfPhases);

        LOG.info("Final number of phases: {}. Extra phases: {}.", count.getNumberOfPhasesFOrDay(), count.getExtraPhases().size());

        return count;
    }

    private static boolean hasItem(final Player player, final ItemInformation itemInformation) {
        return player.getCharacter().hasItem(itemInformation);
    }

}
