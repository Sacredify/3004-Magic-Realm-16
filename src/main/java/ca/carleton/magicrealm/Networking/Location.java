package ca.carleton.magicrealm.Networking;

import java.util.ArrayList;


public class Location {
    public ArrayList<Path> paths = null;
    public String type;
    public int index;

    public void addPath(Path p) {
        paths.add(p);
    }
}
