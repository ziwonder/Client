package network;

import map.ClientMap;
import map.HalfMapNode;
import map.FullMap;
import messagesbase.messagesfromclient.EMove;
import messagesbase.messagesfromclient.ETerrain;
import messagesbase.messagesfromclient.PlayerHalfMap;
import messagesbase.messagesfromclient.PlayerHalfMapNode;
import messagesbase.messagesfromserver.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Converter {

    public PlayerHalfMap convertMap(ClientMap halfMapPlayer, String playerId) {
        List<HalfMapNode> nodes = halfMapPlayer.getNodesList();
        Collection<PlayerHalfMapNode> nodeCollection = new ArrayList<>();

        for (HalfMapNode node : nodes) {
            PlayerHalfMapNode playerHalfMapNode = new PlayerHalfMapNode(node.getX(),
                    node.getY(), node.isFortPresent(), convertETerrain(node.getTerrain()));
            nodeCollection.add(playerHalfMapNode);

        }
        return new PlayerHalfMap(playerId, nodeCollection);
    }

    private ETerrain convertETerrain(map.ETerrain terrain) {
        return ETerrain.valueOf(terrain.name());
    }


    public FullMap convertMap(messagesbase.messagesfromserver.FullMap fullMap) {
        Collection<FullMapNode> nodes = fullMap.getMapNodes();
        List<map.FullMapNode> nodeCollection = new ArrayList<>();
        for (FullMapNode node : nodes) {

            map.FullMapNode mapNode = new map.FullMapNode(node.getX(), node.getY(),
                    convertETerrain(node.getTerrain()), isMyPlayerHere(node),
                            isEnemyPlayerHere(node), hasMyTreasure(node),
                            hasMyFort(node), hasEnemyFort(node));
            nodeCollection.add(mapNode);
        }
        return new FullMap(nodeCollection);
    }

    private map.ETerrain convertETerrain(ETerrain terrain) {
        return map.ETerrain.valueOf(terrain.name());
    }

    private boolean isMyPlayerHere(FullMapNode node) {
        EPlayerPositionState state = node.getPlayerPositionState();
        return state == EPlayerPositionState.MyPlayerPosition || state == EPlayerPositionState.BothPlayerPosition;
    }

    private boolean isEnemyPlayerHere(FullMapNode node) {
        EPlayerPositionState state = node.getPlayerPositionState();
        return state == EPlayerPositionState.EnemyPlayerPosition || state == EPlayerPositionState.BothPlayerPosition;
    }

    private boolean hasMyTreasure(FullMapNode node) {
        return node.getTreasureState() == ETreasureState.MyTreasureIsPresent;
    }

    private boolean hasMyFort(FullMapNode node) {
        return node.getFortState() == EFortState.MyFortPresent;
    }

    private boolean hasEnemyFort(FullMapNode node) {
        return node.getFortState() == EFortState.EnemyFortPresent;
    }


    public EMove convertEMove(ai.EMove move) {
        return EMove.valueOf(move.name());
    }

    public GameState convertGameState(messagesbase.messagesfromserver.GameState gameState, String playerId) {
        FullMap map = convertMap(gameState.getMap());
        EPlayerState playerState = convertEPlayerState(gameState.getPlayers().stream()
                .filter(p -> p.getUniquePlayerID().equals(playerId))
                .map(PlayerState::getState)
                .findFirst().orElse(EPlayerGameState.MustWait));
        boolean isTreasureCollected = gameState.getPlayers().stream()
                .filter(p -> p.getUniquePlayerID().equals(playerId))
                .map(PlayerState::hasCollectedTreasure).findFirst().orElse(false);
        String gameStateId = gameState.getGameStateId();
        return new GameState(gameStateId, map, playerState, isTreasureCollected);
    }

    private EPlayerState convertEPlayerState(EPlayerGameState playerGameState) {
        return EPlayerState.valueOf(playerGameState.name());
    }
}
