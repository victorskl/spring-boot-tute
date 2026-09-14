package hello;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Users", description = "User management APIs")
@RestController
public class UserController {

    @Operation(summary = "Get all users")
    @GetMapping("/users")
    public List<String> getUsers() {
        return List.of("User 1", "User 2", "User 3");
    }
}
