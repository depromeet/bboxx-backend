package bboxx.domain.decibel;

import bboxx.domain.decibel.commandmodel.DecibelRepository;
import bboxx.domain.helper.RandomIdGenerator;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class FakeDecibelRepository implements DecibelRepository {

    public List<Decibel> decibels = new ArrayList<>();

    @Override
    public Decibel save(Decibel decibel) {
        if (decibel.getId() == null) {
            try {
                Field idField = decibel.getClass().getDeclaredField("id");
                idField.setAccessible(true);
                idField.set(decibel, RandomIdGenerator.generate(decibels.stream().map(Decibel::getId).collect(Collectors.toList())));
            } catch (Exception e) {
                // do nothing ...
            }
            this.decibels.add(decibel);
        } else {
            int index = decibels.stream()
                    .map(Decibel::getId)
                    .collect(Collectors.toList())
                    .indexOf(decibel.getId());
            if (index == -1) {
                decibels.add(decibel);
            } else {
                decibels.set(index, decibel);
            }
        }
        return decibel;
    }

    @Override
    public Optional<Decibel> findById(Long id) {
        return decibels.stream()
                .filter(decibel -> id.equals(decibel.getId()))
                .findFirst();
    }
}
