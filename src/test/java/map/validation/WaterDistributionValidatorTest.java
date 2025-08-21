package map.validation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.InvalidWaterDistributionException;
import map.ETerrain;
import map.HalfMapNode;

class WaterDistributionValidatorTest {
	private final double percentage = 0.2;
    private WaterDistributionValidator validator = new WaterDistributionValidator(percentage);
    private Notification notification;

    @BeforeEach
    void setUp() {
        notification = new Notification();
    }

	@Test
    void WaterDistributionBelowThreshold_thenAddsError() {
        List<HalfMapNode> nodes = List.of(
                new HalfMapNode(0, 0, ETerrain.Mountain, false),
                new HalfMapNode(0, 1, ETerrain.Water, false),
                new HalfMapNode(1, 0, ETerrain.Grass, false),
                new HalfMapNode(1, 1, ETerrain.Grass, false),
                new HalfMapNode(2, 0, ETerrain.Grass, false),
                new HalfMapNode(0, 2, ETerrain.Grass, false)
        );

        validator.validate(nodes, notification);

        assertTrue(notification.hasError());
        assertEquals(1, notification.getErrors().size());
        assertInstanceOf(InvalidWaterDistributionException.class, notification.getErrors().getFirst());
    }

    @Test
    void EnoughWaterFields_thenAddsNoError() {
        List<HalfMapNode> nodes = List.of(
                new HalfMapNode(0, 0, ETerrain.Grass, false),
                new HalfMapNode(0, 1, ETerrain.Grass, false),
                new HalfMapNode(1, 0, ETerrain.Water, false),
                new HalfMapNode(1, 1, ETerrain.Grass, false)
        );
        validator.validate(nodes, notification);

        assertFalse(notification.hasError());
    }

}
