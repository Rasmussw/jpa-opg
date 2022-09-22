package jpaopgave.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

    public User(){
    }

    private String name;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    //laver en join table med user og venue
    @ManyToMany
    @JoinTable(
            name = "venue_like",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "venue_id")
    )
    @JsonBackReference
    private Set<Venue> venuesLiked = new HashSet<>();

    //laver en join table med user og band
    @ManyToMany
    @JoinTable(
            name = "band_like",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "band_id")
    )
    @JsonBackReference
    private Set<Band> bandsLiked = new HashSet<>();

    @OneToMany(mappedBy = "user") //mapped til user objectet inden i Review Klassen
    @JsonIgnore
    private Set<Review> reviews = new HashSet<>();
}