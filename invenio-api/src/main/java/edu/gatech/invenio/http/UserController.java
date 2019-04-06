package edu.gatech.invenio.http;

import edu.gatech.invenio.model.Friend;
import edu.gatech.invenio.model.Settings;
import edu.gatech.invenio.model.User;
import edu.gatech.invenio.repository.FriendRepository;
import edu.gatech.invenio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
public class UserController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FriendRepository friendRepository;

    @Autowired
    public UserController(UserRepository userRepository, PasswordEncoder passwordEncoder, FriendRepository friendRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.friendRepository = friendRepository;
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
    public User addFriend(@CookieValue("userId") String userId, @PathVariable("id") String friendId) {
        friendRepository.save(new Friend(userId, friendId));

        return userRepository.findById(friendId).orElse(null);
    }

    @DeleteMapping(value = "/user/friend/{id}")
    public void removeFriend(@CookieValue("userId") String userId, @PathVariable("id") String friendId) {
        Friend friend = friendRepository.findByUserAndFriend(userId, friendId);
        if (friend == null) {
            friend = friendRepository.findByUserAndFriend(friendId, userId);
        }
        friendRepository.delete(friend);
    }

    @GetMapping(value = "/user/friend")
    public Iterable<User> findFriends(@CookieValue("userId") String userId) {
        List<String> friendIds = friendRepository.findByUserOrFriend(userId, userId).stream().filter(Friend::isApproved).map(friend -> {
            if (friend.getUser().equals(userId)) {
                return friend.getFriend();
            } else {
                return friend.getUser();
            }
        }).collect(Collectors.toList());

        return userRepository.findAllById(friendIds);
    }

    @GetMapping(value = "/user/friend/not")
    public Iterable<User> findNotFriends(@CookieValue("userId") String userId) {
        List<String> friendIds = friendRepository.findByUserOrFriend(userId, userId).stream().map(friend -> {
            if (friend.getUser().equals(userId)) {
                return friend.getFriend();
            } else {
                return friend.getUser();
            }
        }).collect(Collectors.toList());

        friendIds.add(userId);

        return userRepository.findByIdNotIn(friendIds);
    }

    @PutMapping(value = "/user/friend/approve/{id}")
    public User approveFriend(@CookieValue("userId") String userId, @PathVariable("id") String friendId) {
        Friend friend = friendRepository.findByUserAndFriend(friendId, userId);
        friend.setApproved(true);

        friendRepository.save(friend);
        return userRepository.findById(friendId).orElse(null);
    }

    @DeleteMapping(value = "user/friend/request/{id}")
    public void denyRequest(@CookieValue("userId") String userId, @PathVariable("id") String friendId) {
        friendRepository.delete(friendRepository.findByUserAndFriend(friendId, userId));
    }

    @GetMapping(value = "user/friend/request")
    public Iterable<User> findFriendRequests(@CookieValue("userId") String userId) {
        List<String> friendIds = friendRepository.findByFriendAndApproved(userId, false).stream().map(Friend::getUser).collect(Collectors.toList());

        return userRepository.findAllById(friendIds);
    }

    @PutMapping("user/settings")
    public User updateSettings(@CookieValue("userId") String userId, @RequestBody Settings settings) {
        return userRepository.findById(userId).map(user -> {
            user.setSettings(settings);
            return userRepository.save(user);
        }).orElse(null);
    }
}
