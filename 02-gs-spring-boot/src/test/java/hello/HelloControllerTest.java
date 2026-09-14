package hello;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class HelloControllerTest {

    private final HelloController controller = new HelloController();

    @Test
    void getHello() {
        String response = controller.index();

        assertEquals("Greetings from Spring Boot!", response);
    }
}
