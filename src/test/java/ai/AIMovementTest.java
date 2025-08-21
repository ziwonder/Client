package ai;

import static org.junit.jupiter.api.Assertions.*;

import map.FullMap;
import map.ETerrain;
import map.FullMapNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.NoMoveFoundException;

import java.util.List;

class AIMovementTest {
	private AIMovement ai;

	@BeforeEach
	void setUp() throws Exception {
		ai = new AIMovement();

	}

	@Test
	void NoTreasureOrFortWereDetected_moevesToNextTarget() {
		List<FullMapNode> nodes = List.of(
				new FullMapNode(0,0, ETerrain.Water, false, false,false,false,false),
				new FullMapNode(1,0, ETerrain.Grass, false, false,false,false,false),
				new FullMapNode(0,1, ETerrain.Grass, true, false,false,false,false),
				new FullMapNode(1,1, ETerrain.Grass, false, false,false,true,false)
		);
		FullMap map = new FullMap(nodes);
		EMove move = ai.move(map, false);
		assertEquals(EMove.Right, move);
	}
	
	@Test
	void treasureWasDetected_setsTreasurePosition() {
		List<FullMapNode> nodes = List.of(
				new FullMapNode(0,0, ETerrain.Water, false, false,false,false,false),
				new FullMapNode(1,0, ETerrain.Grass, false, false,false,false,false),
				new FullMapNode(0,1, ETerrain.Grass, true, false,false,false,false),
				new FullMapNode(1,1, ETerrain.Grass, false, false,false,false,false),
				new FullMapNode(2,0, ETerrain.Grass, false, false,true,true,false),
				new FullMapNode(2,1, ETerrain.Grass, false, false,false,false,false)
		);
		FullMap map = new FullMap(nodes);
		ai.move(map, false);
		assertEquals(new Position(2,0), ai.getTreasurePosition());
	}
	
	@Test
	void fortWasDetected_setsFortPosition() {
		List<FullMapNode> nodes = List.of(
				new FullMapNode(0,0, ETerrain.Water, false, false,false,false,false),
				new FullMapNode(1,0, ETerrain.Grass, true, false,false,false,false),
				new FullMapNode(0,1, ETerrain.Grass, false, false,false,true,false),
				new FullMapNode(1,1, ETerrain.Mountain, false, false,false,false,false),
				new FullMapNode(2,0, ETerrain.Grass, false, false,false,false,true),
				new FullMapNode(2,1, ETerrain.Grass, false, false,false,false,false)
		);
		FullMap map = new FullMap(nodes);
		EMove move = ai.move(map, true);
		assertEquals(EMove.Right, move);
	}
	
	@Test
	void noMoveClaculated_throwsNoMoveFoundException() {
		List<FullMapNode> nodes = List.of(
				new FullMapNode(0,0, ETerrain.Water, false, false,false,false,false),
				new FullMapNode(1,0, ETerrain.Grass, true, false,false,false,false),
				new FullMapNode(0,1, ETerrain.Grass, false, false,false,true,false),
				new FullMapNode(1,1, ETerrain.Water, false, false,false,false,false),
				new FullMapNode(2,0, ETerrain.Grass, false, false,false,false,true),
				new FullMapNode(2,1, ETerrain.Grass, false, false,false,false,false)
		);
		FullMap map = new FullMap(nodes);
		NoMoveFoundException exception = assertThrows(NoMoveFoundException.class,
				() -> ai.move(map, false));
		assertEquals("AI did not find next move to send - actions list is empty", exception.getMessage());
	} 

}
