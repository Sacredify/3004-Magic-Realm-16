package ca.carleton.magicrealm.GUI;

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
    	
        
    }

    public ArrayList<ArrayList<AbstractTile>> getBoard() {
        return board;
    }

    public void setBoard(ArrayList<ArrayList<AbstractTile>> board) {
        this.board = board;
    }
}
