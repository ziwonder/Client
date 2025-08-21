package map;

import ai.Position;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FullMap implements IMapVisualisation {
    private final List<FullMapNode> nodeList;
    private final int mapWidthX;
    private final int mapHeightY;
    private List<FullMapNode> nodeListMyPart;
    private List<FullMapNode> nodeListEnemyPart;
    private Optional<Position> treasurePosition = Optional.empty();

    private final Logger logger = LoggerFactory.getLogger(FullMap.class);

    public FullMap(List<FullMapNode> nodeList) {
        this.nodeList = nodeList;
        this.nodeListMyPart = new ArrayList<>();
        this.nodeListEnemyPart = new ArrayList<>();
        this.mapWidthX = nodeList.stream()
                .mapToInt(FullMapNode::getX)
                .max()
                .orElse(0);

        this.mapHeightY = nodeList.stream()
                .mapToInt(FullMapNode::getY)
                .max()
                .orElse(0);
        splitMap();
    }

    public List<FullMapNode> getNodeList() {
        return nodeList;
    }

    public List<FullMapNode> getNodeListMyPart() {
        return nodeListMyPart;
    }

    public List<FullMapNode> getNodeListEnemyPart() {
        return nodeListEnemyPart;
    }

    public int getMapWidthX() {
        return mapWidthX;
    }

    public int getMapHeightY() {
        return mapHeightY;
    }

    public void setTreasurePosition(Position pos) {
        this.treasurePosition = Optional.ofNullable(pos);
    }

    private void splitMap() {
        boolean splitVertically = mapWidthX > mapHeightY;
        int splitLine = splitVertically ? mapWidthX / 2 : mapHeightY / 2;
        logger.trace("Splitting map vertically at line {}", splitLine);
        Optional<FullMapNode> fortNodeOptional = nodeList.stream()
                .filter(FullMapNode::isMyFortPresent)
                .findFirst();

        if (fortNodeOptional.isPresent()) {
            FullMapNode fortNode = fortNodeOptional.get();
            boolean isFortOnRightSide = splitVertically
                    ? fortNode.getX() > splitLine
                    : fortNode.getY() > splitLine;
            logger.trace("My fort is on the {} side.", isFortOnRightSide ? "right" : "left");

            for (FullMapNode node : nodeList) {
                boolean isOnMySide = splitVertically
                        ? (isFortOnRightSide == (node.getX() > splitLine))
                        : (isFortOnRightSide == (node.getY() > splitLine));

                if (isOnMySide) {
                    nodeListMyPart.add(node);
                } else {
                    nodeListEnemyPart.add(node);
                }
            }
        }
    }
    public void updateEnemyPart(Position enemyPosition) {
        List<FullMapNode> newNodeList = new ArrayList<>();
        for (FullMapNode node : nodeListEnemyPart) {
            if (isCloseToEnemy(node, enemyPosition)) {
                newNodeList.add(node);
            }
        }
        this.nodeListEnemyPart = newNodeList;
    }
    private boolean isCloseToEnemy(FullMapNode node, Position enemyPosition) {
        int distance = Math.abs(node.getX() - enemyPosition.x()) + Math.abs(node.getY() - enemyPosition.y());
        return distance < 5;
    }

    @Override
    public void printMap() {

        int width = mapWidthX + 1;
        int height = mapHeightY + 1;
        FullMapNode[][] grid = new FullMapNode[height][width]; // Y=0..5, X=0..10

        for (FullMapNode node : nodeList) {
            int x = node.getX();
            int y = node.getY();
            grid[y][x] = node;
        }
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                FullMapNode node = grid[y][x];
                if (node == null) {
                    System.out.print("[   ]");
                } else {
                    String symbol = getFieldSymbol(node);
                    System.out.printf("[%s]", symbol);
                }
            }
            System.out.println();
        }
    }

    private String getFieldSymbol(FullMapNode node) {
        String symbol = switch (node.getTerrain()) {
            case ETerrain.Grass -> "🌳";
            case ETerrain.Water -> "💦";
            case ETerrain.Mountain -> "⛰️";
        };
        if (node.isPlayerPresent()) {
            symbol = "🦸";
        }
        if (node.isEnemyPresent()) {
            symbol = "🦹";
        }
        if (node.isEnemyPresent() && node.isPlayerPresent()) {
            symbol = "⚔️";
        }
        if (node.isMyFortPresent()) {
            symbol = "🏰";
        }
        if (node.isEnemyFortPresent()) {
            symbol = "🏯";
        }
        if (treasurePosition.isPresent()) {
            if (node.getX() == treasurePosition.get().x()
                    && node.getY() == treasurePosition.get().y()) {
                symbol = "❌";
            }
        }

        if (node.isTreasurePresent()) {
            symbol = "💰";
        }
        return symbol;
    }
}
