package jpaopgave.security.models;

import jpaopgave.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class JwtRequestModel {
    private String username;
    private String password;
    //private List<String> roles = new ArrayList<>();
}
