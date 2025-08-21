package ai;

import exception.NoMoveFoundException;
import map.FullMap;
import map.FullMapNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public class AIMovement {
    private Position actualPosition;
    private Position treasurePosition;
    private Position fortPosition;

    private List<EMove> actions = new LinkedList<>();
    private boolean isTreasureTaken = false;
    private boolean switchSide = false;

    private final TargetFinder targetFinder = new TargetFinder();
    private final WayFinder wayFinder = new WayFinder();
    private final VisitedFieldsTracker visitedFieldsTracker = new VisitedFieldsTracker();
    private static final Logger logger = LoggerFactory.getLogger(AIMovement.class);


    public Position getTreasurePosition() {
        return treasurePosition;
    }

    public EMove move(FullMap fullMap, boolean isTreasureCollected) {

        updatePosition(fullMap, isTreasureCollected);

        if (isTreasureCollected && !switchSide) {
            actions.clear();
            switchSide = true;
        }

        if (isNewTargetPresent(fullMap)) {

            Position currentTarget = targetFinder.getCurrentTarget();

            List<FullMapNode> mapNodes = wayFinder.findWay(currentTarget,
                    actualPosition, fullMap);

            PathConverter converter = new PathConverter();
            actions = converter.convertToMoves(mapNodes);
        }
        if (actions.isEmpty()) {
            throw new NoMoveFoundException("AI did not find next move to send - actions list is empty");
        }
        return actions.removeFirst();
    }

    private boolean isNewTargetPresent(FullMap fullMap) {
        Position currentTarget = targetFinder.getCurrentTarget();
        if (isTreasureFound(fullMap.getNodeListMyPart())
                && (!currentTarget.equals(treasurePosition))) {
            targetFinder.setCurrentTarget(treasurePosition);
            return true;
        }
        if (isFortFound(fullMap.getNodeList()) && isTreasureTaken
                && (!currentTarget.equals(fortPosition))) {
            targetFinder.setCurrentTarget(fortPosition);
            return true;
        }
        if (actions.isEmpty()) {
            List<FullMapNode> nodeList = isTreasureTaken ?
                    fullMap.getNodeListEnemyPart(): fullMap.getNodeListMyPart();

            targetFinder.findNextTarget(nodeList,
                    actualPosition, visitedFieldsTracker.getVisitedNodes());
            return true;
        }
        return false;
    }

    private boolean isTreasureFound(List<FullMapNode> map) {
        for (FullMapNode node: map) {
            if (node.isTreasurePresent()) {
                treasurePosition = new Position(node.getX(), node.getY());
                logger.info("Treasure is found, position: {}", treasurePosition);
                return true;
            }
        }
        return false;
    }
    private boolean isFortFound(List<FullMapNode> map) {
        for (FullMapNode node: map) {
            if (node.isEnemyFortPresent()) {
                fortPosition = new Position(node.getX(), node.getY());
                logger.info("Fort is found, position: {}", fortPosition);
                return true;
            }
        }
        return false;
    }

    private void updatePosition(FullMap fullMap, boolean isTreasureCollected) { 
        for (FullMapNode node : fullMap.getNodeList()) {
            if(node.isPlayerPresent()) {
                actualPosition = new Position(node.getX(), node.getY());
                visitedFieldsTracker.noteVisitedField(node, fullMap.getMapWidthX(), fullMap.getMapHeightY());
                if (isTreasureCollected) {
                    if (!isTreasureTaken) {
                        treasurePosition = new Position(node.getX(), node.getY());
                        isTreasureTaken = true;
                        logger.info("Treasure is collected");
                    }
                }
                break;
            }
        }
    }

}
