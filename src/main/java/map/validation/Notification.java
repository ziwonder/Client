package map.validation;

import java.util.ArrayList;
import java.util.List;

public class Notification {
    private List<Exception> errors = new ArrayList<>();

    public void addError(Exception e) {
        errors.add(e);
    }
    public boolean hasError() {
        return !errors.isEmpty();
    }

    public List<Exception> getErrors() {
        return errors;
    }
}
