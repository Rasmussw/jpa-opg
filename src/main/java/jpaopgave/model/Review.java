package jpaopgave.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;
    private int rate;


    @ManyToOne
    //@JsonBackReference
    @EqualsAndHashCode.Exclude
    private Event event;//bliver mapped i Event klassen

    @ManyToOne
    //@JsonBackReference
    @EqualsAndHashCode.Exclude
    private User user;//bliver mapped i User klassen
}
