package jpaopgave.controller;

import jpaopgave.model.Band;
import jpaopgave.model.User;
import jpaopgave.model.Venue;
import jpaopgave.service.IBandService;
import jpaopgave.service.IUserService;
import jpaopgave.service.IVenueService;
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
public class UserController {
    private IVenueService venueService;
    private IUserService iUserService;
    private IBandService bandService;

    public UserController(IVenueService venueService, IUserService iUserService, IBandService iBandService) {
        this.venueService = venueService;
        this.iUserService = iUserService;
        this.bandService = iBandService;
    }

    @PostMapping("/createBandLike")
    public ResponseEntity<String> createBandLike(@RequestParam Long userID,
                                             @RequestParam Long bandID){
        Optional<User> user_ = iUserService.findById(userID);
        Optional<Band> band_ = bandService.findById(bandID);
        if (user_.isPresent() && band_.isPresent()){
            user_.get().getBandsLiked().add(band_.get());
            iUserService.save(user_.get());
            return new ResponseEntity<>("ok at gemme user: " + userID + " band: " + bandID, HttpStatus.OK);
        }
        return new ResponseEntity<>("fejl", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/createVenueLike")
    public ResponseEntity<String> createVenueLike(@RequestParam Long userID,
                                                  @RequestParam Long venueID){
        Optional<User> user_ = iUserService.findById(userID);
        Optional<Venue> venue_ = venueService.findById(venueID);
        if (user_.isPresent() && venue_.isPresent()){
            user_.get().getVenuesLiked().add(venue_.get());
            iUserService.save(user_.get());
            return new ResponseEntity<>("ok at gemme user: " + userID + " venue: " + venueID, HttpStatus.OK);
        }
        return new ResponseEntity<>("fejl", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/createUser")
    public ResponseEntity<String> createEvent(@RequestBody User user){
        String message = "";
        if (iUserService.save(user) != null){
            message = "oprettet USER " + user.getName();
        } else {
            message = "USER ikke oprettet " + user.getName();
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<HashSet> getAllUsers(){
        return new ResponseEntity(iUserService.findAll(), HttpStatus.OK);
    }

    @GetMapping("getUserByName")
    public ResponseEntity<List<User>> getUserByName(String name){
        return new ResponseEntity<>(iUserService.findUsersByName(name), HttpStatus.OK);
    }
}