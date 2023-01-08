package jpaopgave.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ManyToAny;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.lang.reflect.Array;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User /*implements UserDetails*/ {

    public User(String password, String username) {
        this.username = username;
        this.password = password;
    }
//fetch = FetchType.EAGER gør at man kan finde user_roles tabellen når man kalder på user
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    @JsonBackReference
    private Set<Role> roles = new HashSet<>();

    private String password;
    private String username;
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

    public User(String password, String username, Set<Role> roles) {

    }

/*
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
       // authorities.add(new SimpleGrantedAuthority("hej"));
        //authorities.add(new SimpleGrantedAuthority("kat"));

 */
        /*
        for (Role role : roles) {
            System.out.println( "sjfjk" + role.getName());
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        *//*
        authorities.add(new SimpleGrantedAuthority(getRol()));
        return authorities;
    }
    */

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }




    /*
    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

     */

    @Override
    public String toString() {
        return "User{" +
                ", password='" + password + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}