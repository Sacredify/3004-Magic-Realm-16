import ca.carleton.magicrealm.GUI.board.BoardModel;
import ca.carleton.magicrealm.GUI.board.ChitBuilder;
import ca.carleton.magicrealm.GUI.phaseselector.detailwindows.SearchSelectionPanel;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.GUI.tile.Discoverable;
import ca.carleton.magicrealm.GUI.tile.Path;
import ca.carleton.magicrealm.control.Daylight;
import ca.carleton.magicrealm.entity.character.CharacterFactory;
import ca.carleton.magicrealm.entity.character.CharacterType;
import ca.carleton.magicrealm.entity.chit.GoldChit;
import ca.carleton.magicrealm.entity.monster.Spider;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.impl.SearchPhase;
import ca.carleton.magicrealm.item.ItemInformation;
import ca.carleton.magicrealm.item.treasure.Treasure;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.either;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by Tony on 02/04/2015.
 */
public class SearchPhaseTest {

    @Test
    public void canSearchLocate() {
        final BoardModel boardModel = new BoardModel();
        ChitBuilder.placeChits(boardModel);
        final Player player = new Player();
        player.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));

        boardModel.getStartingLocation().addEntity(player.getCharacter());

        final List<AbstractPhase> phases = new ArrayList<AbstractPhase>();
        final SearchPhase searchPhase = new SearchPhase();

        Clearing clearing = boardModel.getStartingLocation();

        GoldChit goldChit = new GoldChit(Integer.parseInt(clearing.getName()), "test site");
        clearing.getParentTile().addChit(goldChit);

        Path path = boardModel.getStartingLocation().getAdjacentPaths().get(0);
        path.setHidden(true);

        searchPhase.setAction(SearchSelectionPanel.LOCATE_TEXT);

        phases.add(searchPhase);

        Daylight.doDaylight(boardModel, player, phases);

        if (searchPhase.getRoll() == 2 || searchPhase.getRoll() == 3) {
            assertThat(player.getDiscoveredThings().get(0), is(path));
        }
        else if (searchPhase.getRoll() == 4) {
            assertThat(player.getDiscoveredThings().get(0), is(goldChit));
        }
        else if (searchPhase.getRoll() == 1) {
            assertThat(player.getDiscoveredThings().get(0), either(is((Discoverable)path)).or(is(goldChit)));
            assertThat(player.getDiscoveredThings().get(1), either(is((Discoverable)path)).or(is(goldChit)));
        }
        else {
            assertThat(player.getDiscoveredThings(), is(Collections.<Discoverable>emptyList()));
        }
    }

    @Test
    public void canSearchPeer() {
        final BoardModel boardModel = new BoardModel();
        ChitBuilder.placeChits(boardModel);
        final Player player = new Player();
        player.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));

        boardModel.getStartingLocation().addEntity(player.getCharacter());

        final List<AbstractPhase> phases = new ArrayList<AbstractPhase>();
        final SearchPhase searchPhase = new SearchPhase();

        Clearing clearing = boardModel.getStartingLocation();

        Path path = boardModel.getStartingLocation().getAdjacentPaths().get(0);
        path.setHidden(true);

        Spider spider = new Spider();
        spider.setHidden(true);

        clearing.addEntity(spider);

        searchPhase.setAction(SearchSelectionPanel.PEER_TEXT);

        phases.add(searchPhase);

        Daylight.doDaylight(boardModel, player, phases);

        if (searchPhase.getRoll() == 2) {
            assertThat(player.getDiscoveredThings().get(0), is(path));
        }
        else if (searchPhase.getRoll() == 4) {
            assertThat(player.getDiscoveredThings().get(0), is(spider));
        }
        else if (searchPhase.getRoll() == 1 || searchPhase.getRoll() == 3) {
            assertThat(player.getDiscoveredThings().get(0), either(is((Discoverable)path)).or(is(spider)));
            assertThat(player.getDiscoveredThings().get(1), either(is((Discoverable)path)).or(is(spider)));
        }
        else {
            assertThat(player.getDiscoveredThings(), is(Collections.<Discoverable>emptyList()));
        }
    }

    @Test
    public void cannotSearchLootWithoutSearchingFirst() {
        final BoardModel boardModel = new BoardModel();
        ChitBuilder.placeChits(boardModel);
        final Player player = new Player();
        player.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));

        boardModel.getStartingLocation().addEntity(player.getCharacter());

        final List<AbstractPhase> phases = new ArrayList<AbstractPhase>();
        final SearchPhase searchPhase = new SearchPhase();

        Clearing clearing = boardModel.getStartingLocation();

        GoldChit goldChit = new GoldChit(Integer.parseInt(clearing.getName()), "test site");
        clearing.getParentTile().addChit(goldChit);

        final Treasure treasure = new Treasure(ItemInformation.ANCIENT_TELESCOPE);

        for (int i = 0; i < 6; i++) {
            goldChit.addTreasure(treasure);
        }

        searchPhase.setAction(SearchSelectionPanel.LOOT_TEXT);

        phases.add(searchPhase);

        Daylight.doDaylight(boardModel, player, phases);

        assertThat(player.getCharacter().getItems().get(player.getCharacter().getItems().size()-1), is(not(treasure)));
    }

    @Test
    public void canSearchLootWithDiscoveredSite() {
        final BoardModel boardModel = new BoardModel();
        ChitBuilder.placeChits(boardModel);
        final Player player = new Player();
        player.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));

        boardModel.getStartingLocation().addEntity(player.getCharacter());

        Clearing clearing = boardModel.getStartingLocation();

        GoldChit goldChit = new GoldChit(Integer.parseInt(clearing.getName()), "test site");
        clearing.getParentTile().addChit(goldChit);

        final Treasure treasure = new Treasure(ItemInformation.ANCIENT_TELESCOPE);

        for (int i = 0; i < 6; i++) {
            goldChit.addTreasure(treasure);
        }

        player.getDiscoveredThings().add(goldChit);

        final List<AbstractPhase> phases = new ArrayList<AbstractPhase>();
        final SearchPhase lootPhase = new SearchPhase();

        lootPhase.setAction(SearchSelectionPanel.LOOT_TEXT);

        phases.add(lootPhase);

        Daylight.doDaylight(boardModel, player, phases);

        assertThat(player.getCharacter().getItems().get(player.getCharacter().getItems().size()-1), is(treasure));
    }
}
