package map;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ClientMapTest {
	
	private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

	@BeforeEach
	void setUp() throws Exception {
		System.setOut(new PrintStream(outputStream));
	}

	@AfterEach
	void tearDown() throws Exception {
		System.setOut(originalOut);
	}
	
	
	@Test
	void printClientMap_returnsExpectedSymbols() {
		List<HalfMapNode> nodes = List.of(
                new HalfMapNode(0, 0, ETerrain.Grass, false),
                new HalfMapNode(1, 0, ETerrain.Mountain, false),
                new HalfMapNode(0, 1, ETerrain.Water, false),
                new HalfMapNode(1, 1, ETerrain.Grass, true) 
        );
		ClientMap map = new ClientMap(nodes, 2, 2);
		map.printMap();
		
		String output = outputStream.toString();

        assertTrue(output.contains("🌳"));
        assertTrue(output.contains("⛰️")); 
        assertTrue(output.contains("💦")); 
        assertTrue(output.contains("🏰"));
	}

}
