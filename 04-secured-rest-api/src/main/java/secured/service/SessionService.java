package secured.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class SessionService {

  private SessionRegistry sessionRegistry;

  @Autowired
  public SessionService(SessionRegistry sessionRegistry) {
    this.sessionRegistry = sessionRegistry;
  }

  // https://mtyurt.net/post/spring-expiring-all-sessions-of-a-user.html
  public void expireUserSessions(String username) {
    for (Object principal : sessionRegistry.getAllPrincipals()) {
      if (principal instanceof User) {
        UserDetails userDetails = (UserDetails) principal;
        if (userDetails.getUsername().equals(username)) {
          for (SessionInformation information : sessionRegistry.getAllSessions(userDetails, true)) {
            information.expireNow();
          }
        }
      }
    }
  }

  // http://www.baeldung.com/spring-security-track-logged-in-users
  public List<String> getAllLoggedInUsers() {
    return sessionRegistry.getAllPrincipals().stream()
        .filter(u -> !sessionRegistry.getAllSessions(u, false).isEmpty())
        .map(Object::toString)
        .collect(Collectors.toList());
  }
}
