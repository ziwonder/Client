package client.main;

import network.ClientNetwork;

public class MainClient {

	public static void main(String[] args) {

		String serverBaseUrl = args[1];
		String gameId = args[2];

		ClientNetwork network = new ClientNetwork(serverBaseUrl, gameId);

		RegistrationPhase registrationPhase = new RegistrationPhase();
		registrationPhase.registerPlayer(network);

		MapPhase mapPhase = new MapPhase();
		mapPhase.generateAndSendMap(network);

		MovementPhase movementPhase = new MovementPhase();
		movementPhase.exploreMap(network);

		System.exit(0);
	}
}
