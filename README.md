# Treasure Hunt Client

A Java 21 command-line client that plays a treasure hunt game autonomously against another player through a game server.

## Features

- Generates and validates half maps with terrain and fort placement rules.
- Explores the map, searches for treasure, and targets the enemy fort.
- Uses Dijkstra pathfinding with terrain-based movement costs.
- Displays game progress in the console and logs events with SLF4J/Logback.
- Implemented using best practices and OOP principles.

## Build and run

Requires **JDK 21**, internet access to download dependencies, and a compatible game server with an existing game ID. Gradle 8.8 is included through the wrapper.

```sh
./gradlew build
java -jar build/libs/Client.jar ignored <server-url> <game-id>
```

On Windows, use `./gradlew.bat build`. Replace `<server-url>` and `<game-id>` with your server's values. The server were implemented an d provided by the course. The first argument is a required placeholder: the client reads the server URL and game ID from the second and third arguments. Player registration details are configured in `src/main/java/client/main/RegistrationPhase.java`.

## Tests

```sh
./gradlew test
```

JUnit 5 and Mockito tests cover map generation, validation, and AI movement.

