package ca.carleton.magicrealm.log;

import org.apache.log4j.AppenderSkeleton;
import org.apache.log4j.spi.LoggingEvent;

import javax.swing.text.JTextComponent;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Date: 02/04/2015
 * Time: 5:32 AM
 */
public class LogWriter extends AppenderSkeleton {

    private static final List<String> messages = new ArrayList<String>();

    public boolean stopLogging;

    @Override
    protected void append(final LoggingEvent event) {
        if (!this.stopLogging) {
            messages.add(event.getRenderedMessage());
        }
    }

    @Override
    public void close() {
    }

    @Override
    public boolean requiresLayout() {
        return true;
    }

    public void output(final JTextComponent target) {
        try {
            target.setText(messages.stream().collect(Collectors.joining("\n")));
        } catch (final ConcurrentModificationException exception) {
            // Continue, honestly it doesn't matter at this point if there is an error...
        }
    }
}
