package bboxx.domain.member;

import java.util.List;

public class RandomIdGenerator {

    public static long generate(List<Long> idList) {
        while (true) {
            long id = (long)(Math.random() * 1000 / 1000);
            if (idList.stream().noneMatch(existedId -> existedId == id)) {
                return id;
            }
        }
    }
}
