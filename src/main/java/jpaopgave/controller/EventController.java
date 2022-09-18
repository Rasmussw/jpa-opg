package jpaopgave.controller;

import jpaopgave.model.Band;
import jpaopgave.model.Event;
import jpaopgave.service.EventService;
import jpaopgave.service.IBandService;
import jpaopgave.service.IEventService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.Optional;

@Controller
public class EventController {

    private IEventService ieventService;
    private IBandService bandService;

    public EventController(IEventService ieventService, IBandService bandService) {
        this.ieventService = ieventService;
        this.bandService = bandService;
    }

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
