package map.validation;

import map.ClientMap;


import java.util.*;

public class MapValidation {

    private Notification note = new Notification();
    private List<IRuleValidator> ruleValidators;

    public MapValidation(List<IRuleValidator> ruleValidators) {
        this.ruleValidators = ruleValidators;
    }

    public boolean isMapValid(ClientMap map) {

        for (IRuleValidator validator : ruleValidators) {
            validator.validate(map.getNodesList(), note);
        }
        
        return !note.hasError();
    }

    public Notification getNotification() {
        return note;
    }

}
