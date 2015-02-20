package ca.carleton.magicrealm.Networking;

import com.sun.corba.se.spi.activation.Server;

import java.util.*;

/**
 * Created by anvargazizov on 2015-02-19.
 */
public class TurnController {
    private PriorityQueue<Integer> orderedIDs= null;

    public TurnController(){
        orderedIDs = new PriorityQueue<>();
    }

    public void createNewTurn(ArrayList<ServerThread> clientThreads){
        Collections.shuffle(clientThreads);
        for(ServerThread s:clientThreads){
            orderedIDs.add(s.getID());
        }
    }

    public int getNextPlayer(){
        if(orderedIDs.size()!=0){
            return  orderedIDs.remove();
        }
        else{
            return -1;
        }
    }
}
