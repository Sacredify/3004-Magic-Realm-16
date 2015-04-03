package ca.carleton.magicrealm.network;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * Handles figuring out whose turn it is during game-play.
 */
public class TurnController {

    private static final Logger LOG = LoggerFactory.getLogger(TurnController.class);

    private final PriorityQueue<Integer> turnQueue;

    private int turnCount;

    private final AppServer server;

    public TurnController(final AppServer server) {
        this.server = server;
        this.turnCount = 0;
        this.turnQueue = new PriorityQueue<>();
    }

    /**
     * Creates a new ordering of the given client threads.
     */
    public void createNewTurnOrder(ArrayList<ServerThread> clientThreads) {
        Collections.shuffle(clientThreads);
        this.turnQueue.clear();
        this.turnQueue.addAll(clientThreads.stream().map(ServerThread::getID).collect(Collectors.toList()));
        LOG.info("Created new turn order. Ordering of clients is: {}", this.turnQueue);
    }

    /**
     * Increment the number of turns that have passed.
     */
    public int incrementTurnCount() {
        this.turnCount++;
        LOG.info("A player has made a turn. Number of turns made this round: {}.", this.turnCount);
        if (this.turnCount == this.server.getClientCount()) {
            LOG.info("All players have taken a turn. Resetting to 0.");
            this.turnCount = 0;
            return this.server.getClientCount();
        } else {
            return this.turnCount;
        }
    }

    /**
     * Return the next thread id in the queue.
     *
     * @return the id.
     */
    public int getNextPlayer() {
        if (this.turnQueue.size() != 0) {
            return this.turnQueue.remove();
        } else {
            return -1;
        }
    }
}
