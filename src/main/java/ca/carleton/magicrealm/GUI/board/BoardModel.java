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
        row1.get(1).setAngle(240);

        board.add(row1);

        ArrayList<AbstractTile> row2 = new ArrayList<>();
        row2.add(new AwfulValley()); // change to evilvalley
        row2.get(0).setAngle(120);

        board.add(row2);

        ArrayList<AbstractTile> row3 = new ArrayList<>();
        row3.add(new HighPass());
        row3.get(0).setAngle(120);
        row3.add(new Ledges());
        row3.get(1).setAngle(240);

        board.add(row3);

        ArrayList<AbstractTile> row4 = new ArrayList<>();
        row4.add(new BorderLand());
        row4.get(0).setAngle(300);
        row4.add(new Crag());
        row4.get(1).setAngle(180);

        board.add(row4);

        ArrayList<AbstractTile> row5 = new ArrayList<>();
        row5.add(new Cavern());
        row5.get(0).setAngle(60);
        row5.add(new OakWoods());
        row5.get(1).setAngle(240);
        row5.add(new DarkValley());
        row5.get(2).setAngle(120);

        board.add(row5);

        ArrayList<AbstractTile> row6 = new ArrayList<>();
        row6.add(new BadValley());
        row6.get(0).setAngle(300);
        row6.add(new DeepWoods());
        row6.get(1).setAngle(0);

        board.add(row6);

        ArrayList<AbstractTile> row7 = new ArrayList<>();
        row7.add(new Mountain());
        row7.get(0).setAngle(300);
        row7.add(new MapleWoods());
        row7.get(1).setAngle(120);
        row7.add(new CurstValley());
        row7.get(2).setAngle(0);

        board.add(row7);

        ArrayList<AbstractTile> row8 = new ArrayList<>();
        row8.add(new Caves());
        row8.get(0).setAngle(120);
        row8.add(new NutWoods());
        row8.get(1).setAngle(180);

        board.add(row8);

        ArrayList<AbstractTile> row9 = new ArrayList<>();
        row9.add(new PineWoods());
        row9.get(0).setAngle(0);
        row9.add(new Ruins());
        row9.get(1).setAngle(120);

        board.add(row9);

        ArrayList<AbstractTile> row10 = new ArrayList<>();
        row10.add(null);
        row10.add(new AwfulValley());
        row10.get(1).setAngle(0);

        board.add(row10);

        ArrayList<AbstractTile> row11 = new ArrayList<>();
        row11.add(null);
        row11.add(new LindenWoods());
        row11.get(1).setAngle(60);

        board.add(row11);
    }

    public ArrayList<ArrayList<AbstractTile>> getBoard() {
        return board;
    }

    public void setBoard(ArrayList<ArrayList<AbstractTile>> board) {
        this.board = board;
    }
}
