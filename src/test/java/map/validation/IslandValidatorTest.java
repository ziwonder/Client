package map.validation;

import static org.junit.jupiter.api.Assertions.*;

import exception.IslandDetectedException;
import map.ETerrain;
import map.HalfMapNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class IslandValidatorTest {
	private int height = 3;
	private int width = 4;
	private IslandValidator validator = new IslandValidator(height, width);
    private Notification notification;
    
	@BeforeEach
	void setUp() throws Exception {
		notification = new Notification();
	}

	@Test
	void islandFound_thenAddsError() {
		List<HalfMapNode> nodes = List.of(
				new HalfMapNode(0, 0, ETerrain.Water, false),
				new HalfMapNode(0, 1, ETerrain.Water, false),
				new HalfMapNode(1, 0, ETerrain.Water, false),
				new HalfMapNode(2, 1, ETerrain.Water, false),
				new HalfMapNode(1, 1, ETerrain.Grass, false),
				new HalfMapNode(1, 2, ETerrain.Water, false),
				new HalfMapNode(2, 0, ETerrain.Water, false),
				new HalfMapNode(0, 2, ETerrain.Water, false),
				new HalfMapNode(2, 2, ETerrain.Water, false),
				new HalfMapNode(3, 0, ETerrain.Grass, false),
				new HalfMapNode(3, 1, ETerrain.Grass, false),
				new HalfMapNode(3, 2, ETerrain.Mountain, false)
		);

		validator.validate(nodes, notification);

		assertTrue(notification.hasError());
		assertEquals(1, notification.getErrors().size());
		assertInstanceOf(IslandDetectedException.class, notification.getErrors().getFirst());
	}

	@Test
	void islandNotFound_thenAddsNoError() {
		List<HalfMapNode> nodes = List.of(
				new HalfMapNode(0, 0, ETerrain.Water, false),
				new HalfMapNode(0, 1, ETerrain.Grass, false),
				new HalfMapNode(1, 0, ETerrain.Grass, false),
				new HalfMapNode(1, 1, ETerrain.Grass, false),
				new HalfMapNode(2, 1, ETerrain.Grass, false),
				new HalfMapNode(1, 2, ETerrain.Grass, false),
				new HalfMapNode(2, 0, ETerrain.Water, false),
				new HalfMapNode(0, 2, ETerrain.Mountain, false),
				new HalfMapNode(2, 2, ETerrain.Water, false),
				new HalfMapNode(3, 0, ETerrain.Grass, false),
				new HalfMapNode(3, 1, ETerrain.Grass, false),
				new HalfMapNode(3, 2, ETerrain.Mountain, false)
		);

		validator.validate(nodes, notification);

		assertFalse(notification.hasError());
	}



}
