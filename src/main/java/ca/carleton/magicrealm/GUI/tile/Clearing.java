package ca.carleton.magicrealm.GUI.tile;

import ca.carleton.magicrealm.item.Item;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Date: 16/02/15
 * Time: 7:54 PM
 */



public class Clearing {

    private int x;

    private int y;

    private List<Item> items;

    //private Dwelling dwelling;

    private AbstractTile parentTile;

    private List<Clearing> adjacentClearings;

    public Clearing(final AbstractTile parentTile) {
        this.parentTile = parentTile;
    }
    
    //Initializes a clearings array with pTile as the parentTile for all of the members

    /**
     * Connects the clearing parameter .
     * 
     * @param clearing is the clearing to add.
     * returns clearing that was passed in as parameter 
     * ,but with self added to the clearings possible paths list
     */
    
    public Clearing connectTo(Clearing clearing){
    	adjacentClearings.add(clearing);
    	clearing.adjacentClearings.add(clearing);
    	return clearing;
    }
    
    
    
    /**
     * Add an item to this clearing.
     *
     * @param item the item to add.
     */
    public void addItem(final Item item) {
        this.items.add(item);
    }

    /**
     * Add a clearing that is adjacent to this one.
     *
     * @param clearing the clearing to add.
     */
    public void addAdjacentClearing(final Clearing clearing) {
        this.adjacentClearings.add(clearing);
    }

    /**
     * Return the list of adjacent clearings.
     *
     * @return the adjacent clearings one may move to.
     */
    public List<Clearing> getAdjacentClearings() {
        return this.adjacentClearings;
    }

    /**
     * The list of items on this clearing.
     *
     * @return the list of items.
     */
    public List<Item> getItems() {
        return this.items;
    }

    /**
     * Gets the tile this clearing is on.
     *
     * @return the parent tile.
     */
    public AbstractTile getParentTime() {
        return this.parentTile;
    }
  
    public static Clearing[] initializeClearingsArray(int size, AbstractTile pTile){
    	
    	Clearing[] placeHolder = new Clearing[size];
    	
    	for(int i = 0 ; i < size ; i++){
    		placeHolder[i] = new Clearing(pTile);
    	}
    	
    	return placeHolder;
    }
}

