package jpaopgave.controller;

import jpaopgave.model.Band;
import jpaopgave.model.Event;
import jpaopgave.service.IBandService;
import jpaopgave.service.IEventService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Optional;

@RestController
@AllArgsConstructor
public class EventController {

    private IEventService ieventService;
    private IBandService bandService;

    @PostMapping("/createEvent")
    public ResponseEntity<String> createEvent(@RequestBody Event event, @RequestParam Long bandId){
        Optional<Band> band = bandService.findById(bandId);
        if (band.isPresent()){
            event.setBand(band.get());
            ieventService.save(event);
            return new ResponseEntity<>("ok at oprette event", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("event ikke oprettet", HttpStatus.OK);
        }
    }

    @GetMapping("/getAllEvents")
    public ResponseEntity<HashSet> getAllEvents(){
        return new ResponseEntity(ieventService.findAll(), HttpStatus.OK);
    }
}
