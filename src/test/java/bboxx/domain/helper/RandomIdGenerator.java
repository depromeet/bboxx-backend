package bboxx.domain.helper;

import java.util.List;
import java.util.Random;

public class RandomIdGenerator {

    public static long generate(List<Long> idList) {
        while (true) {
            Random random = new Random();
            long id = random.nextInt(100000000);
            if (idList.stream().noneMatch(existedId -> existedId == id)) {
                return id;
            }
        }
    }
}
