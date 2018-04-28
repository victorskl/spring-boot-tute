package hello;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

// Unit Test endpoint

@RunWith(SpringRunner.class)
@SpringBootTest // Having used @SpringBootTest we are asking for the whole application context to be created
@AutoConfigureMockMvc
public class HelloControllerTest {

    @Autowired
    private MockMvc mvc; // mocking the HTTP request cycle unit test

    @Test
    public void getHello() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Greetings from Spring Boot!")));
    }
}

// An alternative would be to ask Spring Boot to create only the web layers of the context using the @WebMvcTest.
// Spring Boot automatically tries to locate the main application class of your application in either case,
// but you can override it, or narrow it down, if you want to build something different.