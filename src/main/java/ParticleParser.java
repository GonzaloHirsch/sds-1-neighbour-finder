import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ParticleParser {

    /**
     * Total number of particles expected
     */
    public static int particleCount;
    /**
     * List with all the parsed particles
     */
    public static Map<Integer, Particle> particleMap;
    /**
     * Length of the area of the particles
     */
    public static double areaLength;

    /**
     * Parses the files given with the static and dynamic information in order to create the particles
     *
     * @param staticFileName  File path for the static file
     * @param dynamicFileName File path for the dynamic file
     */
    public static void ParseParticles(String staticFileName, String dynamicFileName) throws FileNotFoundException {
        ParseStaticData(staticFileName);
        ParseDynamicData(dynamicFileName);
    }

    private static void ParseStaticData(String staticFileName) throws FileNotFoundException {
        File file = new File(staticFileName);
        Scanner sc = new Scanner(file);

        // Parsing the particle count
        particleCount = sc.nextInt();

        // Parsing the length of the area
        areaLength = sc.nextDouble();

        for (int i = 0; i < particleCount; i++){
            // Parsing the radius
            double radius = sc.nextDouble();

            // Parsing the property
            double property = sc.nextDouble();

            // Creating and adding a new particle to the map
            particleMap.put(i + 1, new Particle(i + 1, radius, property));
        }
    }

    private static void ParseDynamicData(String dynamicFileName) throws FileNotFoundException {
        File file = new File(dynamicFileName);
        Scanner sc = new Scanner(file);

        // Skipping the time of the file
        sc.nextInt();

        for (int i = 0; i < particleCount; i++){
            // Parsing the x position
            double x = sc.nextDouble();

            // Parsing the y position
            double y = sc.nextDouble();

            // Parsing the x velocity
            double vx = sc.nextDouble();

            // Parsing the y velocity
            double vy = sc.nextDouble();

            // Recover the particle, add the new props and return it to the map
            Particle p = particleMap.get(i + 1);
            p.setX(x);
            p.setY(y);
            // FIXME -> Use correct amount
            p.setCellRow((int)Math.floor(x));
            p.setCellColumn((int)Math.floor(y));
            particleMap.put(p.getId(), p);
        }
    }
}