package ai;

import map.ETerrain;
import map.FullMapNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VisitedFieldsTracker {
    private final Set<Position> visitedNodes = new HashSet<>();
    private static final Logger logger = LoggerFactory.getLogger(VisitedFieldsTracker.class);

    public Set<Position> getVisitedNodes() {
        return visitedNodes;
    }

    public void noteVisitedField(FullMapNode node, int width, int height) {
        Position current = new Position(node.getX(), node.getY());
        visitedNodes.add(current);
        if (node.getTerrain().equals(ETerrain.Mountain)) {
            List<Position> neighbors = getNeighborMountain(current, width, height);
            logger.trace("Mountain {} has revealed: {}", current, neighbors);
            visitedNodes.addAll(neighbors);
        }
    }

    private List<Position> getNeighborMountain(Position pos, int width, int height) {
        List<Position> neighbors = new ArrayList<>();
        int [] rowDirection = {0, 0, 1, -1, 1, -1, -1, 1}; //right, left, up, down + diagonals
        int [] colDirection = {1, -1, 0, 0, 1, -1, 1, -1};
        for (int i = 0; i < rowDirection.length; i++) {
            int neighborX = pos.x() + rowDirection[i];
            int neighborY = pos.y() + colDirection[i];
            if (neighborX >= 0 && neighborX <= width && neighborY >= 0 && neighborY <= height) {
                neighbors.add(new Position(neighborX, neighborY));
            }
        }
        return neighbors;
    }

}
