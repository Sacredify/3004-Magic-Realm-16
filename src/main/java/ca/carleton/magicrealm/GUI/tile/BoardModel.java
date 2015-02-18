package ca.carleton.magicrealm.GUI;

import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.impl.AwfulValley;
import ca.carleton.magicrealm.GUI.tile.impl.BadValley;

import java.util.ArrayList;

/**
 * Created by Tony on 17/02/2015.
 */
public class BoardModel {

    private ArrayList<AbstractTile> board;

    //TODO: Remove once proper builder is made, replace with looping... something better than this
    public BoardModel() {
        // In the future, mark each tile's x and y grid coordinates when added
        board = new ArrayList<AbstractTile>();
        board.add(new AwfulValley());
        board.add(new BadValley());
        board.add(new BorderLand());
        board.add(new Cavern());
        board.add(new Caves());
        board.add(new Cliff());
        board.add(new Crag());
        board.add(new CrustValley());
        board.add(new DarkValley());
        board.add(new DeepWoods());
        board.add(new EvilValley());
        board.add(new HighPass());
        board.add(new Ledges());
        board.add(new LindenWoods()); 
        board.add(new MapleWoods);
        board.add(new Mountain());
        board.add(new NutWoods());
        board.add(new OakWoods());
        board.add(new PineWoods());
        board.add(new Ruins());
    }

    public ArrayList<ArrayList<AbstractTile>> getBoard() {
        return board;
    }

    public void setBoard(ArrayList<ArrayList<AbstractTile>> board) {
        this.board = board;
    }
}
