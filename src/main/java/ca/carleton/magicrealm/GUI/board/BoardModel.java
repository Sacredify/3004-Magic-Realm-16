package ca.carleton.magicrealm.GUI.board;

import ca.carleton.magicrealm.GUI.tile.AbstractTile;
import ca.carleton.magicrealm.GUI.tile.impl.AwfulValley;
import ca.carleton.magicrealm.GUI.tile.impl.BadValley;

import java.util.ArrayList;

/**
 * Created by Tony on 17/02/2015.
 */
public class BoardModel {

    private ArrayList<ArrayList<AbstractTile>> board = new ArrayList<>();

    //TODO: Remove once proper builder is made, replace with looping... something better than this
    public BoardModel() {
        // In the future, mark each tile's x and y grid coordinates when added

        ArrayList<AbstractTile> row1 = new ArrayList<>();

        row1.add(new AwfulValley());
        row1.add(new AwfulValley());

        board.add(row1);

        ArrayList<AbstractTile> row2 = new ArrayList<>();
        row2.add(new AwfulValley());

        board.add(row2);

        ArrayList<AbstractTile> row3 = new ArrayList<>();
        row3.add(new AwfulValley());

        board.add(row3);
    	
        
    }

    public ArrayList<ArrayList<AbstractTile>> getBoard() {
        return board;
    }

    public void setBoard(ArrayList<ArrayList<AbstractTile>> board) {
        this.board = board;
    }
}
