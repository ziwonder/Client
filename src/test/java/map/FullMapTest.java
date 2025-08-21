package map;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FullMapTest {

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
	void printFullMap_returnsExpectedSymbols() {
		List<FullMapNode> nodes = List.of(
				new FullMapNode(0,0, ETerrain.Water, false, false,false,false,false),
				new FullMapNode(1,0, ETerrain.Grass, false, false,false,false,false),
				new FullMapNode(0,1, ETerrain.Grass, true, false,false,false,false),
				new FullMapNode(1,1, ETerrain.Mountain, false, false,false,false,false),
				new FullMapNode(2,0, ETerrain.Grass, false, false,true,false,false),
				new FullMapNode(2,1, ETerrain.Grass, false, true,false,false,false)
		);
		FullMap map = new FullMap(nodes);
		map.printMap();

		String output = outputStream.toString();

		assertTrue(output.contains("🌳"));
		assertTrue(output.contains("⛰️"));
		assertTrue(output.contains("💦"));
		assertTrue(output.contains("🦸"));
		assertTrue(output.contains("💰"));
		assertTrue(output.contains("🦹"));
	}

}
