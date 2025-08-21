package map;


public class MapNode {
    private final int x;
    private final int y;
    private final ETerrain terrain;
    public MapNode(int x, int y, ETerrain terrain) {
        this.x = x;
        this.y = y;
        this.terrain = terrain;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }
    public ETerrain getTerrain() {
        return terrain;
    }
}
