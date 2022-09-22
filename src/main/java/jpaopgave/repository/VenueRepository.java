package jpaopgave.repository;

import jpaopgave.model.User;
import jpaopgave.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VenueRepository extends JpaRepository<Venue, Long> {

}
