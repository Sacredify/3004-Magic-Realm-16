package ca.carleton.magicrealm.GUI.board;

import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.GUI.tile.TileType;
import ca.carleton.magicrealm.entity.chit.*;
import ca.carleton.magicrealm.entity.monster.Ghost;
import ca.carleton.magicrealm.item.treasure.TreasureCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * Date: 21/02/15
 * Time: 12:39 PM
 */
public class ChitBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(ChitBuilder.class);

    private static final Random random = new Random();

    private static final List<YellowChit> valleyWarningChits = new ArrayList<YellowChit>();

    private static final List<YellowChit> woodsWarningChits = new ArrayList<YellowChit>();

    private static final List<YellowChit> caveWarningChits = new ArrayList<YellowChit>();

    private static final List<YellowChit> mountainWarningChits = new ArrayList<YellowChit>();

    private static final List<RedChit> soundChits = new ArrayList<RedChit>();

    private static final List<GoldChit> treasureChits = new ArrayList<GoldChit>();

    private static LostCity lostCityChit;

    private static LostCastle lostCastleChit;

    /**
     * Test runner.
     */
    public static void main(String[] args) {
        ChitBuilder.placeChits(new BoardGUIModel());
    }

    /**
     * Places the chits on the given board.
     *
     * @param board the board.
     */
    public static void placeChits(final BoardGUIModel board) {

        buildWarningChits();
        buildTreasureChits();
        buildSoundChits();
        buildLostCityAndCastle();

        LOG.info("Starting chit placement.");
        LOG.info("Starting warning chit placement.");

        // Step 1. Place warning chits of the correct type on the correct tile types [20 total].
        for (final AbstractTile tile : board.getTilesOfType(TileType.VALLEY)) {
            tile.addChit(valleyWarningChits.remove(random.nextInt(valleyWarningChits.size())));
        }
        for (final AbstractTile tile : board.getTilesOfType(TileType.MOUNTAIN)) {
            tile.addChit(mountainWarningChits.remove(random.nextInt(mountainWarningChits.size())));
        }
        for (final AbstractTile tile : board.getTilesOfType(TileType.CAVE)) {
            tile.addChit(caveWarningChits.remove(random.nextInt(caveWarningChits.size())));
        }
        for (final AbstractTile tile : board.getTilesOfType(TileType.WOODS)) {
            tile.addChit(woodsWarningChits.remove(random.nextInt(woodsWarningChits.size())));
        }

        LOG.info("Starting lost city/castle + 8 treasure/sound chit placement.");

        // Step 2. Place lost city and lost castle, as well as 4 site/sound chits [8 total + (2 * 5) = 18 total]
        // Lost city + 4 goes in caves. Lost castle + 4 goes in mountain.
        final List<ColoredChit> remainingChits = new ArrayList<ColoredChit>();

        TreasureCollection t = new TreasureCollection();
        for (int i = 0; i < t.treasures.length; i++) {
            treasureChits.get(random.nextInt(treasureChits.size())).addTreasure(t.treasures[i]);
        }
        remainingChits.addAll(treasureChits);

        treasureChits.clear();
        remainingChits.addAll(soundChits);
        soundChits.clear();
        Collections.shuffle(remainingChits);

        final List<ColoredChit> cavesChits = new ArrayList<ColoredChit>();
        cavesChits.addAll(remainingChits.subList(0, 4));
        cavesChits.add(lostCityChit);

        for (final AbstractTile tile : board.getTilesOfType(TileType.CAVE)) {
            tile.addChit(cavesChits.remove(random.nextInt(cavesChits.size())));
        }

        final List<ColoredChit> mountainsChits = new ArrayList<ColoredChit>();
        mountainsChits.addAll(remainingChits.subList(4, 8));
        mountainsChits.add(lostCastleChit);

        for (final AbstractTile tile : board.getTilesOfType(TileType.MOUNTAIN)) {
            tile.addChit(mountainsChits.remove(random.nextInt(mountainsChits.size())));
        }

        LOG.info("Starting valley chit replacement.");

        for (final AbstractTile tile : board.getTilesOfType(TileType.VALLEY)) {
            final Iterator<ColoredChit> iter = tile.getChits().iterator();
            while (iter.hasNext()) {
                final ColoredChit chit = iter.next();
                switch (chit.getDescription()) {
                    case "BONES":
                        final Clearing startForGhosts = tile.getClearings()[tile.getClearings().length - 1];
                        final Ghost ghost1 = new Ghost();
                        ghost1.setStartingClearing(startForGhosts);
                        final Ghost ghost2 = new Ghost();
                        ghost2.setStartingClearing(startForGhosts);
                        startForGhosts.addEntity(ghost1);
                        startForGhosts.addEntity(ghost2);
                        break;
                    case "DANK":
                        tile.getClearings()[tile.getClearings().length - 1].setDwelling(Dwelling.CHAPEL);
                        break;
                    case "RUINS":
                        tile.getClearings()[tile.getClearings().length - 1].setDwelling(Dwelling.GUARD);
                        break;
                    case "SMOKE":
                        tile.getClearings()[tile.getClearings().length - 1].setDwelling(Dwelling.HOUSE);
                        break;
                    case "STINK":
                        tile.getClearings()[tile.getClearings().length - 1].setDwelling(Dwelling.INN);
                        break;
                }
                iter.remove();
            }
        }

        LOG.info("Done with valley chit placement.");

        LOG.info("Chit placement finished.");
        LOG.info("Beginning sanity checks.");
        assertThat(treasureChits.size(), is(0));
        assertThat(soundChits.size(), is(0));
        assertThat(woodsWarningChits.size(), is(0));
        assertThat(valleyWarningChits.size(), is(0));
        assertThat(mountainWarningChits.size(), is(0));
        assertThat(caveWarningChits.size(), is(0));
        assertThat(cavesChits.size(), is(0));
        assertThat(mountainsChits.size(), is(0));
        assertThat(lostCityChit.treasureChits.size() + lostCityChit.soundChits.size(), is(5));
        assertThat(lostCastleChit.treasureChits.size() + lostCastleChit.soundChits.size(), is(5));
        LOG.info("Sanity checks complete. All chits assigned.");
    }

    private static void buildWarningChits() {
        valleyWarningChits.add(new YellowChit("SMOKE", TileType.VALLEY));
        valleyWarningChits.add(new YellowChit("RUINS", TileType.VALLEY));
        valleyWarningChits.add(new YellowChit("DANK", TileType.VALLEY));
        valleyWarningChits.add(new YellowChit("BONES", TileType.VALLEY));
        valleyWarningChits.add(new YellowChit("STINK", TileType.VALLEY));
        Collections.shuffle(valleyWarningChits);

        mountainWarningChits.add(new YellowChit("SMOKE", TileType.MOUNTAIN));
        mountainWarningChits.add(new YellowChit("RUINS", TileType.MOUNTAIN));
        mountainWarningChits.add(new YellowChit("DANK", TileType.MOUNTAIN));
        mountainWarningChits.add(new YellowChit("BONES", TileType.MOUNTAIN));
        mountainWarningChits.add(new YellowChit("STINK", TileType.MOUNTAIN));
        Collections.shuffle(mountainWarningChits);

        caveWarningChits.add(new YellowChit("SMOKE", TileType.CAVE));
        caveWarningChits.add(new YellowChit("RUINS", TileType.CAVE));
        caveWarningChits.add(new YellowChit("DANK", TileType.CAVE));
        caveWarningChits.add(new YellowChit("BONES", TileType.CAVE));
        caveWarningChits.add(new YellowChit("STINK", TileType.CAVE));
        Collections.shuffle(caveWarningChits);

        woodsWarningChits.add(new YellowChit("SMOKE", TileType.WOODS));
        woodsWarningChits.add(new YellowChit("RUINS", TileType.WOODS));
        woodsWarningChits.add(new YellowChit("DANK", TileType.WOODS));
        woodsWarningChits.add(new YellowChit("BONES", TileType.WOODS));
        woodsWarningChits.add(new YellowChit("STINK", TileType.WOODS));
        Collections.shuffle(woodsWarningChits);

        LOG.info("Built and shuffled warning chits.");
    }

    private static void buildSoundChits() {
        soundChits.add(new RedChit("HOWL", 4));
        soundChits.add(new RedChit("SLITHER", 3));
        soundChits.add(new RedChit("ROAR", 6));
        soundChits.add(new RedChit("PATTER", 2));
        soundChits.add(new RedChit("FLUTTER", 1));
        soundChits.add(new RedChit("SLITHER", 6));
        soundChits.add(new RedChit("ROAR", 4));
        soundChits.add(new RedChit("PATTER", 5));
        soundChits.add(new RedChit("SLITHER", 6));
        soundChits.add(new RedChit("FLUTTER", 2));
        Collections.shuffle(soundChits);

        LOG.info("Built and shuffled sound chits.");
    }

    private static void buildTreasureChits() {
        treasureChits.add(new GoldChit(2, "STATUE"));
        treasureChits.add(new GoldChit(6, "HOARD"));
        treasureChits.add(new GoldChit(1, "ALTAR"));
        treasureChits.add(new GoldChit(3, "LAIR"));
        treasureChits.add(new GoldChit(3, "VAULT"));
        treasureChits.add(new GoldChit(5, "CAIRNS"));
        treasureChits.add(new GoldChit(6, "POOL"));
        treasureChits.add(new GoldChit(4, "SHRINE"));
        Collections.shuffle(treasureChits);

        LOG.info("Built and shuffled treasure chits.");
    }

    /**
     * Builds the lost city and castle. Ensure this is called AFTER building other types of chits.
     */
    private static void buildLostCityAndCastle() {

        // Mix the 18 sound/treasure chits. Pick 5 at random for each.
        List<ColoredChit> mixedChits = new ArrayList<ColoredChit>();
        mixedChits.addAll(soundChits);
        mixedChits.addAll(treasureChits);
        Collections.shuffle(mixedChits);

        lostCityChit = new LostCity(3, "LOST CITY");

        // Add 5 to the lost city.
        for (int i = 0; i < 5; i++) {
            final ColoredChit takenChit = mixedChits.remove(random.nextInt(mixedChits.size()));
            if (takenChit.getChitColor() == ChitColor.GOLD) {
                lostCityChit.treasureChits.add((GoldChit) takenChit);
                treasureChits.remove(takenChit);
            } else {
                lostCityChit.soundChits.add((RedChit) takenChit);
                soundChits.remove(takenChit);
            }
        }

        LOG.info("Built lost city.");

        lostCastleChit = new LostCastle(1, "LOST CASTLE");

        // Add the remainder to the lost castle.
        for (int i = 0; i < 5; i++) {
            final ColoredChit takenChit = mixedChits.remove(random.nextInt(mixedChits.size()));
            if (takenChit.getChitColor() == ChitColor.GOLD) {
                lostCastleChit.treasureChits.add((GoldChit) takenChit);
                treasureChits.remove(takenChit);
            } else {
                lostCastleChit.soundChits.add((RedChit) takenChit);
                soundChits.remove(takenChit);
            }
        }

        LOG.info("Built lost castle.");

    }
}
