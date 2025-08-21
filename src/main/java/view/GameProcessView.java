package view;

import map.ClientMap;
import map.FullMap;
import map.IMapVisualisation;
import model.AObservable;

public class GameProcessView implements IObserver<IMapVisualisation> {


    @Override
    public void update(AObservable<IMapVisualisation> o, IMapVisualisation arg) {
        System.out.println("My Player 🦸 with Fort 🏰 vs Enemy 🦹 with Fort 🏯");
        if (arg instanceof FullMap map) {
            System.out.println("Map:");
            map.printMap();
        }
        if (arg instanceof ClientMap map) {
            System.out.println("Generated half map by client:");
            map.printMap();
        }
    }
}
