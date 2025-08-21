package ai;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import map.ETerrain;
import map.FullMapNode;

class TargetFinderTest {
	
	private TargetFinder targetFinder;
    private List<FullMapNode> map;
    private Set<Position> visited;

	@BeforeEach
	void setUp() throws Exception {
		targetFinder = new TargetFinder();
		map = new ArrayList<FullMapNode>();
		visited = new HashSet<Position>();
	}

	@Test
	void testFindsMountainsWithTheMostGrassCount() {
		Position actualPosition = new Position(0, 0);
		map.add(new FullMapNode(0, 1, ETerrain.Grass, false, false, false, false, false));
		map.add(new FullMapNode(1, 0, ETerrain.Mountain, false, false, false, false, false));
		map.add(new FullMapNode(1, 1, ETerrain.Grass, false, false, false, false, false));
		map.add(new FullMapNode(2, 0, ETerrain.Mountain, false, false, false, false, false));
		map.add(new FullMapNode(2, 2, ETerrain.Mountain, false, false, false, false, false));
		map.add(new FullMapNode(0, 2, ETerrain.Mountain, false, false, false, false, false));
		map.add(new FullMapNode(2, 1, ETerrain.Grass, false, false, false, false, false));
		map.add(new FullMapNode(1, 2, ETerrain.Grass, false, false, false, false, false));
		map.add(new FullMapNode(0, 3, ETerrain.Grass, false, false, false, false, false));
		map.add(new FullMapNode(1, 3, ETerrain.Grass, false, false, false, false, false));
		map.add(new FullMapNode(2, 3, ETerrain.Grass, false, false, false, false, false));
		
		
		targetFinder.findNextTarget(map, actualPosition, visited);
		assertEquals(new Position(0, 2), targetFinder.getCurrentTarget());
	}
	
	@Test 
	void testFindsClosestGrassWhenNoMountains() {
		Position actualPosition = new Position(0, 0);
		
		map.add(new FullMapNode(0, 1, ETerrain.Grass, false, false, false, false, false));
		map.add(new FullMapNode(1, 0, ETerrain.Grass, false, false, false, false, false));
		map.add(new FullMapNode(1, 1, ETerrain.Grass, false, false, false, false, false));
		map.add(new FullMapNode(2, 0, ETerrain.Mountain, false, false, false, false, false));
		map.add(new FullMapNode(0, 2, ETerrain.Mountain, false, false, false, false, false));
		map.add(new FullMapNode(2, 1, ETerrain.Grass, false, false, false, false, false));
		map.add(new FullMapNode(2, 2, ETerrain.Mountain, false, false, false, false, false));
		map.add(new FullMapNode(1, 2, ETerrain.Grass, false, false, false, false, false));
		map.add(new FullMapNode(0, 3, ETerrain.Grass, false, false, false, false, false));
		map.add(new FullMapNode(1, 3, ETerrain.Grass, false, false, false, false, false));
		map.add(new FullMapNode(2, 3, ETerrain.Grass, false, false, false, false, false));
		map.add(new FullMapNode(3, 3, ETerrain.Water, false, false, false, false, false));
		
		visited.addAll(Arrays.asList(new Position(2, 2), new Position(2, 1), new Position(2, 3),
				new Position(1, 3), new Position(1, 2), new Position(1, 1)));
		targetFinder.findNextTarget(map, actualPosition, visited);
		assertEquals(new Position(0, 1), targetFinder.getCurrentTarget());
	}
	
	@Test 
	void testFindsNoTarget() {
Position actualPosition = new Position(0, 0);
		
		map.add(new FullMapNode(0, 1, ETerrain.Grass, false, false, false, false, false));
		map.add(new FullMapNode(1, 0, ETerrain.Grass, false, false, false, false, false));
		map.add(new FullMapNode(1, 1, ETerrain.Grass, false, false, false, false, false));
		map.add(new FullMapNode(2, 0, ETerrain.Mountain, false, false, false, false, false));
		map.add(new FullMapNode(0, 2, ETerrain.Mountain, false, false, false, false, false));
		map.add(new FullMapNode(2, 1, ETerrain.Grass, false, false, false, false, false));
		map.add(new FullMapNode(2, 2, ETerrain.Mountain, false, false, false, false, false));
		map.add(new FullMapNode(1, 2, ETerrain.Grass, false, false, false, false, false));
		map.add(new FullMapNode(0, 3, ETerrain.Grass, false, false, false, false, false));
		map.add(new FullMapNode(1, 3, ETerrain.Grass, false, false, false, false, false));
		map.add(new FullMapNode(2, 3, ETerrain.Grass, false, false, false, false, false));
		
		visited.addAll(Arrays.asList(new Position(0, 1),
		        new Position(1, 0), 
		        new Position(1, 1), 
		        new Position(2, 1), 
		        new Position(1, 2),
		        new Position(0, 3),
		        new Position(1, 3), 
		        new Position(2, 3), 
		        new Position(2, 0), 
		        new Position(0, 2),
		        new Position(2, 2)));
		targetFinder.findNextTarget(map, actualPosition, visited);
		assertEquals(actualPosition, targetFinder.getCurrentTarget());
	}
	@Test
	void setTarget_shouldReturnSetTargetWithGetter() {
		Position position = new Position(0,0);
		targetFinder.setCurrentTarget(position);
		assertEquals(position, targetFinder.getCurrentTarget());
	}
	

}
