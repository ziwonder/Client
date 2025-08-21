package map.validation;

import exception.InvalidGrassDistributionException;
import map.ETerrain;
import map.HalfMapNode;

import java.util.List;

public class GrassDistributionValidator implements IRuleValidator {

    private double percentage;

    public GrassDistributionValidator(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public void validate(List<HalfMapNode> mapNodes, Notification note) {
        long grass = mapNodes.stream()
                .filter(n -> n.getTerrain().equals(ETerrain.Grass))
                .count();
        if (grass < mapNodes.size() * percentage) {
            note.addError( new InvalidGrassDistributionException("Not enough grass fields"));
        }
    }
}
