package controller;

import map.validation.Notification;
import model.MapValidationModel;
import view.MapValidationView;

public class MapValidationController {
    private final MapValidationModel model;
    private MapValidationView view;

    public MapValidationController() {
        this.model = new MapValidationModel();
        this.view = new MapValidationView();
        model.addObserver(view);
    }

    public void MapValidationErrorsOccurred(Notification note) {
        this.model.setNotification(note);
    }
}
