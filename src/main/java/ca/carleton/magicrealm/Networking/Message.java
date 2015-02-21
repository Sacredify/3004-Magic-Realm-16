package ca.carleton.magicrealm.Networking;

import ca.carleton.magicrealm.GUI.tile.Clearing;

import java.io.Serializable;
import java.util.List;

public final class Message implements Serializable {


    int senderID;
    String messageType;
    Object messageObject;


    public Message(int senderID,String messageType,Object messageObject){
         this.senderID = senderID;
         this.messageType = messageType;
         this.messageObject = messageObject;
    }

    //TILES

    public static final String AWFUL_VALLEY = "AWFUL_VALLEY";
    public static final String BAD_VALLEY = "BAD_VALLEY";
    public static final String CRUST_VALLEY = "CRUST_VALLEY";
    public static final String DARK_VALLEY = "DARK_VALLEY";
    public static final String EVIL_VALLEY = "EVIL_VALLEY";
    public static final String BORDER_LAND = "BORDER_LAND";
    public static final String CAVERN = "CAVERN";
    public static final String CAVES = "CAVES";
    public static final String CLIFF = "CLIFF";
    public static final String CRAG = "CRAG";
    public static final String DEEP_WOODS = "DEEP_WOODS";
    public static final String LINDEN_WOODS = "LINDEN_WOODS";
    public static final String MAPLE_WOODS = "MAPLE_WOODS";
    public static final String NUT_WOODS = "NUT_WOODS";
    public static final String OAK_WOODS = "OAK_WOODS";
    public static final String PINE_WOODS = "PINE_WOODS";
    public static final String HIGH_PASS = "HIGH_PASS";
    public static final String LEDGES = "LEDGES";
    public static final String MOUNTAIN = "MOUNTAIN";
    public static final String RUINS = "RUINS";

    //PHASES

    public static final String MOVE = "MOVE";
    public static final String TRADE = "TRADE";
    public static final String ALERT = "ALERT";
    public static final String SEARCH = "SEARCH";
    public static final String HIRE = "HIRE";
    public static final String FOLLOW = "FOLLOW";

    //GAME MECHANICS

    public static final String SELECT_CHARACTER = "SELECT_CHARACTER";
    public static final String TURN_ALERT = "TURN_ALERT";
    public static final String PHASE_SELECT = "PHASE_SELECT";
    public static final String AVAILABLE_CLEARINGS = "AVAILABLE_CLEARINGS";
    public static final String ALL_PARTICIPATED = "ALL_PARTICIPATED";



    //CHARACTER TYPES

    public static final String SWORDSMAN = "SWORDSMAN";
    public static final String CAPTAIN = "CAPTAIN";
    public static final String DWARF = "DWARF";
    public static final String AMAZON = "AMAZON";
    public static final String ELF = "ELF";
    public static final String BLACK_KNIGHT = "BLACK_KNIGHT";



    public String getMessageType(){
        return messageType;
    }

    public Object getMessageObject() {
        return this.messageObject;
    }


}
