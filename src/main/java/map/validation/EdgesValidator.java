package map.validation;

import exception.EdgeInaccessibleException;
import map.ETerrain;
import map.HalfMapNode;

import java.util.List;

public class EdgesValidator implements IRuleValidator {

    private final int height;
    private final int width;

    public EdgesValidator(int height, int width) {
        this.height = height;
        this.width = width;
    }

    @Override
    public void validate(List<HalfMapNode> map, Notification note) {
        if (!areEdgesAccessible(map)) {
            note.addError(new EdgeInaccessibleException("Edge are not accessible"));
        }
    }

    private boolean areEdgesAccessible(List<HalfMapNode> nodes) {
        return (isEdgeAccessible(nodes, 0, true )) &&
                (isEdgeAccessible(nodes, 0, false )) &&
                (isEdgeAccessible(nodes, width - 1, false)) &&
                (isEdgeAccessible(nodes, height - 1, true));
    }

    private boolean isEdgeAccessible(List<HalfMapNode> nodes,
                                     int start,boolean horizontally) {
        int accessible = 0;
        int total = 0;
        double accessibilityPercentage = 0.51;
        for (HalfMapNode node : nodes) {
            if (node.getX() == start && !horizontally
                    || node.getY() == start && horizontally) {
                total++;
                if (isAccessible(node)) {
                    accessible++;
                }
            }
        }
        return accessible > total*accessibilityPercentage;
    }

    private boolean isAccessible(HalfMapNode node) {
        return !node.getTerrain().equals(ETerrain.Water);
    }

}
