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
        //Awful Valley -0
        this.board.add(new AwfulValley());
        //Bad Valley -1
        this.board.add(new BadValley());
        //Border Land -2
        this.board.add(new BorderLand());
        //Cavern -3
        this.board.add(new Cavern());
        //Caves -4
        this.board.add(new Caves());
        //Cliff -5
        this.board.add(new Cliff());
        //Crag -6
        this.board.add(new Crag());
        //Crust Valley -7
        this.board.add(new CurstValley());
        //Dark Valley -8
        this.board.add(new DarkValley());
        //Deep Woods - 9 
        this.board.add(new DeepWoods());
        //Evil Valley -10
        this.board.add(new EvilValley());
        //High Pass -11
        this.board.add(new HighPass());
        //Ledges -12
        this.board.add(new Ledges());
        //Linden Woods -13
        this.board.add(new LindenWoods());
        //Maple Woods -14
        this.board.add(new MapleWoods());
        //Mountains -15
        this.board.add(new Mountain());
        //Nut Woods -16
        this.board.add(new NutWoods());
        //Oak Woods -17
        this.board.add(new OakWoods());
        //Pine Woods -18
        this.board.add(new PineWoods());
        //Ruins - 19
        this.board.add(new Ruins());


        //AwfulValley - 0
        this.board.get(0).clearings[3] = this.board.get(0).clearings[0].connectTo(this.board.get(0).clearings[3]);
        this.board.get(0).clearings[1] = this.board.get(0).clearings[2].connectTo(this.board.get(0).clearings[1]);
        this.board.get(16).clearings[1] = this.board.get(0).clearings[2].connectTo(this.board.get(16).clearings[1]);
        this.board.get(19).clearings[1] = this.board.get(0).clearings[0].connectTo(this.board.get(19).clearings[1]);
        this.board.get(13).clearings[2] = this.board.get(0).clearings[1].connectTo(this.board.get(13).clearings[2]);
        //Linden Woods - 13
        //2
        this.board.get(13).clearings[1] = this.board.get(13).clearings[0].connectTo(this.board.get(13).clearings[1]);
        //4
        this.board.get(19).clearings[1] = this.board.get(13).clearings[1].connectTo(this.board.get(19).clearings[1]);
        //NutWoods - 16
        //2
        this.board.get(16).clearings[1] = this.board.get(16).clearings[0].connectTo(this.board.get(16).clearings[1]);
        //4
        this.board.get(16).clearings[1] = this.board.get(19).clearings[0].connectTo(this.board.get(16).clearings[1]);
        //5
        this.board.get(16).clearings[3] = this.board.get(7).clearings[3].connectTo(this.board.get(16).clearings[3]);
        this.board.get(16).clearings[3] = this.board.get(14).clearings[0].connectTo(this.board.get(16).clearings[3]);
        //Crust Valley - 7
        //1
        this.board.get(7).clearings[3] = this.board.get(7).clearings[0].connectTo(this.board.get(7).clearings[3]);
        this.board.get(8).clearings[0] = this.board.get(7).clearings[0].connectTo(this.board.get(8).clearings[0]);
        //2
        this.board.get(7).clearings[1] = this.board.get(7).clearings[2].connectTo(this.board.get(7).clearings[1]);
        this.board.get(7).clearings[1] = this.board.get(9).clearings[1].connectTo(this.board.get(7).clearings[1]);
        //Dark Valley -8
        //1
        this.board.get(8).clearings[0] = this.board.get(8).clearings[3].connectTo(this.board.get(8).clearings[0]);
        //3
        this.board.get(8).clearings[1] = this.board.get(8).clearings[2].connectTo(this.board.get(8).clearings[1]);
        this.board.get(9).clearings[1] = this.board.get(8).clearings[2].connectTo(this.board.get(9).clearings[1]);
        //Deep Woods -9
        //1
        this.board.get(9).clearings[0] = this.board.get(17).clearings[1].connectTo(this.board.get(9).clearings[0]);
        this.board.get(9).clearings[0] = this.board.get(6).clearings[1].connectTo(this.board.get(9).clearings[0]);
        this.board.get(9).clearings[0] = this.board.get(9).clearings[5].connectTo(this.board.get(9).clearings[0]);
        //2
        this.board.get(9).clearings[1] = this.board.get(6).clearings[2].connectTo(this.board.get(9).clearings[1]);
        //3
        this.board.get(9).clearings[2] = this.board.get(9).clearings[4].connectTo(this.board.get(9).clearings[2]);
        //4
        this.board.get(9).clearings[3] = this.board.get(9).clearings[4].connectTo(this.board.get(9).clearings[3]);
        this.board.get(9).clearings[3] = this.board.get(9).clearings[5].connectTo(this.board.get(9).clearings[3]);
        //5
        this.board.get(9).clearings[4] = this.board.get(14).clearings[3].connectTo(this.board.get(9).clearings[4]);
        //Crag-6
        //1
        this.board.get(6).clearings[0] = this.board.get(6).clearings[5].connectTo(this.board.get(6).clearings[0]);
        this.board.get(6).clearings[0] = this.board.get(6).clearings[3].connectTo(this.board.get(6).clearings[0]);
        //4
        this.board.get(6).clearings[3] = this.board.get(6).clearings[5].connectTo(this.board.get(6).clearings[3]);
        //3
        this.board.get(6).clearings[2] = this.board.get(6).clearings[5].connectTo(this.board.get(6).clearings[2]);
        this.board.get(6).clearings[2] = this.board.get(6).clearings[1].connectTo(this.board.get(6).clearings[2]);
        this.board.get(6).clearings[2] = this.board.get(6).clearings[4].connectTo(this.board.get(6).clearings[2]);
        //5
        this.board.get(6).clearings[4] = this.board.get(6).clearings[1].connectTo(this.board.get(6).clearings[4]);
        //Ruins -19
        //1
        this.board.get(19).clearings[0] = this.board.get(19).clearings[1].connectTo(this.board.get(19).clearings[0]);
        this.board.get(19).clearings[0] = this.board.get(19).clearings[4].connectTo(this.board.get(19).clearings[0]);
        this.board.get(19).clearings[0] = this.board.get(19).clearings[3].connectTo(this.board.get(19).clearings[0]);
        //6
        this.board.get(19).clearings[5] = this.board.get(19).clearings[3].connectTo(this.board.get(19).clearings[5]);
        this.board.get(19).clearings[5] = this.board.get(19).clearings[2].connectTo(this.board.get(19).clearings[5]);
        this.board.get(19).clearings[5] = this.board.get(4).clearings[6].connectTo(this.board.get(19).clearings[5]);
        //5
        this.board.get(19).clearings[4] = this.board.get(19).clearings[2].connectTo(this.board.get(19).clearings[4]);
        this.board.get(19).clearings[4] = this.board.get(14).clearings[0].connectTo(this.board.get(19).clearings[4]);
        //Maple Woods 14
        //4
        this.board.get(14).clearings[1] = this.board.get(14).clearings[0].connectTo(this.board.get(14).clearings[1]);
        this.board.get(14).clearings[1] = this.board.get(4).clearings[4].connectTo(this.board.get(14).clearings[1]);
        //5
        this.board.get(14).clearings[2] = this.board.get(17).clearings[2].connectTo(this.board.get(14).clearings[2]);
        //Oak Woods -17
        //5
        this.board.get(17).clearings[2] = this.board.get(1).clearings[0].connectTo(this.board.get(17).clearings[2]);
        //2
        this.board.get(17).clearings[0] = this.board.get(12).clearings[4].connectTo(this.board.get(17).clearings[0]);
        this.board.get(17).clearings[0] = this.board.get(2).clearings[1].connectTo(this.board.get(17).clearings[0]);
        this.board.get(17).clearings[0] = this.board.get(17).clearings[1].connectTo(this.board.get(17).clearings[0]);

    }

    public ArrayList<AbstractTile> getBoard() {
        return this.board;
    }

    public void setBoard(ArrayList<AbstractTile> board) {
        this.board = board;
    }
}
