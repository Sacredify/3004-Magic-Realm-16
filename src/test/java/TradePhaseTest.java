import ca.carleton.magicrealm.GUI.board.BoardGUIModel;
import ca.carleton.magicrealm.GUI.board.ChitBuilder;
import ca.carleton.magicrealm.control.Daylight;
import ca.carleton.magicrealm.entity.character.CharacterFactory;
import ca.carleton.magicrealm.entity.character.CharacterType;
import ca.carleton.magicrealm.entity.natives.NativeFaction;
import ca.carleton.magicrealm.entity.natives.NativeFactory;
import ca.carleton.magicrealm.entity.natives.NativeType;
import ca.carleton.magicrealm.game.Player;
import ca.carleton.magicrealm.game.phase.AbstractPhase;
import ca.carleton.magicrealm.game.phase.impl.TradePhase;
import ca.carleton.magicrealm.item.Item;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created with IntelliJ IDEA.
 * Date: 11/03/15
 * Time: 11:56 PM
 */
public class TradePhaseTest {

    @Test
    public void canSellWithNative() {

        // Create the board and player
        final BoardGUIModel boardGUIModel = new BoardGUIModel();
        ChitBuilder.placeChits(boardGUIModel);
        final Player player = new Player();
        player.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));

        // Set starting location.
        boardGUIModel.getStartingLocation().addEntity(player.getCharacter());

        final List<AbstractPhase> phases = new ArrayList<AbstractPhase>();
        final TradePhase tradePhase = new TradePhase();

        final Item itemSelling = player.getCharacter().getItems().get(0);
        final int itemsBeforeTrade = player.getCharacter().getItems().size();
        final int goldValueOfItem = itemSelling.getGoldValue();

        tradePhase.setDrinksBought(false);
        tradePhase.setSelling(true);
        tradePhase.setItemToTrade(itemSelling);
        tradePhase.setTradeTarget(NativeFactory.createNative(NativeFaction.LANCERS, NativeType.KNIGHT));
        tradePhase.override = 0;

        phases.add(tradePhase);

        Daylight.processPhasesForPlayer(boardGUIModel, player, phases);
        assertThat(player.getCharacter().getItems().size(), is(itemsBeforeTrade - 1));
        assertThat(player.getCharacter().getCurrentGold(), is(10 + goldValueOfItem));
        assertThat(tradePhase.getTradeTarget().getItems().size(), is(1));
    }

    @Test
    public void canBuyFromNative() {
        final BoardGUIModel boardGUIModel = new BoardGUIModel();
        ChitBuilder.placeChits(boardGUIModel);
        final Player player = new Player();
        player.setCharacter(CharacterFactory.createCharacter(CharacterType.AMAZON));

        // Set starting location.
        boardGUIModel.getStartingLocation().addEntity(player.getCharacter());

        final List<AbstractPhase> phases = new ArrayList<AbstractPhase>();
        final TradePhase tradePhase = new TradePhase();

        final Item itemSelling = player.getCharacter().getItems().get(0);
        final int itemsBeforeTrade = player.getCharacter().getItems().size();
        final int goldValueOfItem = itemSelling.getGoldValue();

        tradePhase.setDrinksBought(false);
        tradePhase.setSelling(false);
        tradePhase.setItemToTrade(itemSelling);
        tradePhase.setTradeTarget(NativeFactory.createNative(NativeFaction.LANCERS, NativeType.KNIGHT));
        tradePhase.override = 5; // price x 4

        phases.add(tradePhase);

        Daylight.processPhasesForPlayer(boardGUIModel, player, phases);
        assertThat(player.getCharacter().getItems().size(), is(itemsBeforeTrade + 1));
        assertThat(player.getCharacter().getCurrentGold(), is(10 - (goldValueOfItem * 4)));
        assertThat(tradePhase.getTradeTarget().getItems().size(), is(0));
    }
}
