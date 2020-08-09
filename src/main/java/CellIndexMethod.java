import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CellIndexMethod {
    private Map<Integer, Map<Integer, List<Particle>>> map;
    private long particleCount;
    private long mapLength;
    private int cellHeight;

    public CellIndexMethod(Map<Integer, Map<Integer, List<Particle>>> map, long particleCount, long mapLength, int cellHeight) {
        this.map = map;
        this.particleCount = particleCount;
        this.mapLength = mapLength;
        this.cellHeight = cellHeight;
    }

    
}
