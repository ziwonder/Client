package model;

import map.validation.Notification;

public class MapValidationModel extends AObservable<Notification> {

    public void setNotification(Notification notification) {
        super.notifyObservers(notification);
    }
}
