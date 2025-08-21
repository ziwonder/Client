package map;


import controller.MapValidationController;
import map.validation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MapGeneration {

    private static final Logger logger = LoggerFactory.getLogger(MapGeneration.class);

    public ClientMap generateMap(int sizeX, int sizeY, double grassPercentage,double mountainPercentage ,
                                 double waterPercentage, double fortPercentage) {
        MapValidationController controller = new MapValidationController();
        int generationRounds = 0;

        while (true) {
            ClientMap map = new ClientMap(generateNodes(sizeX, sizeY, fortPercentage), sizeX, sizeY);

            List<IRuleValidator> ruleValidators = new ArrayList<>();
            ruleValidators.add(new GrassDistributionValidator(grassPercentage));
            ruleValidators.add(new MountainDistributionValidator(mountainPercentage));
            ruleValidators.add(new WaterDistributionValidator(waterPercentage));
            ruleValidators.add(new FortDistributionValidator(fortPercentage));
            ruleValidators.add(new IslandValidator(map.getSizeY(), map.getSizeX()));
            ruleValidators.add(new EdgesValidator(map.getSizeY(), map.getSizeX()));

            MapValidation validation = new MapValidation(ruleValidators);

            if (validation.isMapValid(map)) {
                logger.info("The map is successfully generated with {} rounds", generationRounds);
                return map;
            }

            Notification note = validation.getNotification();
            controller.MapValidationErrorsOccurred(note);
            generationRounds++;
        }
    }

    private List<HalfMapNode> generateNodes(int sizeX, int sizeY, double fortPercentage){
        List<HalfMapNode> nodes = new ArrayList<>();
        Random random = new Random();
        int fortPlacedCount = 0;
        int fortRequiredCount =  (int)(sizeX*sizeY*fortPercentage);
        for (int y = 0; y < sizeY; y++) {
            for (int x = 0; x < sizeX; x++) {
                ETerrain terrain = ETerrain.values()[random.nextInt(ETerrain.values().length)];
                boolean hasFort = random.nextBoolean();
                if (hasFort) {
                    if (fortPlacedCount == fortRequiredCount
                            || terrain != ETerrain.Grass) {
                        hasFort = false;
                    } else {
                        fortPlacedCount++;
                    }
                }
                nodes.add(new HalfMapNode(x, y, terrain, hasFort));
            }
        }
        return nodes;
    }
}
