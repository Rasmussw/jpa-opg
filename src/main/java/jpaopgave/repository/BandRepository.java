package jpaopgave.repository;

import jpaopgave.model.Band;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BandRepository extends JpaRepository<Band, Long> {

    //JPA hibernate kan lave sin genere sine egne metoder - findBy - og så hvilken kolonne den skal findes by
    List<Band> findBandByNameOrderById(String name); //orderBy - man sortere her
}
