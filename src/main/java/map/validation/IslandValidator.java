package map.validation;

import exception.IslandDetectedException;
import map.ETerrain;
import map.HalfMapNode;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

public class IslandValidator implements IRuleValidator {

    private final int height;
    private final int width;

    public IslandValidator(int height, int width) {
        this.height = height;
        this.width = width;
    }

    @Override
    public void validate(List<HalfMapNode> map, Notification note) {
        if (!floodFill(map)) {
            note.addError( new IslandDetectedException("There is at least one island on the map"));
        }
    }

    private boolean floodFill(List<HalfMapNode> nodes) {
        boolean[][] visited = new boolean[width][height];
        Queue<HalfMapNode> queue = new LinkedList<>();
        for (HalfMapNode node : nodes) {
            if (isAccessible(node)) {
                visited[node.getX()][node.getY()] = true;
                queue.offer(node);
                break;
            }
        }
        while (!queue.isEmpty()) { //dfs
            HalfMapNode node = queue.poll();
            int x = node.getX();
            int y = node.getY();

            int [] rowDirection = {0, 0, 1, -1}; //right, left, up, down
            int [] colDirection = {1, -1, 0, 0};
            for (int i = 0; i < rowDirection.length; i++) {
                int adjX = x + rowDirection[i];
                int adjY = y + colDirection[i];
                if (adjX >= 0 && adjX < width && adjY >= 0 && adjY < height && !visited[adjX][adjY]) {
                    Optional<HalfMapNode> neighbor = findNodeAt(nodes, adjX, adjY);
                    if (neighbor.isPresent() && isAccessible(neighbor.get())) {
                        visited[adjX][adjY] = true;
                        queue.offer(neighbor.get());
                    }
                }
            }
        }
        int totalVisited = 0;
        int totalAccessible = 0;

        for (HalfMapNode node : nodes) {
            if (isAccessible(node)) {
                totalAccessible++;
            }
            if (visited[node.getX()][node.getY()]) {
                totalVisited++;
            }
        }
        return totalAccessible == totalVisited;
    }

    private boolean isAccessible(HalfMapNode node) {
        return !node.getTerrain().equals(ETerrain.Water);
    }

    private Optional<HalfMapNode> findNodeAt(List<HalfMapNode> nodes, int x, int y) {
        for (HalfMapNode node : nodes) {
            if (node.getX() == x && node.getY() == y) return Optional.of(node);
        }
        return Optional.empty();
    }

}
