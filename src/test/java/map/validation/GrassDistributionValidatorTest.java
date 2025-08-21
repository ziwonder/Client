package map.validation;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import exception.InvalidGrassDistributionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import map.ETerrain;
import map.HalfMapNode;

class GrassDistributionValidatorTest {
    private final double percentage = 0.48;
    private GrassDistributionValidator validator = new GrassDistributionValidator(percentage);
    private Notification notification;

    @BeforeEach
    void setUp() {
        notification = new Notification();
    }

	@Test
    void GrassDistributionBelowThreshold_thenAddsError() {
        List<HalfMapNode> nodes = List.of(
                new HalfMapNode(0, 0, ETerrain.Mountain, false),
                new HalfMapNode(0, 1, ETerrain.Water, false),
                new HalfMapNode(1, 0, ETerrain.Mountain, false),
                new HalfMapNode(1, 1, ETerrain.Grass, false)
        );

        validator.validate(nodes, notification);

        assertTrue(notification.hasError());
        assertEquals(1, notification.getErrors().size());
        assertInstanceOf(InvalidGrassDistributionException.class, notification.getErrors().getFirst());
    }

    @Test
    void EnoughGrassFields_thenAddsNoError() {
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
