package jpaopgave.service;

import jpaopgave.model.User;
import jpaopgave.model.Venue;
import jpaopgave.repository.VenueRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class VenueService implements IVenueService{
    private VenueRepository venueRepository;

    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    @Override
    public Set<Venue> findAll() {
        Set<Venue> venues = new HashSet<>();
        venueRepository.findAll().forEach(venues :: add);
        return venues;
    }

    @Override
    public Venue save(Venue object) {
        venueRepository.save(object);
        return object;
    }

    @Override
    public void delete(Venue object) {
        venueRepository.delete(object);
    }

    @Override
    public void deleteById(Long aLong) {
        venueRepository.deleteById(aLong);
    }

    @Override
    public Optional<Venue> findById(Long aLong) {
        return venueRepository.findById(aLong);
    }
}
