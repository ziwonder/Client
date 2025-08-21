package map.validation;

import map.HalfMapNode;

import java.util.List;

public interface IRuleValidator {
    void validate(List<HalfMapNode> mapNodes, Notification note);
}
