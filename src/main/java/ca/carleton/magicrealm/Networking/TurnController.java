package ca.carleton.magicrealm.Networking;

import java.util.*;

/**
 * Created by anvargazizov on 2015-02-19.
 */
public class TurnController {
    private PriorityQueue<Integer> orderedIDsQueue= null;
    private ArrayList<Integer>  orderedIDs = null;
    private int turnCount;

    public TurnController(){
        turnCount = 0;
        orderedIDsQueue = new PriorityQueue<>();
        orderedIDs = new ArrayList<>();
    }

    public void createNewTurnOrder(ArrayList<ServerThread> clientThreads){
        Collections.shuffle(clientThreads);
        for(ServerThread s:clientThreads){
            orderedIDsQueue.add(s.getID());
            orderedIDs.add(s.getID());
        }
    }

    public void createNewTurn(){
        for(Integer s:orderedIDs){
            orderedIDsQueue.add(s);
        }
    }

    //Returns current turn count
    public int incrementTurnCount(){
        turnCount++;
        if(turnCount == 6){
            turnCount = 0;
            return 6;
        }
        else
        return turnCount;
    }

    public int getNextPlayer(){
        if(orderedIDsQueue.size()!=0){
            return  orderedIDsQueue.remove();
        }
        else{
            return -1;
        }
    }
}
