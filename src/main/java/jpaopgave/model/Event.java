package jpaopgave.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Event {

    public Event(){}
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String venue;
    @ManyToOne //flere events kan have et band
    @JsonBackReference // pga. cirkulær reference i rescontroller
    @EqualsAndHashCode.Exclude //pga. hashcode() i @Data
    private Band band;


    @OneToMany(mappedBy = "event")
    @JsonIgnore
    private Set<Review> reviews = new HashSet<>();
}