package ca.carleton.magicrealm.GUI.tile;

import java.io.Serializable;

/**
 * Created by Tony on 01/04/2015.
 */
public class Path implements Serializable{

    private static final long serialVersionUID = 2549968426993209153L;

    private Clearing fromClearing;

    private Clearing toClearing;

    public Path(final Clearing fromClearing, final Clearing toClearing) {
        this.fromClearing = fromClearing;
        this.toClearing = toClearing;
    }

    /*
    public boolean checkIfClearingIsConnectedToPath(final Clearing clearing) {
        for (Path path: clearing.getAdjacentPaths()) {
            if (fromClearing.toString().equals(path.toString())
                    || toClearing.toString().equals(cl.toString()))
                return true;
        }
        return false;
    }
    */

    public Clearing getFromClearing() {
        return fromClearing;
    }

    public Clearing getToClearing() {
        return toClearing;
    }
}
