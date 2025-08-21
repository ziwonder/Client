package map;

public class HalfMapNode extends MapNode{
    private boolean fortPresent;
    public HalfMapNode(int x, int y, ETerrain terrain, boolean fortPresent){
        super(x, y, terrain);
        this.fortPresent = fortPresent;
    }

    public boolean isFortPresent() {
        return fortPresent;
    }
}
