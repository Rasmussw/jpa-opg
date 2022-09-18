package jpaopgave.controller;

import jpaopgave.model.Band;
import jpaopgave.model.Event;
import jpaopgave.model.Review;
import jpaopgave.model.User;
import jpaopgave.service.IBandService;
import jpaopgave.service.IEventService;
import jpaopgave.service.IReviewService;
import jpaopgave.service.IUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Controller
public class ReviewController {
    private IReviewService reviewService;
    private IUserService userService;
    private IEventService eventService;

    public ReviewController(IReviewService reviewService, IUserService userService, IEventService eventService) {
        this.reviewService = reviewService;
        this.userService = userService;
        this.eventService = eventService;
    }

    @PostMapping("/createReview")
    public ResponseEntity<String> createReview(@RequestBody Review review, @RequestParam Long eventId, @RequestParam Long userId){
        Optional<Event> event = eventService.findById(eventId);
        Optional<User> user = userService.findById(userId);

        if (event.isPresent() && user.isPresent()){
            review.setEvent(event.get());
            review.setUser(user.get());
            reviewService.save(review);
            return new ResponseEntity<>("ok at oprette review", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("review ikke oprettet", HttpStatus.OK);
        }
    }
    @GetMapping("/getReview")
    public ResponseEntity<HashSet> getReview(@RequestBody Event event){

        Set<Review> reviews = new HashSet<>();

        int index = 0;

        for (Review review : reviewService.findAll()) {
            if (review.getEvent().getId() == event.getId()){
                reviews.add(review);
            }
            index ++;
        }

        return new ResponseEntity(reviews, HttpStatus.OK);
    }


    @GetMapping("/getAllReviews")
    public ResponseEntity<HashSet> getAllReviews(){
        return new ResponseEntity(reviewService.findAll(), HttpStatus.OK);
    }

}
