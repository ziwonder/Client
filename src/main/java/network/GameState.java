package network;

import map.FullMap;

public class GameState {
    private final String gameStateId;
    private FullMap map;
    private EPlayerState playerState;
    private boolean isTreasureCollected;


    public GameState(String gameStateId, FullMap map, EPlayerState playerState,
                     boolean isCollectedTreasure) {
        this.gameStateId = gameStateId;
        this.map = map;
        this.playerState = playerState;
        this.isTreasureCollected = isCollectedTreasure;
    }

    public GameState() {
        this.gameStateId = "";
    }

    public String getGameStateId() {
        return gameStateId;
    }

    public FullMap getMap() {
        return map;
    }

    public EPlayerState getPlayerState() {
        return playerState;
    }

    public boolean isTreasureCollected() {
        return isTreasureCollected;
    }

}
