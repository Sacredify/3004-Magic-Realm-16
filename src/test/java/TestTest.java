import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;

/**
 * Created with IntelliJ IDEA.
 * Date: 03/02/15
 * Time: 5:14 PM
 */
public class TestTest {

    @Test
    public void canTestICanMakeATest9() throws  Exception {
        assertThat("test", is(not(nullValue())));
    }
}
