package code.climb.cccars.controller;

import code.climb.cccars.dto.SignupDTO;
import code.climb.cccars.dto.UserDTO;
import code.climb.cccars.model.*;
import code.climb.cccars.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public ResponseEntity<Page<User>> getUsersPage(@RequestParam(defaultValue = "0") int pageNo, @RequestParam(defaultValue = "10") int pageSize) {
        Page<User> products = userService.getUsers(pageNo, pageSize);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/add-admin")
    public ResponseEntity<User> addAdmin(@RequestBody SignupDTO requestBody) {
        User user = userService.create(requestBody, Role.ADMIN);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping("/me")
    public ResponseEntity<User> getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User userDetails = userService.loadUserByUsername(username);
        if (userDetails == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDetails);
    }

    @PutMapping("/me")
    public ResponseEntity<User> updateCurrentUser(@RequestBody UserDTO user) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User savedUser = userService.loadUserByUsername(username);
        if (savedUser == null) {
            return ResponseEntity.notFound().build();
        }

        savedUser.setUsername(user.getUsername());
        savedUser.setEmail(user.getEmail());
        savedUser.setFirstName(user.getFirstName());
        savedUser.setLastName(user.getLastName());
        savedUser.setPhoneNumber(user.getPhoneNumber());
        String password = user.getPassword();
        if (password != null) {
            savedUser.setPassword(passwordEncoder.encode(password));
        }
        userService.save(savedUser);

        return ResponseEntity.ok(savedUser);
    }

    @PutMapping("/{username}")
    public ResponseEntity<User> updateUser(@PathVariable("username") String username, @RequestBody UserDTO user) {
        User savedUser = userService.loadUserByUsername(username);
        if (savedUser == null) {
            return ResponseEntity.notFound().build();
        }

        savedUser.setUsername(user.getUsername());
        savedUser.setEmail(user.getEmail());
        savedUser.setFirstName(user.getFirstName());
        savedUser.setLastName(user.getLastName());
        savedUser.setPhoneNumber(user.getPhoneNumber());
        String password = user.getPassword();
        if (password != null) {
            savedUser.setPassword(passwordEncoder.encode(password));
        }
        savedUser.setIsEnabled(user.getIsEnabled());
        savedUser.setIsVerified(user.getIsVerified());
        savedUser.setProfilePictureUrl(user.getProfilePictureUrl());
        userService.save(savedUser);

        return ResponseEntity.ok(savedUser);
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username) {
        User userDetails = userService.loadUserByUsername(username);
        if (userDetails == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userDetails);
    }

    @DeleteMapping("/{username}")
    public void deleteUser(@PathVariable("username") String username) {
        User userDetails = userService.loadUserByUsername(username);
        userService.deleteById(userDetails.getId());
    }

    @GetMapping("/filter")
    public ResponseEntity<List<User>> filterUsers(@RequestParam(required = false) String username,
                                                @RequestParam(required = false) String email,
                                                @RequestParam(required = false) String firstName,
                                                @RequestParam(required = false) String lastName,
                                                @RequestParam(required = false) Role role,
                                                @RequestParam(required = false) Boolean enabled,
                                                @RequestParam(required = false) Boolean verified) {
        Specification<User> spec = Specification.where(null);

        if (username != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("username"), username));
        }

        if (email != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("email"), email));
        }

        if (firstName != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("firstName"), firstName));
        }

        if (lastName != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("lastName"), lastName));
        }

        if (role != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("role"), role));
        }

        if (enabled != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("isEnabled"), enabled));
        }

        if (verified != null) {
            spec = spec.and((root, query, cb) -> cb.equal(root.get("isVerified"), verified));
        }

        List<User> users = userService.findUsersByQueryParams(spec);

        return ResponseEntity.ok(users);

    }
}
