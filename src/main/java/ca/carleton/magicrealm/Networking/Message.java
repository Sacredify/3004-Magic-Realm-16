package ca.carleton.magicrealm.Networking;

import java.io.Serializable;

/**
 * Container class to facilitate the transfer to and from the server.
 */
public final class Message implements Serializable {

    private static final long serialVersionUID = 741681056897874122L;

    public static final String SELECT_CHARACTER = "SELECT_CHARACTER";

    public static final String BIRDSONG_START = "BIRDSONG_START";

    public static final String DAYLIGHT_START = "DAYLIGHT_START";

    public static final String SUNSET_START = "SUNSET_START";

    public static final String START_COMBAT_IN_CLEARING = "COMBAT_START";

    public static final String DONE_COMBAT_IN_CLEARING = "COMBAT_FINISH";

    public static final String MIDNIGHT_START = "MIDNIGHT_START";

    public static final String BIRDSONG_DONE = "BIRDSONG_DONE";

    public static final String DAYLIGHT_DONE = "DAYLIGHT_DONE";

    public static final String SUNSET_DONE = "SUNSET_DONE";

    public static final String MIDNIGHT_DONE = "MIDNIGHT_DONE";

    /**
     * The ID of the sender of the message (client(s)/server).
     */
    private final int senderID;

    /**
     * The type of message sent. Used to direct gameflow.
     */
    private final String messageType;

    /**
     * The data sent along with the message, if any.
     */
    private final Object payload;

    public Message(int senderID, String messageType, Object payload) {
        this.senderID = senderID;
        this.messageType = messageType;
        this.payload = payload;
    }

    public int getSenderID() {
        return this.senderID;
    }

    public String getMessageType() {
        return this.messageType;
    }

    public Object getPayload() {
        return this.payload;
    }


}
