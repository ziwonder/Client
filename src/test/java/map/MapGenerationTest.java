package map;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MapGenerationTest {

	@Test
	void tesMapGeneration_returnValidMapDistribution() {
		double grassPercentage = 0.48;
        double mountainPercentage = 0.1;
        double waterPercentage = 0.14;
        double fortPercentage = 0.12;
        int sizeX = 10;
        int sizeY = 5;
        
		MapGeneration generation = new MapGeneration();
		
		ClientMap map = generation.generateMap(sizeX, sizeY, grassPercentage,
                mountainPercentage, waterPercentage, fortPercentage);
		long grass = map.getNodesList().stream()
                .filter(n -> n.getTerrain().equals(ETerrain.Grass))
                .count();
		long mountain = map.getNodesList().stream()
                .filter(n -> n.getTerrain().equals(ETerrain.Mountain))
                .count();
		long water = map.getNodesList().stream()
                .filter(n -> n.getTerrain().equals(ETerrain.Water))
                .count();
		assertTrue(sizeX*sizeY*grassPercentage <= grass);
		assertTrue(sizeX*sizeY*mountainPercentage <= mountain);
		assertTrue(sizeX*sizeY*waterPercentage <= water);
	}

}
