package ca.carleton.magicrealm.entity.natives;

/**
 * Created with IntelliJ IDEA.
 * Date: 17/02/15
 * Time: 4:27 PM
 */
public class NativeFactory {

    public static AbstractNative createNative(final NativeFaction faction, final NativeType nativeType) {

        switch (nativeType) {
            case ASSASSIN:
                return new Assassin(faction);
            case ARCHER:
                return new Archer(faction);
            case CROSSBOW_MAN:
                return new Crossbowman(faction);
            case GREAT_AXEMAN:
                return new GreatAxeman(faction);
            case GREAT_SWORDSMAN:
                return new GreatSwordsman(faction);
            case KNIGHT:
                return new Knight(faction);
            case LANCER:
                return new Lancer(faction);
            case PIKEMAN:
                return new Pikeman(faction);
            case RAIDER:
                return new Raider(faction);
            case SHORT_SWORDSMAN:
                return new ShortSwordsman(faction);
            case SWORDSMAN:
                return new Swordsman(faction);
            default:
                throw new IllegalArgumentException("Couldn't create character of type " + nativeType + ".");
        }
    }

}
