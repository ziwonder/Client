package map.validation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exception.InvalidMountainDistributionException;
import map.ETerrain;
import map.HalfMapNode;

class MountainDistributionValidatorTest {
	private final double percentage = 0.2;
    private MountainDistributionValidator validator = new MountainDistributionValidator(percentage);
    private Notification notification;

    @BeforeEach
    void setUp() {
        notification = new Notification();
    }

	@Test
    void MountainDistributionBelowThreshold_thenAddsError() {
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
        assertInstanceOf(InvalidMountainDistributionException.class, notification.getErrors().getFirst());
    }

    @Test
    void EnoughMountainFields_thenAddsNoError() {
        List<HalfMapNode> nodes = List.of(
                new HalfMapNode(0, 0, ETerrain.Grass, false),
                new HalfMapNode(0, 1, ETerrain.Grass, false),
                new HalfMapNode(1, 0, ETerrain.Mountain, false),
                new HalfMapNode(1, 1, ETerrain.Grass, false)
        );
        validator.validate(nodes, notification);

        assertFalse(notification.hasError());
    }

}
