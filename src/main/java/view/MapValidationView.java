package view;

import map.validation.Notification;
import model.AObservable;


public class MapValidationView implements IObserver<Notification> {
    @Override
    public void update(AObservable<Notification> o, Notification arg) {
            System.err.println("🚨Errors by map generation:");
            for (Exception error : arg.getErrors()) {
                System.err.println("Type of the error: " + error.getClass().getName());
                System.err.println("Error description : " + error.getMessage());
                System.err.println("Stacktrace: " + error.getStackTrace()[0]);
        }
    }
}
