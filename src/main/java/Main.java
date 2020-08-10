import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;
import java.util.Collection;

public class Main {
    private static String OUTPUT_FILE = "./output.txt";
    private static String STAT_FILE = "./statistics.txt";

    public static void main(String[] args) {
        // Parsing the options
        OptionsParser.ParseOptions(args);

        try {
            // Parsing the particles
            ParticleParser.ParseParticles(OptionsParser.staticFile, OptionsParser.dynamicFile, ParticleParser.matrixSize);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(1);
        }

        // Creating the instance of the method
        CellIndexMethod cim = new CellIndexMethod(ParticleParser.particleMap.values(), ParticleParser.matrixSize, ParticleParser.areaLength, ParticleParser.interactionRadius, OptionsParser.usePeriodicBorders);

        long startTime = Instant.now().toEpochMilli();

        // Calculating the neighbours
        Collection<Particle> particles;
        if (OptionsParser.useBruteForce){
            particles = cim.solveBruteForce();
        } else {
            particles = cim.solveOptimized();
        }

        // Printing the result time
        long endTime = Instant.now().toEpochMilli();
        System.out.format("[%s + %s] Time = %d | Particles = %d\n",
                OptionsParser.useBruteForce ? "Brute Force": "Cell Index Method",
                OptionsParser.usePeriodicBorders ? "Periodic": "Not Periodic",
                endTime - startTime,
                ParticleParser.particleCount
        );
        // Generate stats file
        GenerateStatFile(endTime - startTime, OptionsParser.useBruteForce, ParticleParser.particleCount, ParticleParser.matrixSize, OptionsParser.usePeriodicBorders);

        // Generate the output file
        GenerateOutputFile(particles);
    }

    /**
     * Generates the statistics file
     *
     * @param executionTime Duration of the execution of the program in milliseconds
     * @param useBruteForce Boolean which identifies which method was used -> brute force or CMI
     * @param particleCount Amount of particles analyzed
     * @param matrixSize If CMI was used, indicates the n of the nxn matrix created
     * @param usePeriodicBorders Indicates if the neighbour analysis was done considering or not borders
     */
    private static void GenerateStatFile(long executionTime, boolean useBruteForce, int particleCount, int matrixSize, boolean usePeriodicBorders) {
        try {
            String sf = String.format("%s %d %d %d\n", useBruteForce ? "BF" : "CMI", matrixSize, particleCount, executionTime);
            Files.write(Paths.get(STAT_FILE), sf.getBytes(), StandardOpenOption.APPEND);

        } catch (FileNotFoundException e) {
            System.out.println(STAT_FILE + " not found");
        } catch (IOException e) {
            System.out.println("Error writing to the output file");
        }

    }

    /**
     * Generates the output file given the analyzed particles
     *
     * @param particles List of particles, in order by id, to be written in the output file
     */
    private static void GenerateOutputFile(Collection<Particle> particles) {
        try {
            // Create the file to make sure it exists
            File file = new File(OUTPUT_FILE);
            FileWriter fr = new FileWriter(file, false);

            // StringBuilder to minimize file writes
            StringBuilder sb = new StringBuilder();

            // Creating the output for the file
            particles.forEach(particle -> {
                sb.append(particle.getId());
                particle.getNeighbours().forEach(neighbour -> {
                    sb.append(" ");
                    sb.append(neighbour.getId());
                });
                sb.append("\n");
            });

            fr.write(sb.toString());
            fr.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error writing to the output file");
        }
    }


}
