import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * Date: 03/02/15
 * Time: 4:45 PM
 */
public class Test {

    private static final Logger LOG = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {
        LOG.info("Logged something.");

        try {
            Display.setDisplayMode(new DisplayMode(500, 500));
            Display.create();
        } catch (LWJGLException e) {
            LOG.error("Error with game library", e);
        }


    }
}
