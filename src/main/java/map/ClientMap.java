package map;

import java.util.ArrayList;
import java.util.List;

public class ClientMap implements IMapVisualisation {
    private List<HalfMapNode> mapNodes;
    private final int sizeX;
    private final int sizeY;

    public ClientMap(List<HalfMapNode> nodes, int sizeX, int sizeY) {
        mapNodes = new ArrayList<>(nodes);
        this.sizeX = sizeX;
        this.sizeY = sizeY;
    }
    public List<HalfMapNode> getNodesList() {
        return mapNodes;
    }

    public int getSizeX() {
        return sizeX;
    }
    public int getSizeY() {
        return sizeY;
    }

    @Override
    public void printMap() {
        HalfMapNode[][] grid = new HalfMapNode[sizeY][sizeX];

        for (HalfMapNode node : mapNodes) {
            int x = node.getX();
            int y = node.getY();
            grid[y][x] = node;
        }
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                HalfMapNode node = grid[y][x];
                if (node == null) {
                    System.out.print("[   ]");
                } else {
                    String symbol = switch (node.getTerrain()) {
                        case ETerrain.Grass -> "🌳";
                        case ETerrain.Water -> "💦";
                        case ETerrain.Mountain -> "⛰️";
                    };
                    if (node.isFortPresent()) {
                        symbol = "🏰";
                    }
                    System.out.printf("[%s]", symbol);
                }
            }
            System.out.println();
        }
    }

}
