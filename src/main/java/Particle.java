import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class Particle implements Comparable<Particle>{
    private int id;
    private double x;
    private double y;
    private double vx;
    private double vy;
    private double radius;
    private double property;
    private Set<Particle> neighbours;

    private int cellRow;
    private int cellColumn;

    public Particle(int id, double x, double y, double vx, double vy, double radius, double property, int cellRow, int cellColumn) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.vx = vx;
        this.vy = vy;
        this.radius = radius;
        this.property = property;
        this.neighbours = new HashSet<>();
        this.cellRow = cellRow;
        this.cellColumn = cellColumn;
    }

    public Particle(int id, double radius, double property) {
        this.id = id;
        this.radius = radius;
        this.property = property;
        this.neighbours = new TreeSet<>();
    }

    public int compareTo(Particle particle) {
        return Integer.compare(id, particle.getId());
    }

    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

    public double getRadius() {
        return radius;
    }

    public double getProperty() {
        return property;
    }

    public Set<Particle> getNeighbours() {
        return neighbours;
    }

    public void addNeighbour(Particle neighbour) {
        this.neighbours.add(neighbour);
    }

    public void addNeighbours(Collection<Particle> neighbours) {
        this.neighbours.addAll(neighbours);
    }

    public int getCellRow() {
        return cellRow;
    }

    public int getCellColumn() {
        return cellColumn;
    }

    public double getDistanceFrom(Particle particle) {
        double distX = this.x - particle.getX();
        double distY = this.y - particle.getY();
        return Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2)) - (this.radius + particle.getRadius());  //FIXME What to do with radius? esto es de centro a centro
    }

    public double getPeriodicDistanceFrom(Particle particle, double totalLength) {
        double distX = Math.abs(this.x - particle.getX());
        double distY = Math.abs(this.y - particle.getY());

        /* If the length between them is larger than that of half the area, then the
         * periodic distance will be shorter.
         */
        if (distX > totalLength / 2) {
            distX = totalLength - distX;
        }
        if (distY > totalLength / 2) {
            distY = totalLength - distY;
        }
        return Math.sqrt(Math.pow(distX, 2) + Math.pow(distY, 2)) - (this.radius + particle.getRadius());  //FIXME What to do with radius? esto es de centro a centro
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Particle particle = (Particle) o;
        return this.id == particle.getId();
    }

    @Override
    public String toString() {
        return String.format("[Particle #%d] {x = %f, y = %f, radius = %f, property = %f}\n",
                this.id,
                this.x,
                this.y,
                this.radius,
                this.property
        );
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setVx(double vx) {
        this.vx = vx;
    }

    public void setVy(double vy) {
        this.vy = vy;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public void setProperty(double property) {
        this.property = property;
    }

    public void setCellRow(int cellRow) {
        this.cellRow = cellRow;
    }

    public void setCellColumn(int cellColumn) {
        this.cellColumn = cellColumn;
    }
}
