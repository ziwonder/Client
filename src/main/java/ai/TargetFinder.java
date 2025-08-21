package ai;

import map.ETerrain;
import map.FullMapNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

public class TargetFinder {

    private Position currentTarget = new Position(-1, -1);
    private static final Logger logger = LoggerFactory.getLogger(TargetFinder.class);

    public void findNextTarget(List<FullMapNode> map, Position actualPosition, Set<Position> visitedNodes) {
        currentTarget = findTarget(map, actualPosition, visitedNodes);
        if (currentTarget != actualPosition) {
            logger.debug("Target: ({}, {})", currentTarget.x(), currentTarget.y());
        } else {
            logger.warn("No reachable target found!");
        }
    }

    public Position getCurrentTarget() {
        return currentTarget;
    }

    public void setCurrentTarget(Position currentTarget) {
        this.currentTarget = currentTarget;
    }

    private Position findTarget(List<FullMapNode> map, Position actualPosition, Set<Position> visitedNodes) {
        Position target = findMountain(map, actualPosition, visitedNodes);
        if (target == actualPosition) {
            target = findClosestGrass(map, actualPosition, visitedNodes);
        }
        return target;
    }

    private Position findMountain(List<FullMapNode> map, Position actualPosition, Set<Position> visitedNodes) {

        Position target = actualPosition;
        int minDistance = Integer.MAX_VALUE;
        int maxGrass = Integer.MIN_VALUE;

        for (FullMapNode node : map) {
            Position pos = new Position(node.getX(), node.getY());
            if (isAccessible(node) && !visitedNodes.contains(pos)
                    && node.getTerrain() == ETerrain.Mountain) {
                int distance = Math.abs(pos.x() - actualPosition.x()) + Math.abs(pos.y() - actualPosition.y());
                int grassCount = getNeighborGrassCount(pos, map, visitedNodes);

                if (grassCount < 3 && node.getTerrain() == ETerrain.Mountain)
                    continue;

                if (grassCount > maxGrass) {
                    minDistance = distance;
                    maxGrass = grassCount;
                    target = pos;
                }

                else if (grassCount == maxGrass && distance < minDistance) {
                    minDistance = distance;
                    target = pos;
                }
                logger.trace("Candidate at {} : grass count: {}, distance: {}", pos, grassCount, distance);
            }

        }

        return target;
    }

    private Position findClosestGrass(List<FullMapNode> map,
                                     Position actualPosition,Set<Position> visitedNodes) {
        Position closest = actualPosition;
        int minDistance = Integer.MAX_VALUE;
        for (FullMapNode node : map) {
            Position pos = new Position(node.getX(), node.getY());
            if (isAccessible(node) && !visitedNodes.contains(pos)
                    && node.getTerrain().equals(ETerrain.Grass) ) {
                int distance = Math.abs(pos.x() - actualPosition.x()) + Math.abs(pos.y() - actualPosition.y());
                if (distance < minDistance) {
                    minDistance = distance;
                    closest = pos;
                }
            }
        }
        return closest;
    }

    private boolean isAccessible(FullMapNode node) {
        return !node.getTerrain().equals(ETerrain.Water);
    }

    private int getNeighborGrassCount(Position pos, List<FullMapNode> map, Set<Position> visitedNodes ) {
        int grassCount = 0;
        for (FullMapNode node : map) {
            Position neighbor = new Position(node.getX(), node.getY());
            int dx = neighbor.x() - pos.x();
            int dy = neighbor.y() - pos.y();

            if (Math.abs(dx) <= 1 && Math.abs(dy) <= 1 && !(dx == 0 && dy == 0)) {
                if (node.getTerrain() == ETerrain.Grass && !visitedNodes.contains(neighbor)) {
                    grassCount++;
                }
            }
        }
        return grassCount;
    }
}

