import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CellIndexMethod {
    private Map<Integer, Map<Integer, List<Particle>>> map;
    private Collection<Particle> particles;
    private int matrixSize;
    private double areaLength;
    private double rc;
    private boolean isPeriodic;

    public CellIndexMethod(Collection<Particle> particles, int matrixSize, double areaLength, double rc, boolean isPeriodic) {
        this.particles = particles;
        this.map = this.createCellMap(particles);
        this.matrixSize = matrixSize;
        this.areaLength = areaLength;
        this.rc = rc;
        this.isPeriodic = isPeriodic;
    }

    private Map<Integer, Map<Integer, List<Particle>>> createCellMap(Collection<Particle> particles) {
        return particles.stream()
                .collect(Collectors.groupingBy(Particle::getCellRow, Collectors.groupingBy(Particle::getCellColumn)));
    }

    private boolean particlesAreNeighbours(Particle p1, Particle p2) {
        return (isPeriodic ? p1.getPeriodicDistanceFrom(p2, areaLength) : p1.getDistanceFrom(p2)) <= rc;
    }

    /**
     * Brute force algorithm to analyze the neighbouring particles for each particle
     * @return Collection of the processed particles, each with its respective neighbor set
     */

    public Collection<Particle> solveBruteForce() {
        this.particles.forEach(particle -> particle.addNeighbours(
                particles.stream()
                        .filter(p -> particle.getId() != p.getId() && this.particlesAreNeighbours(particle, p))
                        .collect(Collectors.toList()))
        );
        return particles;
    }

    /**
     * Optimized algorithm using the Cell Index Method to analyze the neighbouring particles
     * @return Collection of the processed particles, each with its respective neighbor set
     */
    public Collection<Particle> solveOptimized() {
        List<Particle> particles = new ArrayList<>();
        int row, column;

        /* Iterating through the different cells */
        for (row = 0; row < matrixSize; row++) {
            for (column = 0; column < matrixSize; column++) {
                List<Particle> inCellParticles = map.getOrDefault(row, new HashMap<>()).getOrDefault(column, new ArrayList<>());

                if (!inCellParticles.isEmpty()) {
                    this.analyzeParticlesInCell(inCellParticles, row, column);
                }
            }
        }

        /* Add all analyzed particles in the result list */
        for (row = 0; row < matrixSize; row++) {
            for (column = 0; column < matrixSize; column++) {
                particles.addAll(this.getParticlesInCell(row, column));
            }
        }

        particles.sort(Particle::compareTo);
        return particles;
    }

    private void analyzeParticlesInCell(Collection<Particle> inCellParticles, int row, int column) {
        Collection<Particle> adjacentParticles = this.getAdjacentCellsContent(row, column);
        adjacentParticles.addAll(inCellParticles);

        /* For all particles in current cell, find the neighbors in current and adjacent cells */
        for (Particle currentParticle : inCellParticles) {
            Collection<Particle> neighbours = adjacentParticles.stream()
                    .filter(p -> currentParticle.getId() != p.getId() && this.particlesAreNeighbours(currentParticle, p))
                    .collect(Collectors.toList());

            /* Add the each particle to the respective neighbor list */
            currentParticle.addNeighbours(neighbours);
            neighbours.forEach(neighbour -> neighbour.addNeighbour(currentParticle));
        }
    }

    private Collection<Particle> getAdjacentCellsContent(int row, int column) {
        List<Particle> adjParticles = new ArrayList<>();

        adjParticles.addAll(this.getAdjacentContent(row, column + 1));
        adjParticles.addAll(this.getAdjacentContent(row + 1, column));
        adjParticles.addAll(this.getAdjacentContent(row + 1, column + 1));
        adjParticles.addAll(this.getAdjacentContent(row - 1, column + 1));

        return adjParticles;
    }

    private Collection<Particle> getAdjacentContent(int row, int column) {
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

    private Collection<Particle> getParticlesInCell(int row, int col) {
        return map.getOrDefault(row, new HashMap<>()).getOrDefault(col, new ArrayList<>());
    }
}
