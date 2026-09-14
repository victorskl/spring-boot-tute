package hello;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

// MVC integration test

@SpringBootTest // Having used @SpringBootTest we are asking for the whole application context to be created
@AutoConfigureMockMvc
public class HelloControllerMvcTest {

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
