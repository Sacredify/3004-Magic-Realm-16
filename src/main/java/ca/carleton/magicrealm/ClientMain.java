package ca.carleton.magicrealm;

import ca.carleton.magicrealm.Networking.AppClient;
import ca.carleton.magicrealm.control.GameController;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created with IntelliJ IDEA.
 * Date: 03/02/15
 * Time: 4:45 PM
 */
public class ClientMain {

    public static boolean CHEAT_MODE = false;

    private static final Logger LOG = LoggerFactory.getLogger(ClientMain.class);

    private static final String IP_ADDRESS_ARG = "ip";

    private static final String PORT_ARG = "port";

    private static final String CHEAT_ARG = "cheat";

    public static void main(String[] args) {

        Options options = new Options();
        HelpFormatter formatter = new HelpFormatter();

        options.addOption(IP_ADDRESS_ARG, true, "The ip address to connect to.");
        options.addOption(PORT_ARG, true, "The port to use.");
        options.addOption(CHEAT_ARG, false, "Optional. Whether or not to use cheat mode.");

        try {
            CommandLineParser parser = new BasicParser();
            CommandLine cmd = parser.parse(options, args);

            if (!cmd.hasOption(IP_ADDRESS_ARG) || !cmd.hasOption(PORT_ARG)) {
                formatter.printHelp("java -jar Magic_Realm_Client.jar", options);
            } else {
                if (cmd.hasOption(CHEAT_ARG)) {
                    CHEAT_MODE = true;
                    LOG.info("Cheat mode enabled.");
                }
                GameController game = new GameController();
                LOG.info("Connecting to {}:{}.", cmd.getOptionValue(IP_ADDRESS_ARG), cmd.getOptionValue(PORT_ARG));
                AppClient client = new AppClient(cmd.getOptionValue(IP_ADDRESS_ARG), Integer.parseInt(cmd.getOptionValue(PORT_ARG)), game);
                game.setNetworkConnection(client);
            }

        } catch (final Exception exception) {
            LOG.error("Error with options parse. {}", exception.getMessage());
            formatter.printHelp("Team 16's Magic Realm Client", options);
        }
    }
}
