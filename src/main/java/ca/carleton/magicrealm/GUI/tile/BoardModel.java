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
        //Awful Valley -0
        board.add(new AwfulValley());
        //Bad Valley -1
        board.add(new BadValley());
        //Border Land -2
        board.add(new BorderLand());
        //Cavern -3
        board.add(new Cavern());
        //Caves -4
        board.add(new Caves());
        //Cliff -5
        board.add(new Cliff());
        //Crag -6
        board.add(new Crag());
        //Crust Valley -7
        board.add(new CrustValley());
        //Dark Valley -8
        board.add(new DarkValley());
        //Deep Woods - 9 
        board.add(new DeepWoods());
       //Evil Valley -10
        board.add(new EvilValley());
        //High Pass -11
        board.add(new HighPass());
        //Ledges -12
        board.add(new Ledges());
        //Linden Woods -13
        board.add(new LindenWoods()); 
        //Maple Woods -14
        board.add(new MapleWoods);
        //Mountains -15
        board.add(new Mountain());
        //Nut Woods -16
        board.add(new NutWoods());
        //Oak Woods -17
        board.add(new OakWoods());
        //Pine Woods -18
        board.add(new PineWoods());
        //Ruins - 19
        board.add(new Ruins());
        
        
        //AwfulValley - 0
        board.get(0).clearings[3] = board.get(0).clearings[0].connectTo(board.get(0).clearings[3]);
        board.get(0).clearings[1] = board.get(0).clearings[2].connectTo(board.get(0).clearings[1]);
        board.get(16).clearings[1] = board.get(0).clearings[2].connectTo(board.get(16).clearings[1]);
        board.get(19).clearings[1] = board.get(0).clearings[0].connectTo(board.get(19).clearings[1]);
        board.get(13).clearings[2] = board.get(0).clearings[1].connectTo(board.get(13).clearings[2]);
        //Linden Woods - 13
        //2
        board.get(13).clearings[1] = board.get(13).clearings[0].connectTo(board.get(13).clearings[1]);
        //4
        board.get(19).clearings[1] = board.get(13).clearings[1].connectTo(board.get(19).clearings[1]);
        //NutWoods - 16
        //2
        board.get(16).clearings[1] = board.get(16).clearings[0].connectTo(board.get(16).clearings[1]);
        //4
        board.get(16).clearings[1] = board.get(19).clearings[0].connectTo(board.get(16).clearings[1]);
        //5
        board.get(16).clearings[3] = board.get(7).clearings[3].connectTo(board.get(16).clearings[3]);
        board.get(16).clearings[3] = board.get(14).clearings[0].connectTo(board.get(16).clearings[3]);
        //Crust Valley - 7
        //1
        board.get(7).clearings[3] = board.get(7).clearings[0].connectTo(board.get(7).clearings[3]);
        board.get(8).clearings[0] = board.get(7).clearings[0].connectTo(board.get(8).clearings[0]);
        //2
        board.get(7).clearings[1] = board.get(7).clearings[2].connectTo(board.get(7).clearings[1]);
        board.get(7).clearings[1] = board.get(9).clearings[1].connectTo(board.get(7).clearings[1]);
        //Dark Valley -8
        //1
        board.get(8).clearings[0] = board.get(8).clearings[3].connectTo(board.get(8).clearings[0]);
        //3
        board.get(8).clearings[1] = board.get(8).clearings[2].connectTo(board.get(8).clearings[1]);
        board.get(9).clearings[1] = board.get(8).clearings[2].connectTo(board.get(9).clearings[1]);
        //Deep Woods -9
        //1
        board.get(9).clearings[0] = board.get(17).clearings[1].connectTo(board.get(9).clearings[0]);
        board.get(9).clearings[0] = board.get(6).clearings[1].connectTo(board.get(9).clearings[0]);
        board.get(9).clearings[0] = board.get(9).clearings[5].connectTo(board.get(9).clearings[0]);
        //2
        board.get(9).clearings[1] = board.get(6).clearings[2].connectTo(board.get(9).clearings[1]);
        //3
        board.get(9).clearings[2] = board.get(9).clearings[4].connectTo(board.get(9).clearings[2]);
        //4
        board.get(9).clearings[3] = board.get(9).clearings[4].connectTo(board.get(9).clearings[3]);
        board.get(9).clearings[3] = board.get(9).clearings[5].connectTo(board.get(9).clearings[3]);
        //5
        board.get(9).clearings[4] = board.get(14).clearings[3].connectTo(board.get(9).clearings[4]);
        //Crag-6
        //1
        board.get(6).clearings[0] = board.get(6).clearings[5].connectTo(board.get(6).clearings[0]);
        board.get(6).clearings[0] = board.get(6).clearings[3].connectTo(board.get(6).clearings[0]);
        //4
        board.get(6).clearings[3] = board.get(6).clearings[5].connectTo(board.get(6).clearings[3]);
        //3
        board.get(6).clearings[2] = board.get(6).clearings[5].connectTo(board.get(6).clearings[2]);
        board.get(6).clearings[2] = board.get(6).clearings[1].connectTo(board.get(6).clearings[2]);
        board.get(6).clearings[2] = board.get(6).clearings[4].connectTo(board.get(6).clearings[2]);
        //5
        board.get(6).clearings[4] = board.get(6).clearings[1].connectTo(board.get(6).clearings[4]);
        //Ruins -19
        //1
        board.get(19).clearings[0] = board.get(19).clearings[1].connectTo(board.get(19).clearings[0]);
        board.get(19).clearings[0] = board.get(19).clearings[4].connectTo(board.get(19).clearings[0]);
        board.get(19).clearings[0] = board.get(19).clearings[3].connectTo(board.get(19).clearings[0]);
        //6
        board.get(19).clearings[5] = board.get(19).clearings[3].connectTo(board.get(19).clearings[5]);
        board.get(19).clearings[5] = board.get(19).clearings[2].connectTo(board.get(19).clearings[5]);
        board.get(19).clearings[5] = board.get(4).clearings[6].connectTo(board.get(19).clearings[5]);
        //5
        board.get(19).clearings[4] = board.get(19).clearings[2].connectTo(board.get(19).clearings[4]);
        board.get(19).clearings[4] = board.get(14).clearings[0].connectTo(board.get(19).clearings[4]);
        //Maple Woods 14
        //4
        board.get(14).clearings[1] = board.get(14).clearings[0].connectTo(board.get(14).clearings[1]);
        board.get(14).clearings[1] = board.get(4).clearings[4].connectTo(board.get(14).clearings[1]);
        //5
        board.get(14).clearings[2] = board.get(17).clearings[2].connectTo(board.get(14).clearings[2]);
        //Oak Woods -17
        //5
        board.get(17).clearings[2] = board.get(1).clearings[0].connectTo(board.get(17).clearings[2]);
        //2
        board.get(17).clearings[0] = board.get(12).clearings[4].connectTo(board.get(17).clearings[0]);
        board.get(17).clearings[0] = board.get(2).clearings[1].connectTo(board.get(17).clearings[0]);
        board.get(17).clearings[0] = board.get(17).clearings[1].connectTo(board.get(17).clearings[0]);
        
        
    }

    public ArrayList<ArrayList<AbstractTile>> getBoard() {
        return board;
    }

    public void setBoard(ArrayList<ArrayList<AbstractTile>> board) {
        this.board = board;
    }
}
