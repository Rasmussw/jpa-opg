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
public class Band {
    public Band(){

    }
    private String name;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "band") //mapped fra band objectet indeni Event klassen
    private Set<Event> events = new HashSet<>();


    @ManyToMany(mappedBy = "bandsLiked")
    @JsonBackReference
    private Set<User> userLikes = new HashSet<>();

}
