package bboxx.domain.decibel.commandmodel;

import bboxx.domain.decibel.Decibel;

import java.util.Optional;

public interface DecibelRepository {
    Decibel save(Decibel decibel);
    Optional<Decibel> findById(Long id);
}
