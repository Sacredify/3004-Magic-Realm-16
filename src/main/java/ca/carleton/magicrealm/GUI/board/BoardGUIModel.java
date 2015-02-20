package ca.carleton.magicrealm.GUI.board;

import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.Clearing;
import ca.carleton.magicrealm.GUI.tile.impl.*;

import java.util.ArrayList;

/**
 * Created by Tony on 17/02/2015.
 *
 * Model to represent the board's GUI counterpart
 */
public class BoardGUIModel {

    private ArrayList<ArrayList<AbstractTile>> board = new ArrayList<>();

    private ArrayList<AbstractTile> tiles = new ArrayList<AbstractTile>();

    public BoardGUIModel() {
        // In the future, mark each tile's x and y grid coordinates when added
        ArrayList<AbstractTile> row1 = new ArrayList<AbstractTile>();

        this.tiles.add(new Cliff());
        row1.add(null);
        row1.add(this.tiles.get(0));
        row1.get(1).setAngle(300);

        this.board.add(row1);

        this.tiles.add(new EvilValley());
        ArrayList<AbstractTile> row2 = new ArrayList<>();
        row2.add(this.tiles.get(1));
        row2.get(0).setAngle(120);

        this.board.add(row2);

        this.tiles.add(new HighPass());
        this.tiles.add(new Ledges());
        ArrayList<AbstractTile> row3 = new ArrayList<>();
        row3.add(this.tiles.get(2));
        row3.get(0).setAngle(120);
        row3.add(this.tiles.get(3));
        row3.get(1).setAngle(240);

        this.board.add(row3);

        this.tiles.add(new BorderLand());
        this.tiles.add(new Crag());
        ArrayList<AbstractTile> row4 = new ArrayList<>();
        row4.add(this.tiles.get(4));
        row4.get(0).setAngle(300);
        row4.add(this.tiles.get(5));
        row4.get(1).setAngle(180);

        this.board.add(row4);

        this.tiles.add(new Cavern());
        this.tiles.add(new OakWoods());
        this.tiles.add(new DarkValley());
        ArrayList<AbstractTile> row5 = new ArrayList<>();
        row5.add(this.tiles.get(6));
        row5.get(0).setAngle(60);
        row5.add(this.tiles.get(7));
        row5.get(1).setAngle(240);
        row5.add(this.tiles.get(8));
        row5.get(2).setAngle(120);

        this.board.add(row5);

        this.tiles.add(new BadValley());
        this.tiles.add(new DeepWoods());
        ArrayList<AbstractTile> row6 = new ArrayList<>();
        row6.add(this.tiles.get(9));
        row6.get(0).setAngle(300);
        row6.add(this.tiles.get(10));
        row6.get(1).setAngle(0);

        this.board.add(row6);

        this.tiles.add(new Mountain());
        this.tiles.add(new MapleWoods());
        this.tiles.add(new CurstValley());
        ArrayList<AbstractTile> row7 = new ArrayList<>();
        row7.add(this.tiles.get(11));
        row7.get(0).setAngle(300);
        row7.add(this.tiles.get(12));
        row7.get(1).setAngle(120);
        row7.add(this.tiles.get(13));
        row7.get(2).setAngle(0);

        this.board.add(row7);

        this.tiles.add(new Caves());
        this.tiles.add(new NutWoods());
        ArrayList<AbstractTile> row8 = new ArrayList<>();
        row8.add(this.tiles.get(14));
        row8.get(0).setAngle(120);
        row8.add(this.tiles.get(15));
        row8.get(1).setAngle(180);

        this.board.add(row8);

        this.tiles.add(new PineWoods());
        this.tiles.add(new Ruins());
        ArrayList<AbstractTile> row9 = new ArrayList<>();
        row9.add(this.tiles.get(16));
        row9.get(0).setAngle(0);
        row9.add(this.tiles.get(17));
        row9.get(1).setAngle(120);

        this.board.add(row9);

        this.tiles.add(new AwfulValley());
        ArrayList<AbstractTile> row10 = new ArrayList<>();
        row10.add(null);
        row10.add(this.tiles.get(18));
        row10.get(1).setAngle(0);

        this.board.add(row10);

        this.tiles.add(new LindenWoods());
        ArrayList<AbstractTile> row11 = new ArrayList<>();
        row11.add(null);
        row11.add(this.tiles.get(19));
        row11.get(1).setAngle(60);

        this.board.add(row11);

        this.setupConnections();
    }


    /**
     * Setup connects to the different clearings.
     */
    private void setupConnections() {

        Clearing[] cliff = this.tiles.get(0).getClearings();
        cliff[3].connectTo(cliff[5]);
        cliff[4].connectTo(cliff[2]);
        cliff[5].connectTo(cliff[2]);
        cliff[5].connectTo(cliff[0]);
        cliff[2].connectTo(cliff[1]);

        Clearing[] awful = this.tiles.get(1).getClearings();
        awful[0].connectTo(cliff[0]);
        awful[0].connectTo(awful[2]);
        awful[1].connectTo(awful[3]);

        Clearing[] high = this.tiles.get(2).getClearings();
        high[5].connectTo(awful[3]);
        high[5].connectTo(high[2]);
        high[4].connectTo(high[0]);
        high[0].connectTo(high[3]);
        high[3].connectTo(high[1]);

        Clearing[] ledge = this.tiles.get(3).getClearings();
        ledge[2].connectTo(cliff[1]);
        ledge[2].connectTo(ledge[5]);
        ledge[5].connectTo(ledge[0]);
        ledge[0].connectTo(ledge[2]);
        ledge[0].connectTo(ledge[3]);
        ledge[5].connectTo(ledge[3]);
        ledge[1].connectTo(awful[3]);
        ledge[1].connectTo(ledge[4]);

        Clearing[] borderland = this.tiles.get(4).getClearings();
        borderland[1].connectTo(awful[2]);
        borderland[1].connectTo(borderland[2]);
        borderland[2].connectTo(borderland[5]);
        borderland[2].connectTo(borderland[4]);
        borderland[5].connectTo(borderland[0]);
        borderland[5].connectTo(borderland[3]);
        borderland[0].connectTo(high[1]);
        borderland[3].connectTo(borderland[4]);
        borderland[3].connectTo(ledge[3]);

        Clearing[] crag = this.tiles.get(5).getClearings();
        crag[0].connectTo(crag[5]);
        crag[0].connectTo(crag[3]);
        crag[3].connectTo(crag[5]);
        crag[5].connectTo(crag[2]);
        crag[2].connectTo(crag[1]);
        crag[2].connectTo(crag[4]);
        crag[4].connectTo(crag[1]);

        Clearing[] cavern = this.tiles.get(6).getClearings();
        cavern[4].connectTo(high[2]);
        cavern[4].connectTo(cavern[3]);
        cavern[5].connectTo(cavern[2]);
        cavern[2].connectTo(cavern[5]);
        cavern[5].connectTo(cavern[3]);
        cavern[3].connectTo(cavern[0]);
        cavern[2].connectTo(cavern[1]);
        cavern[1].connectTo(borderland[4]);
        cavern[2].connectTo(cavern[0]);

        Clearing[] oakWoods = this.tiles.get(7).getClearings();
        oakWoods[0].connectTo(borderland[1]);
        oakWoods[0].connectTo(ledge[4]);
        oakWoods[0].connectTo(oakWoods[1]);

        Clearing[] darkValley = this.tiles.get(8).getClearings();
        darkValley[2].connectTo(darkValley[0]);
        darkValley[1].connectTo(darkValley[3]);

        Clearing[] badValley = this.tiles.get(9).getClearings();
        badValley[2].connectTo(cavern[0]);
        badValley[2].connectTo(badValley[0]);
        badValley[0].connectTo(oakWoods[2]);
        badValley[1].connectTo(badValley[3]);
        badValley[3].connectTo(borderland[0]);

        Clearing[] deepWoods = this.tiles.get(10).getClearings();
        deepWoods[0].connectTo(oakWoods[1]);
        deepWoods[0].connectTo(crag[1]);
        deepWoods[0].connectTo(deepWoods[3]);
        deepWoods[0].connectTo(deepWoods[5]);
        deepWoods[3].connectTo(deepWoods[5]);
        deepWoods[3].connectTo(deepWoods[4]);
        deepWoods[4].connectTo(deepWoods[2]);
        deepWoods[2].connectTo(deepWoods[5]);
        deepWoods[2].connectTo(deepWoods[1]);
        deepWoods[1].connectTo(darkValley[3]);

        Clearing[] mountain = this.tiles.get(11).getClearings();
        mountain[3].connectTo(mountain[5]);
        mountain[3].connectTo(mountain[1]);
        mountain[1].connectTo(mountain[4]);
        mountain[4].connectTo(mountain[5]);
        mountain[5].connectTo(mountain[2]);
        mountain[2].connectTo(mountain[0]);
        mountain[4].connectTo(badValley[2]);

        Clearing[] mapleWoods = this.tiles.get(12).getClearings();
        mapleWoods[2].connectTo(oakWoods[2]);
        mapleWoods[2].connectTo(deepWoods[4]);
        mapleWoods[1].connectTo(mapleWoods[0]);

        Clearing[] curstValley = this.tiles.get(13).getClearings();
        curstValley[0].connectTo(darkValley[0]);
        curstValley[0].connectTo(curstValley[2]);
        curstValley[1].connectTo(curstValley[3]);
        curstValley[1].connectTo(deepWoods[1]);

        Clearing[] caves = this.tiles.get(14).getClearings();
        caves[1].connectTo(badValley[1]);
        caves[1].connectTo(caves[2]);
        caves[2].connectTo(caves[4]);
        caves[4].connectTo(mapleWoods[1]);
        caves[1].connectTo(caves[3]);
        caves[3].connectTo(caves[5]);
        caves[5].connectTo(caves[0]);

        Clearing[] nutWoods = this.tiles.get(15).getClearings();
        nutWoods[2].connectTo(mapleWoods[0]);
        nutWoods[2].connectTo(curstValley[2]);
        nutWoods[1].connectTo(nutWoods[0]);

        //TODO finish the last 4 once i can see them..

    }

    public ArrayList<ArrayList<AbstractTile>> getBoard() {
        return this.board;
    }

    public void setBoard(ArrayList<ArrayList<AbstractTile>> board) {
        this.board = board;
    }
}
