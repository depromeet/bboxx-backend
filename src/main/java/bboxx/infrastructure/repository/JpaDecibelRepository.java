package bboxx.infrastructure.repository;

import bboxx.domain.decibel.Decibel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaDecibelRepository extends JpaRepository<Decibel, Long> {

}
