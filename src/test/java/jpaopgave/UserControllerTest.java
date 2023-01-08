package jpaopgave;

import jpaopgave.controller.UserController;
import jpaopgave.model.User;
import jpaopgave.repository.UserRepository;
import jpaopgave.security.models.JwtResponseModel;
import jpaopgave.service.BandService;
import jpaopgave.service.ReviewService;
import jpaopgave.service.UserService;
import jpaopgave.service.VenueService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest()
public class UserControllerTest {
    private MockMvc mockMvc; //bruger både service og controller layer
    private UserService userService;
    private BandService bandService;
    private VenueService venueService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    public void setUp(){
        userService = new UserService(userRepository);
        mockMvc = MockMvcBuilders.standaloneSetup(new UserController(venueService,userService,bandService)).build();
    }


    @Test
    public void createUser() throws Exception {

        mockMvc.perform(post("/createUser")
                        .contentType(MediaType.APPLICATION_JSON)//vi sender JSON fromat (til controlleren)
                        .content("{\"username\":\"anna\",\"password\":\"444\"}")//Bygger Request boddyen op
                        .accept(MediaType.APPLICATION_JSON))//Mediatypen den(controlleren) modtager skal være JSON
                .andExpect(status().isOk())//vi regner med at statusen skal være OK
                .andDo(print());
    }

    @Test
    public void getAllUsers() throws Exception{
        //laver to users
        User ras = new User("123", "rasmus");
        User anders = (new User("123", "anders"));
        //adder to users
        userService.save(ras);
        userService.save(anders);
        //fordi der er spring security - bliver passswordet encrypted - derfor skal vi gette den så vi får den formateret form
        String rasmusParsword = ras.getPassword();
        String andersParswork = anders.getPassword();

        mockMvc.perform(get("/getAllUsers"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"username\":\"rasmus\"" +
                        ",\"password\":" +"\""+rasmusParsword +"\"}," +
                        "{\"username\":\"anders\",\"password\":"+ "\"" + andersParswork + "\"}]")); // tjekker at det er de to,
        //der bliver addet, som også kommer frem og at statussen er er OK
    }

    @Test
    public void getUserByName() throws Exception{
    }
}