package controller;

import map.IMapVisualisation;
import model.MapModel;
import view.GameProcessView;

public class MapController {

    private final MapModel model;
    private GameProcessView view;

    public MapController() {
        this.model = new MapModel();
        this.view = new GameProcessView();
        model.addObserver(view);
    }

    public void mapChanged(IMapVisualisation map) {
        this.model.updateMap(map);
    }
}
