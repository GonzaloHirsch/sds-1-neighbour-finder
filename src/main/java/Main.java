import java.io.*;
import java.time.Instant;
import java.util.Collection;

public class Main {
    private static String OUTPUT_FILE = "./output.txt";

    public static void main(String[] args) {
        // Parsing the options
        OptionsParser.ParseOptions(args);

        try {
            // Parsing the particles
            ParticleParser.ParseParticles(OptionsParser.staticFile, OptionsParser.dynamicFile, OptionsParser.matrixSize);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(1);
        }

        // Creating the instance of the method
        CellIndexMethod cim = new CellIndexMethod(ParticleParser.particleMap.values(), OptionsParser.matrixSize, OptionsParser.interactionRadius, OptionsParser.usePeriodicBorders);

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

        // Generate the output file
        GenerateOutputFile(particles);
    }

    /**
     * Generates the output file given the analyzed particles
     *
     * @param particles List of particles, in order by id, to be written in the output file
     */
    private static void GenerateOutputFile(Collection<Particle> particles) {
        try {
            // Create the file to make sure it exists
            new File(OUTPUT_FILE);

            // Open the file for writing
            OutputStream f = new FileOutputStream(OUTPUT_FILE);

            // StringBuilder to minimize file writes
            StringBuilder sb = new StringBuilder();

            // Creating the output for the file
            particles.forEach(particle -> {
                sb.append(particle.getId());
                particle.getNeighbours().forEach(neighbour -> {
                    sb.append(" ");
                    sb.append(particle.getId());
                });
                sb.append("\n");
            });

            // Writing the content to the file
            for (byte b : sb.toString().getBytes()) {
                f.write(b);
            }

            f.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error writing to the output file");
        }
    }
}
