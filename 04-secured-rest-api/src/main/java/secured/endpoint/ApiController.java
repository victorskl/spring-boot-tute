package secured.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ApiController {

  private final Environment env;

  private final ObjectMapper mapper;

  @Autowired
  public ApiController(Environment env, ObjectMapper mapper) {
    this.env = env;
    this.mapper = mapper;
  }

  @RequestMapping(value = "/api/version")
  public String version() {
    log.info("version");
    ObjectNode node = mapper.createObjectNode();
    node.put("version", env.getProperty("info.app.version"));
    return node.toString();
  }
}
