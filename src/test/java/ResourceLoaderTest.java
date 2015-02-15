import org.junit.Test;

import java.net.URL;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Ensures we can find the specified resource.
 * <p/>
 * Created with IntelliJ IDEA.
 * Date: 14/02/15
 * Time: 4:54 PM
 */
public class ResourceLoaderTest {

    @Test
    public void canFindResourceTest() throws Exception {
        final URL url = this.getClass().getClassLoader().getResource("image/armor/breastplate.gif");
        assertThat(url, is(not(nullValue())));
        assertThat(url.toString().contains("breastplate"), is(true));
    }
}

