package jpaopgave.security;

import io.jsonwebtoken.ExpiredJwtException;
import jpaopgave.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private JwtUserDetailsService userDetailsService;
    private TokenManager tokenManager;
    private IUserService userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String tokenHeader = request.getHeader("Authorization");
        System.out.println("JwtFilter doFilterInternal call 3 request header" + tokenHeader ); // + JwtController.printHeader(request)
        String username = null;
        String token = null;
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            token = tokenHeader.substring(7);
            try {
                username = tokenManager.getUsernameFromToken(token);
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");
            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
        } else {
            System.out.println("String does not start with Bearer or tokenheader == NULL");
        }
        validateToken(request, username, token);
        filterChain.doFilter(request, response); //possible: response.setHeader( "key",value); its up to you.
    }

    private void validateToken(HttpServletRequest request, String username, String token) {
        if (null != username && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            //gamle måde at få userens roller ind i den token som spring tjekker
            /*
            List<User> user = userService.findUsersByUsername(username);
            List<SimpleGrantedAuthority> authorities = new ArrayList<>();

            for (int i = 0; i < user.get(0).getRoles().size(); i++) {
                authorities.add(new SimpleGrantedAuthority(user.get(0).getRoles().stream().toList().get(i).toString()));
            }

            //den crasher når jeg prøver at køre det her- virker nu skulle stå fetch over useroles i user:
            //System.out.println("user roles to string in filter: " + user.get(0).getRoles());
*/
            if (tokenManager.validateJwtToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken

                        authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null,
                        //her sættes den ind så spring kan tjekke om den pågældene role
                        //har retigheder til den pågældene endpoint
                        //gammel måde
                        //authorities
                        //ny måde, har overrited getAuthorities i UserdetailsImpl -
                        //som implementere UserDetails. - super smart!!!
                        userDetails.getAuthorities());

                System.out.println("new token: " + authenticationToken);
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
    }
}