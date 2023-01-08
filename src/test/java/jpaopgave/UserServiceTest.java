package jpaopgave;

import jpaopgave.model.User;
import jpaopgave.repository.UserRepository;
import jpaopgave.service.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest //vi bruger H2 databasen, istedet for JPA databasen
public class UserServiceTest {

    private UserService userService;
    @Autowired
    UserRepository userRepository;

   @BeforeEach//@BeforeEach betyder at denne metode bliver kørt før hver @Test
    public void setUp(){
        userService = new UserService(userRepository);
    }

    @Test
    void save() {
        User user = new User("123","ole123");
        User savedUser = userService.save(user);
        Assertions.assertNotNull(savedUser); //tjekker den er gamt
        Assertions.assertEquals("ole123", savedUser.getUsername()); //tjekker om usernamet korrekt
    }
}