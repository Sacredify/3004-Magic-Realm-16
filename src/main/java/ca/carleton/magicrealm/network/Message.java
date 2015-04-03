package ca.carleton.magicrealm.network;

import java.io.Serializable;

/**
 * Container class to facilitate the transfer to and from the server.
 */
public final class Message implements Serializable {

    private static final long serialVersionUID = 741681056897874122L;

    public static final String SELECT_CHARACTER = "SELECT_CHARACTER";

    public static final String BIRDSONG_START = "BIRDSONG_START";

    public static final String BIRDSONG_DONE = "BIRDSONG_DONE";

    public static final String DAYLIGHT_START = "DAYLIGHT_START";

    public static final String DAYLIGHT_DONE = "DAYLIGHT_DONE";

    public static final String SUNSET_UPDATE = "SUNSET UPDATE";

    public static final String COMBAT_FILL_OUT_MELEE_SHEET = "COMBAT_MELEE_SHEET_START";

    public static final String COMBAT_SEND_MELEE_SHEET = "COMBAT_MELEE_SHEET_FINISH";

    public static final String FATIGUE_FATIGUE_CHITS = "FATIGUE_START";

    public static final String FATIGUE_SUBMIT_UPDATED = "FATIGUE_END";

    /**
     * The ID of the sender of the message (client(s)/server).
     */
    private final int senderID;

    /**
     * The type of message sent. Used to direct game flow.
     */
    private final String messageType;

    /**
     * The data sent along with the message, if any.
     */
    private final Object payload;

    public Message(final int senderID, final String messageType, final Object payload) {
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
