package ai;

import static org.junit.jupiter.api.Assertions.*;

import map.ETerrain;
import map.FullMapNode;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

class CalculateEnemyPositionTest {
	private CalculateEnemyPosition calculator= new CalculateEnemyPosition();

	@Test
	void enemyPositionIsPresent_returnedFoundPosition() {
		List<FullMapNode> mapNodes = List.of(
				new FullMapNode(0,0, ETerrain.Grass, false, true,false,false,false),
				new FullMapNode(1,0, ETerrain.Grass, false, false,false,false,false),
				new FullMapNode(0,1, ETerrain.Mountain, false, false,false,false,false)
		);

		Optional<Position> enemyPosition = calculator.calculateEnemyPosition(mapNodes);
		assertEquals(Optional.of(new Position(0,0)), enemyPosition);
	}

	@Test
	void enemyPositionIsAbsent_returnedEmptyOptionalPosition() {
		List<FullMapNode> mapNodes = List.of(
				new FullMapNode(0,0, ETerrain.Grass, false, false,false,false,false),
				new FullMapNode(1,0, ETerrain.Grass, false, false,false,false,false),
				new FullMapNode(0,1, ETerrain.Mountain, false, false,false,false,false)
		);

		Optional<Position> enemyPosition = calculator.calculateEnemyPosition(mapNodes);
		assertEquals(Optional.empty(), enemyPosition);
	}

}
