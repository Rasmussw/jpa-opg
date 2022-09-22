package jpaopgave.service;

import jpaopgave.model.Band;

import java.util.List;

public interface IBandService extends ICrudService<Band, Long>{
    List<Band> findBandByNameOrderById(String name);
}
