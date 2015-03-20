package ca.carleton.magicrealm.GUI.board;

import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.GUI.tile.TileType;
import ca.carleton.magicrealm.entity.chit.*;
import ca.carleton.magicrealm.entity.monster.Ghost;
import ca.carleton.magicrealm.item.treasure.TreasureCollection;


import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Date: 21/02/15
 * Time: 12:39 PM
 */
public class ChitBuilder {


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


        // Step 2. Place lost city and lost castle, as well as 4 site/sound chits [8 total + (2 * 5) = 18 total]
        // Lost city + 4 goes in caves. Lost castle + 4 goes in mountain.
        final List<ColoredChit> remainingChits = new ArrayList<ColoredChit>();

        TreasureCollection t = new TreasureCollection();
        Random r = new Random();
        for(int i = 0 ; i < t.treasures.length; i++){
            treasureChits.get(r.nextInt(treasureChits.size())).addTreasure(t.treasures[i]);
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


        for (final AbstractTile tile : board.getTilesOfType(TileType.VALLEY)) {
            final Iterator<ColoredChit> iter = tile.getChits().iterator();
            while (iter.hasNext()) {
                final ColoredChit chit = iter.next();
                if (chit.getDescription().equals("BONES")) {
                    final Clearing startForGhosts = tile.getClearings()[tile.getClearings().length - 1];
                    final Ghost ghost1 = new Ghost();
                    ghost1.setStartingClearing(startForGhosts);
                    final Ghost ghost2 = new Ghost();
                    ghost2.setStartingClearing(startForGhosts);
                    startForGhosts.addEntity(ghost1);
                    startForGhosts.addEntity(ghost2);
                } else if (chit.getDescription().equals("DANK")) {
                     tile.getClearings()[tile.getClearings().length - 1].setDwelling(Dwelling.CHAPEL);
                } else if (chit.getDescription().equals("RUINS")) {
                    tile.getClearings()[tile.getClearings().length - 1].setDwelling(Dwelling.GUARD);
                } else if (chit.getDescription().equals("SMOKE")) {
                    tile.getClearings()[tile.getClearings().length - 1].setDwelling(Dwelling.HOUSE);
                } else if (chit.getDescription().equals("STINK")) {
                    tile.getClearings()[tile.getClearings().length - 1].setDwelling(Dwelling.INN);
                }
                iter.remove();
            }
        }


        assert (treasureChits.size() == 0);
        assert (soundChits.size() == 0);
        assert (woodsWarningChits.size() == 0);
        assert (valleyWarningChits.size() == 0);
        assert (mountainWarningChits.size() == 0);
        assert (caveWarningChits.size() == 0);
        assert (cavesChits.size() == 0);
        assert (mountainsChits.size() == 0);
        assert (lostCityChit.treasureChits.size() + lostCityChit.soundChits.size() == 5);
        assert (lostCastleChit.treasureChits.size() + lostCastleChit.soundChits.size() == 5);


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
                treasureChits.remove((GoldChit) takenChit);
            } else {
                lostCityChit.soundChits.add((RedChit) takenChit);
                soundChits.remove((RedChit) takenChit);
            }
        }

        lostCastleChit = new LostCastle(1, "LOST CASTLE");

        // Add the remainder to the lost castle.
        for (int i = 0; i < 5; i++) {
            final ColoredChit takenChit = mixedChits.remove(random.nextInt(mixedChits.size()));
            if (takenChit.getChitColor() == ChitColor.GOLD) {
                lostCastleChit.treasureChits.add((GoldChit) takenChit);
                treasureChits.remove((GoldChit) takenChit);
            } else {
                lostCastleChit.soundChits.add((RedChit) takenChit);
                soundChits.remove((RedChit) takenChit);
            }
        }

    }
}
