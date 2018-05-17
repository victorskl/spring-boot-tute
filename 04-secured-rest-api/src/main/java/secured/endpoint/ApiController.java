package secured.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import secured.service.SessionService;

@Slf4j
@RestController
public class ApiController {

  private final Environment env;

  private final ObjectMapper mapper;

  private SessionService sessionService;

  @Autowired
  public ApiController(Environment env, ObjectMapper mapper, SessionService sessionService) {
    this.env = env;
    this.mapper = mapper;
    this.sessionService = sessionService;
  }

  @RequestMapping(value = "/api/version", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public String getVersion() {
    log.info("getVersion");
    ObjectNode node = mapper.createObjectNode();
    node.put("version", env.getProperty("info.app.version"));
    return node.toString();
  }

  @RequestMapping(value = "/api/session/users", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public List<String> getAllLoggedInUsers() {
    log.info("getAllLoggedInUsers");
    return sessionService.getAllLoggedInUsers();
  }

  @RequestMapping(value = "/api/session/users/{username}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void expireUserSessions(@PathVariable String username) {
    log.info("expireUserSessions {}", username);
    sessionService.expireUserSessions(username);
  }
}
