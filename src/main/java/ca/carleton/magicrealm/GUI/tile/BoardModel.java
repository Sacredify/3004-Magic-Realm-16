package ca.carleton.magicrealm.GUI.tile;

import ca.carleton.magicrealm.GUI.tile.impl.*;

import java.util.ArrayList;

/**
 * Created by Tony on 17/02/2015.
 */
public class BoardModel {

    private ArrayList<AbstractTile> board;

    //TODO: Remove once proper builder is made, replace with looping... something better than this
    public BoardModel() {
        // In the future, mark each tile's x and y grid coordinates when added
        this.board = new ArrayList<AbstractTile>();
        this.board.add(new AwfulValley());
        this.board.add(new BadValley());
        this.board.add(new BorderLand());
        this.board.add(new Cavern());
        this.board.add(new Caves());
        this.board.add(new Cliff());
        this.board.add(new Crag());
        this.board.add(new CurstValley());
        this.board.add(new DarkValley());
        this.board.add(new DeepWoods());
        this.board.add(new EvilValley());
        this.board.add(new HighPass());
        this.board.add(new Ledges());
        this.board.add(new LindenWoods());
        this.board.add(new MapleWoods());
        this.board.add(new Mountain());
        this.board.add(new NutWoods());
        this.board.add(new OakWoods());
        this.board.add(new PineWoods());
        this.board.add(new Ruins());
    }

    public ArrayList<AbstractTile> getBoard() {
        return this.board;
    }

    public void setBoard(ArrayList<AbstractTile> board) {
        this.board = board;
    }
}
