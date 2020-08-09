import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CellIndexMethod {
    private Map<Integer, Map<Integer, List<Particle>>> map;
    private List<Particle> particles;
    private int rowCount;
    private double rc;
    private boolean isPeriodic;

    public CellIndexMethod(List<Particle> particles, int rowCount, double rc, boolean isPeriodic) {
        this.particles = particles;
        this.map = this.createCellMap(particles);
        this.rowCount = rowCount;
        this.rc = rc;
        this.isPeriodic = isPeriodic;
    }

    private Map<Integer, Map<Integer, List<Particle>>> createCellMap(List<Particle> particles) {
        return particles.stream()
                .collect(Collectors.groupingBy(Particle::getCellRow, Collectors.groupingBy(Particle::getCellColumn)));
    }

    public List<Particle> solveBruteForce() {
        this.particles.forEach(particle -> particle.addNeighbours(
                particles.stream()
                        .filter(p -> particle.getId() != p.getId() && particle.isNeighbour(isPeriodic, rc, p))
                        .collect(Collectors.toList()))
        );

        return particles;
    }

    public List<Particle> solveOptimized() {
        List<Particle> particles = new ArrayList<>();
        int row, column;

        for (row = 0; row < rowCount; row++) {
            for (column = 0; column < rowCount; column++) {
                List<Particle> inCellParticles = map.getOrDefault(row, new HashMap<>()).getOrDefault(column, new ArrayList<>());

                if (!inCellParticles.isEmpty()) {
                    List<Particle> adjacentCellParticles = this.getAdjacentCellsContent(row, column, isPeriodic);

                    for (Particle inCellParticle : inCellParticles) {
                        List<Particle> neighbours = Stream.concat(
                                inCellParticles.stream().filter(p -> inCellParticle.getId() != p.getId()),
                                adjacentCellParticles.stream()
                        ).filter(p -> inCellParticle.isNeighbour(isPeriodic, rc, p))
                                .collect(Collectors.toList());

                        inCellParticle.addNeighbours(neighbours);
                        neighbours.forEach(neighbour -> neighbour.addNeighbour(inCellParticle));
                    }
                }
            }
        }

        for (row = 0; row < rowCount; row++) {
            for (column = 0; column < rowCount; column++) {
                particles.addAll(this.getParticlesInCell(row, column));
            }
        }
        return particles;
    }

    private List<Particle> getAdjacentCellsContent(int row, int column, boolean isPeriodic) {
        return isPeriodic ? this.getAdjacentCellsContentPeriodic(row, column) : this.getAdjacentCellsContentNonPeriodic(row, column);
    }

    private List<Particle> getAdjacentCellsContentNonPeriodic(int row, int column) {
        List<Particle> adjParticles = new ArrayList<>();

        if (row < rowCount - 1) {
            adjParticles.addAll(this.getParticlesInCell(row + 1, column));
        }
        if (column < rowCount - 1) {
            adjParticles.addAll(this.getParticlesInCell(row, column + 1));
        }
        if (row > 0) {
            adjParticles.addAll(this.getParticlesInCell(row - 1, column));
        }
        if (row < rowCount - 1 && column < rowCount - 1) {
            adjParticles.addAll(this.getParticlesInCell(row + 1, column + 1));
        }
        return adjParticles;
    }

    private List<Particle> getAdjacentCellsContentPeriodic(int row, int column) {
        return null;
    }

    private List<Particle> getParticlesInCell(int row, int col) {
        return map.getOrDefault(row, new HashMap<>()).getOrDefault(col, new ArrayList<>());
    }
}
