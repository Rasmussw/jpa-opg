package jpaopgave.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jpaopgave.model.User;
import jpaopgave.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.security.RolesAllowed;
import java.util.*;

@Component
public class TokenManager {
    public static final long TOKEN_VALIDITY = 10 * 60 * 60 * 1000; // 10 timer
    //det er den secret som står i application propaties
    @Value("${secret}") // aha: this is the server's private key. Which is used to generate new tokens.
    private String jwtSecret;
    private IUserService userService;

    public TokenManager(IUserService userService) {
        this.userService = userService;
    }

    public String generateJwtToken(UserDetails userDetails) {
        System.out.println("TokenManager generateJwtToken(UserDetails) call: 7");
        Map<String, Object> claims = new HashMap<>();

        System.out.println(userDetails.getUsername());

        String username = userDetails.getUsername();
        List<User> user = userService.findUsersByUsername(username);

        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())/* // her tilføjede jeg de roles som useren havde
        men det var den forkerte token, det behøver ikke at blive sendt videre
        tænker det er smart at gøre på denne måde, men kunne ikke finde ud af det*/
        .claim("roles", user.get(0).getRoles().toString())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TOKEN_VALIDITY ))
                .signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
    }


    public Boolean validateJwtToken(String token, UserDetails userDetails) {
        System.out.println("TokenManager validateJwtToken(String token, UserDetails) With token: Call: B");
        String username = getUsernameFromToken(token);
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        Boolean isTokenExpired = claims.getExpiration().before(new Date());
        return (username.equals(userDetails.getUsername()) && !isTokenExpired);
    }

    public String getUsernameFromToken(String token) {
        System.out.println("TokenManager getUsernameFromToken(String token) With token: Call: A");
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        try{
            claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        }catch (Exception e){
            System.out.println("could not parse JWT token for claims");
        }
        return claims.getSubject();
    }
}
