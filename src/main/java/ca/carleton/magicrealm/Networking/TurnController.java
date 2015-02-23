package ca.carleton.magicrealm.Networking;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Created by anvargazizov on 2015-02-19.
 */
public class TurnController {
    private PriorityQueue<Integer> orderedIDsQueue = null;
    private ArrayList<Integer> orderedIDs = null;
    private int turnCount;

    public TurnController() {
        this.turnCount = 0;
        this.orderedIDsQueue = new PriorityQueue<>();
        this.orderedIDs = new ArrayList<>();
    }

    public void createNewTurnOrder(ArrayList<ServerThread> clientThreads) {
        Collections.shuffle(clientThreads);
        for (ServerThread s : clientThreads) {
            this.orderedIDsQueue.add(s.getID());
            this.orderedIDs.add(s.getID());
        }
    }

    public void createNewTurn() {
        for (Integer s : this.orderedIDs) {
            this.orderedIDsQueue.add(s);
        }
    }

    //Returns current turn count
    public int incrementTurnCount() {
        this.turnCount++;
        if (this.turnCount == AppServer.MAX_PLAYERS) {
            this.turnCount = 0;
            return AppServer.MAX_PLAYERS;
        } else {
            return this.turnCount;
        }
    }

    public int getNextPlayer() {
        if (this.orderedIDsQueue.size() != 0) {
            return this.orderedIDsQueue.remove();
        } else {
            return -1;
        }
    }
}
