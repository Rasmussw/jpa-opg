package jpaopgave.controller;

import jpaopgave.model.Band;
import jpaopgave.model.User;
import jpaopgave.service.IBandService;
import jpaopgave.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;

@Controller
public class UserController {
    private IUserService iUserService;

    public UserController(IUserService iUserService) {
        this.iUserService = iUserService;
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
}