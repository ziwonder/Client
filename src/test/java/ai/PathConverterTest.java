package ai;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import map.ETerrain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import map.FullMapNode;

class PathConverterTest {
	
	private List<FullMapNode> nodesList;
	private PathConverter converter = new PathConverter();

	@BeforeEach
	void setUp() throws Exception {
		nodesList = new ArrayList<>();
	}

	@Test
	void emptyNodesList_convertToEMove_emptyEMoveList() {
		converter.convertToMoves(nodesList);
		assertTrue(nodesList.isEmpty());
	}

	@Test
	void duplicatesInNodeList_convertToEMove_throwsIllegalStateException() {
		nodesList.add(new FullMapNode(1,1, ETerrain.Grass, false, false,false,false,false));
		nodesList.add(new FullMapNode(1,1, ETerrain.Grass, false, false,false,false,false));
		IllegalStateException exception = assertThrows(IllegalStateException.class,
				() -> converter.convertToMoves(nodesList));
		assertEquals("No valid directions", exception.getMessage());
	}
	
	@ParameterizedTest
	@MethodSource("argumentsProvider")
	void MapNodesToEMove_convertToEMove_returnsValidEMoveList(
			List<FullMapNode> input, List<EMove> expected) {
		List<EMove> result = converter.convertToMoves(input);
		assertEquals(expected, result);
	}
	
	private static Stream<Arguments> argumentsProvider() {
		return Stream.of(
			Arguments.of(List.of(
					new FullMapNode(0,0, ETerrain.Grass, false, false,false,false,false),
					new FullMapNode(1,0, ETerrain.Grass, false, false,false,false,false),
					new FullMapNode(1,1, ETerrain.Mountain, false, false,false,false,false)),
					List.of(EMove.Right, EMove.Right, EMove.Down, EMove.Down, EMove.Down)),
			Arguments.of(List.of(
					new FullMapNode(1,1, ETerrain.Mountain, false, false,false,false,false),
					new FullMapNode(1,0, ETerrain.Grass, false, false,false,false,false),
					new FullMapNode(0,0, ETerrain.Grass, false, false,false,false,false)),
					List.of(EMove.Up, EMove.Up, EMove.Up, EMove.Left, EMove.Left)),
			Arguments.of(List.of(
					new FullMapNode(0,0, ETerrain.Mountain, false, false,false,false,false),
					new FullMapNode(1,0, ETerrain.Mountain, false, false,false,false,false),
					new FullMapNode(1,1, ETerrain.Grass, false, false,false,false,false)),
					List.of(EMove.Right, EMove.Right,EMove.Right, EMove.Right, EMove.Down, EMove.Down, EMove.Down))
		);
	}
	
}

