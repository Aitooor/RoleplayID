package online.starsmc.roleplayid.util;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class UniqueIDGenerator {
    private final Set<Integer> generatedIDs;
    private final Random random;

    public UniqueIDGenerator() {
        generatedIDs = new HashSet<>();
        random = new Random();
    }

    public int generatePositiveUniqueID() {
        int id;
        do {
            id = 10000 + random.nextInt(90000); // 5 digits
            //id = random.nextInt(Integer.MAX_VALUE); // Random digits size
            //id = 1000000000 + random.nextInt(900000000); // 10 digits
        } while (generatedIDs.contains(id));

        generatedIDs.add(id);
        return id;
    }

    public void clearGeneratedIds() {
        generatedIDs.clear();
    }
}

