package ca.carleton.magicrealm.entity.natives;

import ca.carleton.magicrealm.entity.EntityInformation;

/**
 * Created with IntelliJ IDEA.
 * Date: 17/02/15
 * Time: 4:27 PM
 */
public class NativeFactory {

    public static AbstractNative createNative(final NativeFaction faction, final EntityInformation nativeType) {

        switch (nativeType) {
            case NATIVE_ASSASSIN:
                return new Assassin(faction);
            case NATIVE_ARCHER:
                return new Archer(faction);
            case NATIVE_CROSSBOW_MAN:
                return new Crossbowman(faction);
            case NATIVE_GREAT_AXEMAN:
                return new GreatAxeman(faction);
            case NATIVE_GREAT_SWORDSMAN:
                return new GreatSwordsman(faction);
            case NATIVE_KNIGHT:
                return new Knight(faction);
            case NATIVE_LANCER:
                return new Lancer(faction);
            case NATIVE_PIKEMAN:
                return new Pikeman(faction);
            case NATIVE_RAIDER:
                return new Raider(faction);
            case NATIVE_SHORT_SWORDSMAN:
                return new ShortSwordsman(faction);
            default:
                throw new IllegalArgumentException("Couldn't create character of type " + nativeType + ".");
        }
    }

}
