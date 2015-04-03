package ca.carleton.magicrealm;

import ca.carleton.magicrealm.network.AppClient;
import ca.carleton.magicrealm.network.AppServer;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Main class for the application. Serves as the launcher for both the client and server.
 * Provides help information for the usage of the app (for both server and client.)
 *
 * @author Mike
 */
public class Launcher {

    public static boolean CHEAT_MODE = false;

    private static final Logger LOG = LoggerFactory.getLogger(Launcher.class);

    private static final String IP_ADDRESS_ARG = "ip";

    private static final String PORT_ARG = "port";

    private static final String CHEAT_ARG = "cheat";

    private static final String HOST_ARG = "host";

    private static final String NUMBER_CLIENTS_ARG = "max";

    private static final String LAUNCH_COMMAND = "java -jar Magic_Realm.jar";

    public static void main(String[] args) {

        Options options = new Options();
        HelpFormatter formatter = new HelpFormatter();

        options.addOption(HOST_ARG, false, "Whether or not to start as a server host. [used by -> host]");
        options.addOption(NUMBER_CLIENTS_ARG, true, "Optional. How many clients to wait for. Default is 2. [used by -> host]");
        options.addOption(IP_ADDRESS_ARG, true, "The ip address to connect to. [used by -> client]");
        options.addOption(PORT_ARG, true, "The port to use. [used by -> client/host]");
        options.addOption(CHEAT_ARG, false, "Optional. Whether or not to use cheat mode. [used by -> client/host]");

        try {
            CommandLineParser parser = new BasicParser();
            CommandLine cmd = parser.parse(options, args);
            if (cmd.hasOption(CHEAT_ARG)) {
                CHEAT_MODE = true;
                LOG.info("Cheat mode enabled for this session.");
            }
            if (cmd.hasOption(HOST_ARG)) {
                LOG.info("Attempting to start as host server...");
                if (!cmd.hasOption(PORT_ARG)) {
                    LOG.error("No port specified for host.");
                    throw new Exception("Attempted to start as host with no port specified.");
                }
                if (cmd.hasOption(NUMBER_CLIENTS_ARG)) {
                    LOG.info("Overwriting MAX_PLAYERS arg to user-specified value of {}.", cmd.getOptionValue(NUMBER_CLIENTS_ARG));
                    new AppServer(Integer.parseInt(cmd.getOptionValue(PORT_ARG)), Integer.parseInt(cmd.getOptionValue(NUMBER_CLIENTS_ARG))).start();
                } else {
                    LOG.info("Starting with default max players [{}].", AppServer.DEFAULT_MAX_PLAYERS);
                    new AppServer(Integer.parseInt(cmd.getOptionValue(PORT_ARG)), AppServer.DEFAULT_MAX_PLAYERS).start();
                }
            } else {
                if (!cmd.hasOption(IP_ADDRESS_ARG) || !cmd.hasOption(PORT_ARG)) {
                    throw new Exception("Attempted to start with missing parameters.");
                } else {
                    LOG.info("Started launcher as a game client.");
                    LOG.info("Connecting to {}:{}.", cmd.getOptionValue(IP_ADDRESS_ARG), cmd.getOptionValue(PORT_ARG));
                    new AppClient(cmd.getOptionValue(IP_ADDRESS_ARG), Integer.parseInt(cmd.getOptionValue(PORT_ARG)));
                }
            }
        } catch (final Exception exception) {
            LOG.error("Error with options parse. Cause: --> {}", exception.getMessage() == null || exception.getMessage().equals("null") ? "Invalid parse." : exception.getMessage());
            formatter.printHelp(LAUNCH_COMMAND, options);
        }
    }
}
