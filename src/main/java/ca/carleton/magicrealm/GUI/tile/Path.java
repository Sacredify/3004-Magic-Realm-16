package ca.carleton.magicrealm.GUI.tile;

import java.io.Serializable;

/**
 * Created by Tony on 01/04/2015.
 */
public class Path implements Serializable, Discoverable {

    private static final long serialVersionUID = 2549968426993209153L;

    private Clearing fromClearing;

    private Clearing toClearing;

    private boolean isHidden;

    public Path(final Clearing fromClearing, final Clearing toClearing) {
        this.fromClearing = fromClearing;
        this.toClearing = toClearing;
        this.isHidden = false;
    }

    public Path(final Clearing fromClearing, final Clearing toClearing, final boolean isHidden) {
        this.fromClearing = fromClearing;
        this.toClearing = toClearing;
        this.isHidden = isHidden;
    }

    public boolean checkIfClearingIsConnectedToPath(final Clearing clearing) {
        return fromClearing.equals(clearing) || toClearing.equals(clearing);
    }

    public Clearing getFromClearing() {
        return fromClearing;
    }

    public Clearing getToClearing() {
        return toClearing;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean isHidden) {
        this.isHidden = isHidden;
    }

    @Override
    public boolean equals(final Object path) {
        return path instanceof Path
                && ((this.fromClearing == ((Path) path).getFromClearing() && this.toClearing == ((Path) path).getToClearing())
                || (this.fromClearing == ((Path) path).getToClearing() && this.toClearing == ((Path) path).getFromClearing()));
    }
}
