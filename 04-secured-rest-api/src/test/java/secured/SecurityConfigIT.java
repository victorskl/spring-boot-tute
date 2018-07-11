package secured;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.net.MalformedURLException;
import java.net.URL;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class SecurityConfigIT {

  private TestRestTemplate restTemplate;
  private URL base;

  @LocalServerPort
  int port;

  @Before
  public void setUp() throws MalformedURLException {
    restTemplate = new TestRestTemplate("admin", "tommy");
    base = new URL("http://localhost:" + port + "/api/version");
  }

  @Test
  public void whenLoggedUserRequestsHomePage_ThenSuccess() throws IllegalStateException {

    ResponseEntity<String> response = restTemplate.getForEntity(base.toString(), String.class);

    assertEquals(HttpStatus.OK, response.getStatusCode());
    log.info(response.getBody());
    assertTrue(response.getBody().contains("version"));
  }

  @Test
  public void whenUserWithWrongCredentials_thenUnauthorizedPage() {

    restTemplate = new TestRestTemplate("admin", "wrongpassword");

    ResponseEntity<String> response = restTemplate.getForEntity(base.toString(), String.class);

    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    assertTrue(response
        .getBody()
        .contains("Unauthorized"));
  }
}
