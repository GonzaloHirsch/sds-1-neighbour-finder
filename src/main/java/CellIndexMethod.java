import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CellIndexMethod {
    private Map<Integer, Map<Integer, List<Particle>>> map;
    private Collection<Particle> particles;
    private int matrixSize;
    private double rc;
    private boolean isPeriodic;

    public CellIndexMethod(Collection<Particle> particles, int matrixSize, double rc, boolean isPeriodic) {
        this.particles = particles;
        this.map = this.createCellMap(particles);
        this.matrixSize = matrixSize;
        this.rc = rc;
        this.isPeriodic = isPeriodic;
    }

    private Map<Integer, Map<Integer, List<Particle>>> createCellMap(Collection<Particle> particles) {
        return particles.stream()
                .collect(Collectors.groupingBy(Particle::getCellRow, Collectors.groupingBy(Particle::getCellColumn)));
    }

    public Collection<Particle> solveBruteForce() {
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

        for (row = 0; row < matrixSize; row++) {
            for (column = 0; column < matrixSize; column++) {
                List<Particle> inCellParticles = map.getOrDefault(row, new HashMap<>()).getOrDefault(column, new ArrayList<>());

                if (!inCellParticles.isEmpty()) {
                    List<Particle> adjacentParticles = this.getAdjacentCellsContent(row, column);
                    adjacentParticles.addAll(inCellParticles);

                    for (Particle inCellParticle : inCellParticles) {
                        List<Particle> neighbours = adjacentParticles.stream()
                                .filter(p -> inCellParticle.getId() != p.getId() && inCellParticle.isNeighbour(isPeriodic, rc, p))
                                .collect(Collectors.toList());

                        inCellParticle.addNeighbours(neighbours);
                        neighbours.forEach(neighbour -> neighbour.addNeighbour(inCellParticle));
                    }
                }
            }
        }

        for (row = 0; row < matrixSize; row++) {
            for (column = 0; column < matrixSize; column++) {
                particles.addAll(this.getParticlesInCell(row, column));
            }
        }
        return particles;
    }

    private List<Particle> getAdjacentCellsContent(int row, int column) {
        List<Particle> adjParticles = new ArrayList<>();

        adjParticles.addAll(this.getAdjacentContent(row, column + 1));
        adjParticles.addAll(this.getAdjacentContent(row + 1, column));
        adjParticles.addAll(this.getAdjacentContent(row + 1, column + 1));
        adjParticles.addAll(this.getAdjacentContent(row - 1, column + 1));

        return adjParticles;
    }

    private List<Particle> getAdjacentContent(int row, int column) {
        if (isPeriodic) {
            if (row >= matrixSize) {
                row = 0;
            }
            if (column >= matrixSize) {
                column = 0;
            }
            if (row <= -1) {
                row = matrixSize - 1;
            }
            if (column <= -1) {
                column = matrixSize - 1;
            }
        } else if (row < 0 || row > matrixSize - 1 || column < 0 || column > matrixSize - 1) {
            return Collections.emptyList();
        }
        return this.getParticlesInCell(row, column);
    }
    
    private List<Particle> getParticlesInCell(int row, int col) {
        return map.getOrDefault(row, new HashMap<>()).getOrDefault(col, new ArrayList<>());
    }
}
