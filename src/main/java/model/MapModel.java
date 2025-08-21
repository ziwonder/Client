package model;

import map.IMapVisualisation;

public class MapModel extends AObservable<IMapVisualisation> {

    public void updateMap(IMapVisualisation map){
        super.notifyObservers(map);
    }

}
