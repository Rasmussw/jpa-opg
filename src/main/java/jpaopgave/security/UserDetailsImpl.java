package jpaopgave.security;

import jpaopgave.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


//denne klasse gør at jeg kan bygge userdetails ud fra en user
//det er denne klasse der gør, at jeg senere kan sætte bl.a. rollerne i den token
//som kører i bagrunden i spring
public class UserDetailsImpl implements UserDetails {

    private Collection<? extends GrantedAuthority> authorities;
    private String password;
    private String username;
    private long id;

    public UserDetailsImpl(Collection<? extends GrantedAuthority> authorities, String password, String username, long id) {
        this.authorities = authorities;
        this.password = password;
        this.username = username;
        this.id = id;
    }

    public static UserDetailsImpl buildUserDetailsImpl(User user){
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (int i = 0; i < user.getRoles().size(); i++) {
            authorities.add(new SimpleGrantedAuthority(user.getRoles().stream().toList().get(i).toString()));
        }

        UserDetailsImpl userDetails = new UserDetailsImpl(authorities,
                user.getPassword(),
                user.getUsername(),
                user.getId());

        return userDetails;
    }

    public long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
