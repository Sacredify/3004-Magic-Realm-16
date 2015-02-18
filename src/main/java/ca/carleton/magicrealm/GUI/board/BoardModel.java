package ca.carleton.magicrealm.GUI.board;

import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.impl.*;

import java.util.ArrayList;

/**
 * Created by Tony on 17/02/2015.
 *
 * Model to represent the board's GUI counterpart
 */
public class BoardModel {

    private ArrayList<ArrayList<AbstractTile>> board = new ArrayList<>();

    //TODO: Remove once proper builder is made, replace with looping... something better than this
    public BoardModel() {
        // In the future, mark each tile's x and y grid coordinates when added

        ArrayList<AbstractTile> row1 = new ArrayList<>();

        row1.add(null);
        row1.add(new Cliff());

        board.add(row1);

        ArrayList<AbstractTile> row2 = new ArrayList<>();
        row2.add(new AwfulValley());

        board.add(row2);

        ArrayList<AbstractTile> row3 = new ArrayList<>();
        row3.add(new HighPass());
        row3.add(new Ledges());

        board.add(row3);

        ArrayList<AbstractTile> row4 = new ArrayList<>();
        row4.add(new BorderLand());
        row4.add(new Crag());

        board.add(row4);

        ArrayList<AbstractTile> row5 = new ArrayList<>();
        row5.add(new Cavern());
        row5.add(new OakWoods());
        row5.add(new DarkValley());

        board.add(row5);

        ArrayList<AbstractTile> row6 = new ArrayList<>();
        row6.add(new BadValley());
        row6.add(new DeepWoods());

        board.add(row6);

        ArrayList<AbstractTile> row7 = new ArrayList<>();
        row7.add(new Mountain());
        row7.add(new MapleWoods());
        row7.add(new CurstValley());

        board.add(row7);

        ArrayList<AbstractTile> row8 = new ArrayList<>();
        row8.add(new Caves());
        row8.add(new NutWoods());

        board.add(row8);

        ArrayList<AbstractTile> row9 = new ArrayList<>();
        row9.add(new PineWoods());
        row9.add(new Ruins());

        board.add(row9);

        ArrayList<AbstractTile> row10 = new ArrayList<>();
        row10.add(null);
        row10.add(new AwfulValley());

        board.add(row10);

        ArrayList<AbstractTile> row11 = new ArrayList<>();
        row11.add(null);
        row11.add(new LindenWoods());

        board.add(row11);
    }

    public ArrayList<ArrayList<AbstractTile>> getBoard() {
        return board;
    }

    public void setBoard(ArrayList<ArrayList<AbstractTile>> board) {
        this.board = board;
    }
}
