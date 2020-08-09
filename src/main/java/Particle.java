import java.util.Set;
import java.util.TreeSet;

public class Particle implements Comparable<Particle>{
    private int id;
    private double x;
    private double y;
    private double radius;
    private double property;
    private Set<Particle> neighbours;

    private int cellRow;
    private int cellColumn;

    public Particle(int id, double x, double y, double radius, double property, int cellRow, int cellColumn) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.property = property;
        this.neighbours = new TreeSet<>();
        this.cellRow = cellRow;
        this.cellColumn = cellColumn;
    }

    public int compareTo(Particle particle) {
        return 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getProperty() {
        return property;
    }

    public void setProperty(double property) {
        this.property = property;
    }

    public Set<Particle> getNeighbours() {
        return neighbours;
    }

    public void addNeighbour(Particle neighbour) {
        this.neighbours.add(neighbour);
    }

    public int getCellRow() {
        return cellRow;
    }

    public void setCellRow(int cellRow) {
        this.cellRow = cellRow;
    }

    public int getCellColumn() {
        return cellColumn;
    }

    public void setCellColumn(int cellColumn) {
        this.cellColumn = cellColumn;
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
        return this.id == particle.getId()
                && this.x == particle.getX()
                && this.y == particle.getY();
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
}
