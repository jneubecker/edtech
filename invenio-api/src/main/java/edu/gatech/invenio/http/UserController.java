package edu.gatech.invenio.http;

import edu.gatech.invenio.model.User;
import edu.gatech.invenio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @PostMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return userRepository.findByName(user.getName()).map(foundUser -> new ResponseEntity<User>(HttpStatus.BAD_REQUEST))
                .orElseGet(() -> new ResponseEntity<>(userRepository.save(new User(user.getName(), passwordEncoder.encode(user.getPassword()), user.getDisplayName())), HttpStatus.OK));
    }

    @GetMapping(value = "/user/login/{name}/{password}")
    public ResponseEntity<User> login(@PathVariable("name") String name, @PathVariable("password") String password) {
        return userRepository.findByName(name).filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping(value = "/user")
    public void deleteUsers() {
        userRepository.deleteAll();
    }

    @PutMapping(value = "/user/friend/{id}")
    public ResponseEntity<User> addFriend(@CookieValue("userId") String userId, @PathVariable("id") String friendId) {
        return userRepository.findById(friendId).map(friend -> {
           userRepository.findById(userId).ifPresent(user -> {
               user.getFriends().add(friend.getId());
               userRepository.save(user);
           });

           return new ResponseEntity<>(friend, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @GetMapping(value = "/user/friend")
    public Iterable<User> findFriends(@CookieValue("userId") String userId) {
        return userRepository.findById(userId).map(user -> userRepository.findAllById(user.getFriends())).orElse(Collections.emptyList());
    }
}
