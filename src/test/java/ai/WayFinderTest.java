package ai;

import static org.junit.jupiter.api.Assertions.*;

import map.ETerrain;
import map.FullMap;
import map.FullMapNode;
import org.junit.jupiter.api.Test;

import java.util.List;

class WayFinderTest {
	
	private WayFinder finder = new WayFinder();

	@Test
	void aimIsReachable_returnsNotEmptyList() {
		List<FullMapNode> nodes = List.of(
				new FullMapNode(0,0, ETerrain.Grass, false, false,false,false,false),
				new FullMapNode(1,0, ETerrain.Mountain, false, false,false,false,false),
				new FullMapNode(2,0, ETerrain.Grass, false, false,false,false,false),
				new FullMapNode(0,1, ETerrain.Grass, false, false,false,false,false),
				new FullMapNode(1,1, ETerrain.Water, false, false,false,false,false),
				new FullMapNode(2,1, ETerrain.Grass, false, false,false,false,false),
				new FullMapNode(0,2, ETerrain.Grass, false, false,false,false,false),
				new FullMapNode(1,2, ETerrain.Grass, false, false,false,false,false),
				new FullMapNode(2,2, ETerrain.Grass, false, false,false,false,false)
		);
		FullMap map = new FullMap(nodes);
		List<FullMapNode> expected = List.of(
				new FullMapNode(2,2, ETerrain.Grass, false, false,false,false,false),
				new FullMapNode(1,2, ETerrain.Grass, false, false,false,false,false),
				new FullMapNode(0,2, ETerrain.Grass, false, false,false,false,false),
				new FullMapNode(0,1, ETerrain.Grass, false, false,false,false,false),
				new FullMapNode(0,0, ETerrain.Grass, false, false,false,false,false)
				
		);
		List<FullMapNode> result = finder.findWay(new Position(0,0), new Position(2,2), map);
		
		List<Position> expectedPositions = expected.stream()
		        .map(n -> new Position(n.getX(), n.getY()))
		        .toList();

		List<Position> resultPositions = result.stream()
		        .map(n -> new Position(n.getX(), n.getY()))
		        .toList();

		assertFalse(result.isEmpty());
		assertEquals(expectedPositions,resultPositions);
	}

	@Test
	void aimIsNotReachable_throwsPathNotFoundException() {
		List<FullMapNode> nodes = List.of(
				new FullMapNode(0,0, ETerrain.Water, false, false,false,false,false),
				new FullMapNode(1,0, ETerrain.Grass, false, false,false,false,false),
				new FullMapNode(0,1, ETerrain.Grass, false, false,false,false,false),
				new FullMapNode(1,1, ETerrain.Water, false, false,false,false,false)
		);
		FullMap map = new FullMap(nodes);
		Position targetPosition = new Position(1,0);
		Position actualPosition = new Position(0,1);
		List<FullMapNode> result = finder.findWay(targetPosition, actualPosition , map);
		assertTrue(result.isEmpty());
	}
	

}
