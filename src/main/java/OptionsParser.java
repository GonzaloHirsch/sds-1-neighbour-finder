import org.apache.commons.cli.*;

public class OptionsParser {

    protected static String staticFile;
    protected static String dynamicFile;
    protected static boolean useBruteForce = false;
    protected static boolean usePeriodicBorders = false;

    private static final String PARAM_H = "h";
    private static final String PARAM_SF = "sf";
    private static final String PARAM_DF = "df";
    private static final String PARAM_PB = "pb";
    private static final String PARAM_BF = "bf";

    /**
     * Generates the options for the help.
     *
     * @return Options object with the options
     */
    private static Options GenerateOptions() {
        Options options = new Options();
        options.addOption(PARAM_H, "help", false, "Shows the system help.");
        options.addOption(PARAM_SF, "static_file", true, "Path to the file with the static values.");
        options.addOption(PARAM_DF, "dynamic_file", true, "Path to the file with the dynamic values.");
        options.addOption(PARAM_PB, "periodic_border", false, "Enables periodic border conditions.");
        options.addOption(PARAM_BF, "brute_force", false, "Enables brute force mode.");
        return options;
    }

    /**
     * Public
     *
     * @param args
     */
    public static void ParseOptions(String[] args) {
        // Generating the options
        Options options = GenerateOptions();

        // Creating the parser
        CommandLineParser parser = new DefaultParser();

        try {
            // Parsing the options
            CommandLine cmd = parser.parse(options, args);

            // Parsing the help
            if (cmd.hasOption(PARAM_H)){
                help(options);
            }

            // Parsing the periodic border flag
            if (cmd.hasOption(PARAM_PB)){
                usePeriodicBorders = true;
            }

            // Parsing the brute force flag
            if (cmd.hasOption(PARAM_BF)){
                useBruteForce = true;
            }

            // Checking if the files were present
            if (!cmd.hasOption(PARAM_SF) | !cmd.hasOption(PARAM_DF)){
                System.out.println("The dynamic and static file path are needed");
                System.exit(1);
            }

            // Parsing the file paths
            staticFile = cmd.getOptionValue(PARAM_SF);
            dynamicFile = cmd.getOptionValue(PARAM_DF);
        } catch (ParseException e) {
            System.out.println("Unknown command used");

            // Display the help again
            help(options);
        }
    }

    /**
     * Prints the help for the system to the standard output, given the options
     *
     * @param options Options to be printed as help
     */
    private static void help(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("Main", options);
        System.exit(0);
    }
}
