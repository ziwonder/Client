package map.validation;

import exception.InvalidFortDistributionException;
import map.HalfMapNode;

import java.util.List;

public class FortDistributionValidator implements IRuleValidator {
    private double percentage;

    public FortDistributionValidator(double percentage) {
        this.percentage = percentage;
    }

    @Override
    public void validate(List<HalfMapNode> mapNodes, Notification note) {
        long fort = mapNodes.stream()
                .filter(HalfMapNode::isFortPresent)
                .count();
        if (fort != mapNodes.size() * percentage) {
            note.addError( new InvalidFortDistributionException("Not enough forts placed"));
        }
    }
}
