package ai;

import map.ETerrain;
import map.FullMapNode;

import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PathConverter {
    private static final Logger logger = LoggerFactory.getLogger(PathConverter.class);

    public List<EMove> convertToMoves(List<FullMapNode> mapNodes) {
        List<EMove> moves = new ArrayList<>();
        if (mapNodes.isEmpty()) {
            logger.warn("No elements in moves list");
            return moves;
        }
        FullMapNode currentPosition = mapNodes.getFirst();
        for (FullMapNode node : mapNodes) {
            if (node.equals(mapNodes.getFirst())) continue;
            EMove move = getDirection(currentPosition, node);
            moves.add(move);
            if (currentPosition.getTerrain().equals(ETerrain.Mountain)) {
                moves.add(move);
            }
            moves.add(move);
            if (node.getTerrain().equals(ETerrain.Mountain)) {
                moves.add(move);
            }
            currentPosition = node;
        }
        return moves;
    }

    private EMove getDirection(FullMapNode from, FullMapNode to) {
        if (to.getX() > from.getX()) return EMove.Right;
        if (to.getX() < from.getX()) return EMove.Left;
        if (to.getY() > from.getY()) return EMove.Down;
        if (to.getY() < from.getY()) return EMove.Up;
        throw new IllegalStateException("No valid directions");
    }
}
