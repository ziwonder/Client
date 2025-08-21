package ai;

import map.FullMapNode;

import java.util.List;
import java.util.Optional;

public class CalculateEnemyPosition {
    public Optional<Position> calculateEnemyPosition(List<FullMapNode> nodeList) {
        for (FullMapNode node : nodeList) {
            if (node.isEnemyPresent()) {
                return Optional.of(new Position(node.getX(), node.getY()));
            }
        }
        return Optional.empty();
    }
}
