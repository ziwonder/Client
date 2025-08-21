package client.main;

import ai.AIMovement;
import ai.CalculateEnemyPosition;
import ai.EMove;
import ai.Position;
import controller.EndStateController;
import controller.MapController;
import network.ClientNetwork;
import network.EPlayerState;
import network.GameState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.Optional;

public class MovementPhase {
    private final AIMovement ai = new AIMovement();
    private Position enemyPosition;
    private final MapController mapController = new MapController();
    private GameState gameState = new GameState();
    private final EndStateController endStateController = new EndStateController();
    private int rounds = 0;
    private static final Logger logger = LoggerFactory.getLogger(MovementPhase.class);

    public void exploreMap(ClientNetwork network) {
        logger.info("Client started to explore the map");
        int ENEMY_REVEAL_ROUND = 8;
        while (true) {

            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            updateGameState(network);

            if (rounds == ENEMY_REVEAL_ROUND) {
               trackEnemyPosition();
            }

            if (gameState.getPlayerState() == EPlayerState.Lost ||
                    gameState.getPlayerState() == EPlayerState.Won) {

                endStateController.endGame(gameState.getPlayerState());
                logger.debug("Rounds to finish the game: {}", rounds);
                return;
            }

            if (gameState.getPlayerState() == EPlayerState.MustAct) {
                handlePlayerMove(network);
            }
        }
    }

    private void updateGameState(ClientNetwork network) {
        GameState gameStateNew = network.getState();
        if (!Objects.equals(gameStateNew.getGameStateId(), gameState.getGameStateId())) {
            gameState = gameStateNew;

            if (gameState.isTreasureCollected()) {
                gameState.getMap().setTreasurePosition(ai.getTreasurePosition());
            }
            mapController.mapChanged(gameState.getMap());
        }
    }

    private void trackEnemyPosition() {
        CalculateEnemyPosition enemyPart = new CalculateEnemyPosition();
        Optional<Position> calculatedEnemyPosition= enemyPart.calculateEnemyPosition(gameState.getMap().getNodeList());
        if (calculatedEnemyPosition.isPresent()) {
            enemyPosition = calculatedEnemyPosition.get();
            logger.debug("Enemy position after 8th round: {}", enemyPosition);
        }
    }



    private void handlePlayerMove(ClientNetwork network) {
        Optional<Position> optionalEnemyPosition = Optional.ofNullable(enemyPosition);
        if (optionalEnemyPosition.isPresent()) {
            gameState.getMap().updateEnemyPart(enemyPosition);
        }
        EMove move = ai.move(gameState.getMap(), gameState.isTreasureCollected());
        network.sendMove(move);
        rounds++;
    }
}
