package jpaopgave.service;

import jpaopgave.model.Band;
import jpaopgave.repository.BandRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class BandService implements IBandService{

    private BandRepository bandRepository;

    public BandService(BandRepository bandRepository) {
        this.bandRepository = bandRepository;
    }

    @Override
    public Set<Band> findAll() {
        Set<Band> bands = new HashSet<>();
        bandRepository.findAll().forEach(bands::add);
        return bands;
    }

    @Override
    public Band save(Band object) {
        bandRepository.save(object);
        return object;
    }

    @Override
    public void delete(Band object) {
        bandRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        bandRepository.deleteById(aLong);
    }

    @Override
    public Optional<Band> findById(Long aLong) {
        return bandRepository.findById(aLong);
    }

    @Override
    public List<Band> findBandByNameOrderById(String name) {

        return bandRepository.findBandByNameOrderById(name);
    }
}
