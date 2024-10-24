package code.climb.cccars.controller;

import code.climb.cccars.dto.SignupDTO;
import code.climb.cccars.model.Role;
import code.climb.cccars.model.User;
import code.climb.cccars.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
public class SignupController {
    private final UserService userService;

    public SignupController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<User> signup(@RequestBody SignupDTO signupInformation) {
        User user = userService.create(signupInformation, Role.CUSTOMER);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
