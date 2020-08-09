import org.apache.commons.cli.*;

public class OptionsParser {

    protected static String staticFile;
    protected static String dynamicFile;
    protected static boolean useBruteForce = false;
    protected static boolean usePeriodicBorders = false;
    protected static double interactionRadius;
    protected static int matrixSize;

    private static String PARAM_H = "h";
    private static String PARAM_M = "m";
    private static String PARAM_RC = "rc";
    private static String PARAM_SF = "sf";
    private static String PARAM_DF = "df";
    private static String PARAM_PB = "pb";
    private static String PARAM_BF = "bf";

    /**
     * Generates the options for the help.
     *
     * @return Options object with the options
     */
    private static Options GenerateOptions() {
        Options options = new Options();
        options.addOption(PARAM_H, "help", false, "Shows the system help.");
        options.addOption(PARAM_M, "matrix", true, "Size of the square matrix to be used");
        options.addOption(PARAM_RC, "radius", true, "Radius of interaction between particles");
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

            // Parsing the matrix size
            if (cmd.hasOption(PARAM_M)){
                matrixSize = Integer.parseInt(cmd.getOptionValue(PARAM_M));
            }

            // Parsing the interaction radius
            if (cmd.hasOption(PARAM_RC)){
                interactionRadius = Double.parseDouble(cmd.getOptionValue(PARAM_RC));
            }

            // Parsing the periodic border flag
            if (cmd.hasOption(PARAM_PB)){
                usePeriodicBorders = Boolean.parseBoolean(cmd.getOptionValue(PARAM_PB));
            }

            // Parsing the brute force flag
            if (cmd.hasOption(PARAM_BF)){
                useBruteForce = Boolean.parseBoolean(cmd.getOptionValue(PARAM_BF));
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
