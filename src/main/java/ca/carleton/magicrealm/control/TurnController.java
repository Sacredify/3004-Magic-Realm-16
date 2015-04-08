package ca.carleton.magicrealm.control;

import ca.carleton.magicrealm.Launcher;
import ca.carleton.magicrealm.network.ServerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * Handles figuring out whose turn it is during game-play.
 */
public class TurnController {

    private static final Logger LOG = LoggerFactory.getLogger(TurnController.class);

    private final PriorityQueue<Integer> turnQueue;

    private int turnCount;

    private final ServerController server;

    public TurnController(final ServerController server) {
        this.server = server;
        this.turnCount = 0;
        this.turnQueue = new PriorityQueue<>();
    }

    /**
     * Creates a new ordering of the given client threads.
     */
    public void createNewTurnOrder(ArrayList<ServerThread> clientThreads, final String reason) {

        if (!Launcher.CHEAT_MODE) {
            Collections.shuffle(clientThreads);
            this.turnQueue.clear();
            this.turnQueue.addAll(clientThreads.stream().map(ServerThread::getID).collect(Collectors.toList()));
            LOG.info("Created new turn order. Ordering of clients is: {}", this.turnQueue);

        } else {
            LOG.info("Starting cheated turn order...");
            final List<ServerThread> copyOf = new ArrayList<ServerThread>(clientThreads);
            int count = 1;
            while (!copyOf.isEmpty()) {
                final ServerThread toAdd = (ServerThread) JOptionPane.showInputDialog(null,
                        String.format("Cheated turn order: Select a user to go for %s.[%d]", reason, count++),
                        "Cheat mode",
                        JOptionPane.QUESTION_MESSAGE, null,
                        copyOf.toArray(),
                        copyOf.get(0));
                copyOf.remove(toAdd);

                this.turnQueue.add(toAdd.getID());
                LOG.info("Added {} to the turn queue.", toAdd);
            }
        }
    }

    /**
     * Increment the number of turns that have passed.
     */
    public int incrementTurnCount() {
        this.turnCount++;
        LOG.info("A player has made a turn. Number of turns made this round: {}.", this.turnCount);
        if (this.turnCount == this.server.getNetworkConnection().getClientCount()) {
            LOG.info("All players have taken a turn. Resetting to 0.");
            this.turnCount = 0;
            return this.server.getNetworkConnection().getClientCount();
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
