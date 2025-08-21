package map.validation;

import exception.InvalidWaterDistributionException;
import map.ETerrain;
import map.HalfMapNode;

import java.util.List;

public class WaterDistributionValidator implements IRuleValidator {
    private double percentage;

    public WaterDistributionValidator(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public void validate(List<HalfMapNode> mapNodes, Notification note) {
        long water = mapNodes.stream()
                .filter(n -> n.getTerrain().equals(ETerrain.Water))
                .count();
        if (water < mapNodes.size() * percentage) {
            note.addError( new InvalidWaterDistributionException("Not enough water fields"));
        }
    }
}
