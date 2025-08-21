package network;

import ai.EMove;
import map.ClientMap;
import messagesbase.ResponseEnvelope;
import messagesbase.UniquePlayerIdentifier;
import messagesbase.messagesfromclient.*;
import messagesbase.messagesfromserver.GameState;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import org.slf4j.Logger;

public class ClientNetwork {
    private final WebClient baseWebClient;
    private final String gameId;
    private String playerId;
    private final Converter converter;
    private static final Logger logger = LoggerFactory.getLogger(ClientNetwork.class);

    public ClientNetwork(String serverBaseUrl, String gameId) {
        this.baseWebClient = WebClient.builder().baseUrl(serverBaseUrl + "/games")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_XML_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_XML_VALUE)
                .build();
        this.gameId = gameId;
        this.playerId = "";
        this.converter = new Converter();
    }

    public void registerPlayer(PlayerRegistration playerReg) {
        Mono<ResponseEnvelope<UniquePlayerIdentifier>> webAccess = baseWebClient
                .method(HttpMethod.POST)
                .uri("/" + gameId + "/players")
                .body(BodyInserters.fromValue(playerReg))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});

        ResponseEnvelope<UniquePlayerIdentifier> resultReg = webAccess.block();
        if (resultReg.getState() == ERequestState.Error) {
            logger.error("Registration failed, received message: {}", resultReg.getExceptionMessage());
        } else {
            UniquePlayerIdentifier ownClientPlayerID =  resultReg.getData().get();
            playerId = ownClientPlayerID.getUniquePlayerID();
            logger.info("Successfully registered player {} 🎉", playerId);
        }
    }

    public network.GameState getState() {
        Mono<ResponseEnvelope<GameState>> webAccess = baseWebClient
                .method(HttpMethod.GET)
                .uri("/" + gameId + "/states/" + playerId)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {
                });
        ResponseEnvelope<GameState> requestResult = webAccess.block();
        if (requestResult.getState() == ERequestState.Error) {
            logger.error("Failed to receive a game state, received message: {}", requestResult.getExceptionMessage());
        }
        else {
            GameState currentServerGameState = requestResult.getData().get();
            return converter.convertGameState(currentServerGameState, playerId);
        }
        return new network.GameState();
    }


    public void sendMap(ClientMap halfMapPlayer) {
        PlayerHalfMap playerHalfMap = converter.convertMap(halfMapPlayer, playerId);

        Mono<ResponseEnvelope<UniquePlayerIdentifier>> webAccess = baseWebClient
                .method(HttpMethod.POST)
                .uri("/" + gameId + "/halfmaps")
                .body(BodyInserters.fromValue(playerHalfMap))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
        ResponseEnvelope<UniquePlayerIdentifier> resultMap = webAccess.block();
        if (resultMap.getState() == ERequestState.Error) {
            logger.error("Failed to send a map, received message: {}", resultMap.getExceptionMessage());
        }
        else {
            logger.info("Successfully sent a map");
        }
    }

    public void sendMove(EMove move) {
        PlayerMove newMove = PlayerMove.of(playerId, converter.convertEMove(move));

        Mono<ResponseEnvelope<UniquePlayerIdentifier>> webAccess = baseWebClient
                .method(HttpMethod.POST)
                .uri("/" + gameId + "/moves")
                .body(BodyInserters.fromValue(newMove))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<>() {});
        ResponseEnvelope<UniquePlayerIdentifier> resultMove = webAccess.block();
        if (resultMove.getState() == ERequestState.Error) {
            logger.error("Failed to send a move to server, received message: {}", resultMove.getExceptionMessage());
        }
    }

}
