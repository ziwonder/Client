package map;

public class FullMapNode extends MapNode{

    private boolean isPlayerPresent;
    private boolean isEnemyPresent;
    private boolean isTreasurePresent;
    private boolean isMyFortPresent;
    private boolean isEnemyFortPresent;

    public FullMapNode(int x, int y, ETerrain terrain, boolean isPlayerPresent,
                       boolean isEnemyPresent, boolean isTreasurePresent,
                       boolean isMyFortPresent, boolean isEnemyFortPresent) {
        super(x, y, terrain);
        this.isPlayerPresent = isPlayerPresent;
        this.isEnemyPresent = isEnemyPresent;
        this.isTreasurePresent = isTreasurePresent;
        this.isMyFortPresent = isMyFortPresent;
        this.isEnemyFortPresent = isEnemyFortPresent;
    }

    public boolean isEnemyFortPresent() {
        return isEnemyFortPresent;
    }

    public boolean isEnemyPresent() {
        return isEnemyPresent;
    }

    public boolean isMyFortPresent() {
        return isMyFortPresent;
    }

    public boolean isPlayerPresent() {
        return isPlayerPresent;
    }
    public boolean isTreasurePresent() {
        return isTreasurePresent;
    }
}
