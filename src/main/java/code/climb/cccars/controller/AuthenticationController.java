package code.climb.cccars.controller;

import code.climb.cccars.dto.AuthenticationDTO;
import code.climb.cccars.service.AuthenticationService;
import code.climb.cccars.service.JWTService;
import code.climb.cccars.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private final JWTService jwtService;
    private final AuthenticationService authenticationService;
    private final UserService userService;

    @Autowired
    public AuthenticationController(JWTService jwtService, AuthenticationService authenticationService, UserService userService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login(@RequestBody AuthenticationDTO requestBody) {
        Authentication authentication = authenticationService.authenticate(requestBody);
        if (authentication.isAuthenticated()) {
            userService.updateLastLoginTime(authentication.getName(), Instant.now());
            return jwtService.generateToken(authentication);
        } else {
            throw new BadCredentialsException("Bad credentials");
        }
    }
}
