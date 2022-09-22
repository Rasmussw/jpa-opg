package jpaopgave.controller;

import jpaopgave.model.Band;
import jpaopgave.model.Event;
import jpaopgave.service.IBandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Controller
public class BandController {
    private IBandService bandService;

    public BandController(IBandService bandService) {
        this.bandService = bandService;
    }

    @PostMapping("/createBand")
        public ResponseEntity<String> createEvent(@RequestBody Band band){
        String message = "";
        if (bandService.save(band) != null){
            message = "oprettet band " + band.getName();
        } else {
            message = "band ikke oprettet " + band.getName();
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/getAllBands")
    public ResponseEntity<HashSet> getAllBands(){
        return new ResponseEntity(bandService.findAll(), HttpStatus.OK);
    }

    @PostMapping("/deleteBand")
    public ResponseEntity<String> deleteBand(@RequestParam Long along){
        bandService.deleteById(along);
        return new ResponseEntity("band slettet", HttpStatus.OK);
    }

    @GetMapping("getBandByName")
    public ResponseEntity<List<Band>> getBandByName(String name){
        return new ResponseEntity<>(bandService.findBandByNameOrderById(name), HttpStatus.OK);
    }

}
