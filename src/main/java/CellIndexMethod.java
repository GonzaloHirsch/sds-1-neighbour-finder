import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class CellIndexMethod {
    private Map<Integer, Map<Integer, List<Particle>>> map;
    private List<Particle> particles;
    private long particleCount;
    private long mapLength;
    private int cellHeight;
    private double rc;

    public CellIndexMethod(List<Particle> particles, long particleCount, long mapLength, int cellHeight, double rc) {
        this.particles = particles;
        this.map = this.createCellMap(particles);
        this.particleCount = particleCount;
        this.mapLength = mapLength;
        this.cellHeight = cellHeight;
        this.rc = rc;
    }

    private Map<Integer, Map<Integer, List<Particle>>> createCellMap(List<Particle> particles) {
        return particles.stream()
                .collect(Collectors.groupingBy(Particle::getCellRow, Collectors.groupingBy(Particle::getCellColumn)));
    }

    public List<Particle> solveBruteForce() {
        long startTime = Instant.now().toEpochMilli();

        this.particles.forEach(particle -> {
            particle.addNeighbours(
                    particles.stream()
                            .filter(p -> particle.getId() != p.getId() && particle.isNeighbour(rc, p))
                            .collect(Collectors.toList()));
        });

        long endTime = Instant.now().toEpochMilli();
        System.out.format("[Brute Force] Time = %d | Particles = %d | CellHeight = %d", endTime - startTime, particleCount, cellHeight);

        return particles;
    }

  

}
