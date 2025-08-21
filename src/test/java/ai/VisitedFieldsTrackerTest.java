package ai;

import static org.junit.jupiter.api.Assertions.*;

import map.ETerrain;
import map.FullMapNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VisitedFieldsTrackerTest {
	
	private VisitedFieldsTracker tracker;
	
	@BeforeEach
	void setUp() throws Exception {
		tracker = new VisitedFieldsTracker();
	}

	@Test
	void emptyVisitedList_addedMapNode_VisitedListContainsAddedNode() {
		FullMapNode node = new FullMapNode(0,0, ETerrain.Grass, false, false,false,false,false);

		tracker.noteVisitedField(node, 5,5);

		assertTrue(tracker.getVisitedNodes().contains(new Position(node.getX(), node.getY())));
	}

	@Test
	void notesVisitedMountain_addsAllNeighborsToList() {
		FullMapNode node = new FullMapNode(1,1, ETerrain.Mountain, false, false,false,false,false);

		tracker.noteVisitedField(node, 3,3);
        assertEquals(9, tracker.getVisitedNodes().size());
	}

}
