package jpaopgave.controller;

import jpaopgave.model.Venue;
import jpaopgave.service.IVenueService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class VenueController {
    private IVenueService venueService;

    @PostMapping("/createVenue")
    public ResponseEntity<String> createVenue(@RequestBody Venue venue){
        String message = "";
        if (venueService.save(venue) != null){
            message = "oprettet venue " + venue.getName();
        } else {
            message = "venueikke oprettet " + venue.getName();
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}