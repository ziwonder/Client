package ai;

import exception.PathNotFoundException;
import map.ETerrain;
import map.FullMap;
import map.FullMapNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class WayFinder {

    private final Logger logger = LoggerFactory.getLogger(WayFinder.class);

    public List<FullMapNode> findWay(Position targetPosition, Position actualPosition,
                                     FullMap fullMap) {
        try {
            return findWayDijkstra(targetPosition, actualPosition, fullMap);
        }
        catch (PathNotFoundException e) {
            logger.error(e.getMessage());
            return new ArrayList<>();
        }
    }

    private List<FullMapNode> findWayDijkstra(Position targetPosition, Position actualPosition,
                                              FullMap fullMap) throws PathNotFoundException { //dijkstra-bfs
        Map<Position, Position> cameFrom = new HashMap<>();
        HashMap<Position, Integer> cost = new HashMap<>();
        Queue<Position> frontier = new PriorityQueue<>(
                Comparator.comparingInt(p -> cost.getOrDefault(p, Integer.MAX_VALUE)));
        frontier.add(actualPosition);
        cameFrom.put(actualPosition, null);
        cost.put(actualPosition, 0);
        while (!frontier.isEmpty()) {
            Position currentPosition = frontier.poll();

            if (currentPosition.equals(targetPosition)){
                break;
            }

            for (Position neighbor: getNeighbor(currentPosition)){

                Optional<FullMapNode> neighborNode = findNodeAt(fullMap.getNodeList(), neighbor.x(), neighbor.y());
                if(neighborNode.isPresent() && isWalkable(neighborNode.get())) {
                    int newCost = cost.get(currentPosition);
                    if (neighborNode.get().getTerrain() == ETerrain.Grass) {
                        newCost += 1;
                    }
                    else {
                        newCost += 2;
                    }
                    if (!cost.containsKey(neighbor) || newCost < cost.get(neighbor)) {
                        cost.put(neighbor, newCost);
                        frontier.add(neighbor);
                        cameFrom.put(neighbor, currentPosition);
                    }

                }
            }
        }
        List<FullMapNode> path = constructPath(cameFrom, targetPosition, fullMap);
        if (path.isEmpty()) {
            throw new PathNotFoundException("The target " + targetPosition + "is unreachable from " + actualPosition);
        }
        return path;
    }

    private List<FullMapNode> constructPath(Map<Position, Position> cameFrom,
                                            Position target, FullMap fullMap){
        List<FullMapNode> path = new LinkedList<>();
        Position current = target;

        while (current != null) {
            Optional<FullMapNode> node = findNodeAt(fullMap.getNodeList(), current.x(), current.y());
            if (node.isEmpty())
                break;
            path.addFirst(node.get());
            current = cameFrom.get(current);
        }
        if (!cameFrom.containsKey(target)) {
            return new LinkedList<>();
        }
        return path;
    }

    private List<Position> getNeighbor(Position pos) {
        List<Position> neighbors = new ArrayList<>();
        neighbors.add(new Position(pos.x() + 1, pos.y()));
        neighbors.add(new Position(pos.x() - 1, pos.y()));
        neighbors.add(new Position(pos.x(), pos.y() + 1));
        neighbors.add(new Position(pos.x(), pos.y() - 1));
        return neighbors;
    }

    private Optional<FullMapNode> findNodeAt(List<FullMapNode> map, int x, int y) {
        for (FullMapNode node : map) {
            if (node.getX() == x && node.getY() == y) return Optional.of(node);
        }
        return Optional.empty();
    }

    private boolean isWalkable(FullMapNode node) {
        return !node.getTerrain().equals(ETerrain.Water);
    }

}
