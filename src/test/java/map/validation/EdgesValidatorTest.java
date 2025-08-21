package map.validation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import map.ETerrain;
import map.HalfMapNode;

class EdgesValidatorTest {
	
	private int height = 5;
	private int width = 5; 
	private EdgesValidator validator = new EdgesValidator(height, width);
	private Notification notification;
	
	@BeforeEach
	void setUp() throws Exception {
		notification = new Notification();
	}
	@Test
	void EdgesAreAccessible_thenAddsNoError() {
		List<HalfMapNode> nodes = generateTestMap(width, height, ETerrain.Grass);

		validator.validate(nodes, notification);

		assertFalse(notification.hasError());
	}
	@Test
	void EdgesAreNotAccessible_thenAddsError() {
		List<HalfMapNode> nodes = generateTestMap(width, height, ETerrain.Grass);
		for (int y = 0; y < 3; y++) {
			int finalY = y;
			nodes.removeIf(node -> node.getX() == 0 && node.getY() == finalY);
			nodes.add(new HalfMapNode(0, y, ETerrain.Water, false));
		}

		validator.validate(nodes, notification);
		assertTrue(notification.hasError());
	}
	private List<HalfMapNode> generateTestMap(int width, int height, ETerrain terrain) {
        List<HalfMapNode> map = new ArrayList<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                map.add(new HalfMapNode(x, y, terrain, false));
            }
        }
        return map;
    }

}
