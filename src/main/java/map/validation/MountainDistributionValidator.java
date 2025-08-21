package map.validation;

import exception.InvalidMountainDistributionException;
import map.ETerrain;
import map.HalfMapNode;

import java.util.List;

public class MountainDistributionValidator implements IRuleValidator {

    private double percentage;

    public MountainDistributionValidator(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public void validate(List<HalfMapNode> mapNodes, Notification note) {
        long mountain = mapNodes.stream()
                .filter(n -> n.getTerrain().equals(ETerrain.Mountain))
                .count();
        if (mountain < mapNodes.size() * percentage) {
            note.addError( new InvalidMountainDistributionException("Not enough mountain fields"));
        }
    }
}
