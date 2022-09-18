package jpaopgave.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {

    public User(){
    }

    private String name;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;


    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private Set<Review> reviews = new HashSet<>();

}
