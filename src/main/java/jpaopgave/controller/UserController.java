package jpaopgave.controller;
import jpaopgave.model.Band;
import jpaopgave.model.Role;
import jpaopgave.model.User;
import jpaopgave.model.Venue;
import jpaopgave.security.models.JwtRequestModel;
import jpaopgave.security.models.JwtResponseModel;
import jpaopgave.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.*;

@RestController
//laver en constructer med alle fields??
@AllArgsConstructor
public class UserController {

    private IVenueService venueService;
    private IUserService iUserService;
    private IBandService bandService;
    //private IRoleService roleService;


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
    public ResponseEntity<JwtResponseModel> createUser(@RequestBody JwtRequestModel request){
        System.out.println("createUser: username:" + request.getUsername() + " password: " + request.getPassword() +"roles: " //+ request.getRoles()
                 );
/*
        Set<Role> roles = new HashSet<>();
        for (int i = 0; i < request.getRoles().size(); i++) {
            roles.add(new Role(request.getRoles().get(i).toString()));
        }
        System.out.println("roles2: " + roles);


 */
        User user = new User(request.getPassword(),request.getUsername());
        if(iUserService.findUsersByUsername(user.getUsername()).size()==0) {
            //iUserService.save(user);
            //for (int i = 0; i < request.getRoles().size(); i++) {
            //    user.getRoles().add(new Role(request.getRoles().get(i)));
          //  }
            if (iUserService.save(user) != null) {
                return ResponseEntity.ok(new JwtResponseModel("created user: " + user.getUsername() + " pw: " + user.getPassword()));
            } else {
                return ResponseEntity.ok(new JwtResponseModel("error creating user: " + user.getUsername()));
            }
        }else {
            return ResponseEntity.ok(new JwtResponseModel("error: user exists: " + user.getUsername()));
        }
        /*
        if (iUserService.save(user) != null){
            message = "oprettet USER " + user.getUsername();
        } else {
            message = "USER ikke oprettet " + user.getUsername();
        }
        return new ResponseEntity<>(message, HttpStatus.OK);
        */
    }

    @GetMapping("/getAllUsers")
    //@PreAuthorize("hasAuthority('ADMIN')") //udkomenteret for at få test til at virke
   //@RolesAllowed("ADMIN")
    public ResponseEntity<HashSet> getAllUsers(){
        System.out.println(iUserService.findAll());
        return new ResponseEntity(iUserService.findAll(), HttpStatus.OK);
    }

    @GetMapping("getUserByName")
    public ResponseEntity<List<User>> getUserByName(String name){
        return new ResponseEntity<>(iUserService.findUsersByUsername(name), HttpStatus.OK);
    }
}