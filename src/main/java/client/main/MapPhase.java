package client.main;

import controller.MapController;
import map.ClientMap;
import map.MapGeneration;
import network.ClientNetwork;
import network.EPlayerState;
import network.GameState;

public class MapPhase {
    public void generateAndSendMap(ClientNetwork network) {
        sendMap(generateMap(), network);
    }
    private ClientMap generateMap() {
        double grassPercentage = 0.48;
        double mountainPercentage = 0.1;
        double waterPercentage = 0.14;
        double fortPercentage = 0.12;
        int sizeX = 10;
        int sizeY = 5;

        MapController mapController = new MapController();

        MapGeneration mapGeneration = new MapGeneration();
        ClientMap map = mapGeneration.generateMap(sizeX, sizeY, grassPercentage,
                mountainPercentage, waterPercentage, fortPercentage);

        mapController.mapChanged(map);

        return map;
    }

    private void sendMap(ClientMap map, ClientNetwork network) {
        while (true) {
            GameState gameState = network.getState();

            if (gameState.getPlayerState() == EPlayerState.MustAct) {
                network.sendMap(map);
                break;
            }
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
