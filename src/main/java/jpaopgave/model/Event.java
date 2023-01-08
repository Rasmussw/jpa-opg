package jpaopgave.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String venue;
    @ManyToOne //flere events kan have et band
    @JsonBackReference // pga. cirkulær reference i rescontroller

    @EqualsAndHashCode.Exclude //pga. hashcode() i @Data
    private Band band; //bliver mapped i Band

    private java.sql.Timestamp publishedOn;
    //private Timestamp timestamp; //format "2022-09-22T07:08:52.713+00:00" - år-måned-dag-Time-time-minut-sekund-tusinddelSekund+tidsperiode

    @OneToMany(mappedBy = "event") //mapped til event objectet inden i Review Klassen
    @JsonIgnore
    private Set<Review> reviews = new HashSet<>();


}