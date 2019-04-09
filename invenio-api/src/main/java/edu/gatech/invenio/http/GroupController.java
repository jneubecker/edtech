package edu.gatech.invenio.http;

import edu.gatech.invenio.model.Group;
import edu.gatech.invenio.model.User;
import edu.gatech.invenio.repository.GroupRepository;
import edu.gatech.invenio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
public class GroupController {
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Autowired
    public GroupController(GroupRepository groupRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/group", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Group> findGroupById(@CookieValue("userId") String userId) {
        if (!userId.isEmpty()) {
            return groupRepository.findByMembersContaining(userId);
        } else {
            return Collections.emptyList();
        }
    }

    @GetMapping(value = "/group/not", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Group> findGroupsByUserNotIn(@CookieValue("userId") String userId) {
        if (!userId.isEmpty()) {
            return groupRepository.findByMembersNotContaining(userId);
        } else {
            return Collections.emptyList();
        }
    }

    @GetMapping(value = "/group/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Group> findGroups() {
        return groupRepository.findAll();
    }

    @PutMapping(value = "/group/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Group saveGroup(@PathVariable(value = "name") String name, @CookieValue("userId") String userId) {
        return userRepository.findById(userId).map(user ->
                groupRepository.save(new Group(name, user.getId()))
        ).orElse(null);
    }

    @PutMapping(value = "/group/member/{id}")
    public Group joinGroup(@CookieValue("userId") String userId, @PathVariable(value = "id") String groupId) {
        return groupRepository.findById(groupId).map(group -> {
            group.getMembers().add(userId);
            return groupRepository.save(group);
        }).orElse(null);
    }

    @DeleteMapping(value = "/group/member/{id}")
    public void leaveGroup(@CookieValue("userId") String userId, @PathVariable(value = "id") String groupId) {
        groupRepository.findById(groupId).map(group -> {
            group.getMembers().remove(userId);
            return group;
        }).ifPresent(groupRepository::save);
    }

    @PutMapping(value = "/group/{id}/admin/{userId}")
    public void addAdmin(@CookieValue("userId") String userId, @PathVariable(value = "id") String groupId, @PathVariable("userId") String adminUserId) {
        groupRepository.findOptionalByIdAndAdminsContaining(groupId, userId).ifPresent(group -> {
            userRepository.findById(adminUserId).ifPresent(user -> {
                group.getAdmins().add(user.getId());
                groupRepository.save(group);
            });
        });
    }

    @PutMapping(value = "/group/{id}/moderator/{userId}")
    public void addModerator(@CookieValue("userId") String userId, @PathVariable(value = "id") String groupId, @PathVariable("userId") String adminUserId) {
        groupRepository.findOptionalByIdAndAdminsContaining(groupId, userId).ifPresent(group -> {
            userRepository.findById(adminUserId).ifPresent(user -> {
                group.getModerators().add(user.getId());
                groupRepository.save(group);
            });
        });
    }

    @DeleteMapping(value = "/group/{id}/admin/{userId}")
    public void removeAdmin(@CookieValue("userId") String userId, @PathVariable(value = "id") String groupId, @PathVariable("userId") String adminUserId) {
        groupRepository.findOptionalByIdAndAdminsContaining(groupId, userId).ifPresent(group -> {
            userRepository.findById(adminUserId).ifPresent(user -> {
                group.getAdmins().remove(user.getId());
                groupRepository.save(group);
            });
        });
    }

    @DeleteMapping(value = "/group/{id}/moderator/{userId}")
    public void removeModerator(@CookieValue("userId") String userId, @PathVariable(value = "id") String groupId, @PathVariable("userId") String adminUserId) {
        groupRepository.findOptionalByIdAndAdminsContaining(groupId, userId).ifPresent(group -> {
            userRepository.findById(adminUserId).ifPresent(user -> {
                group.getModerators().remove(user.getId());
                groupRepository.save(group);
            });
        });
    }

    @GetMapping(value = "/group/{id}/members")
    public Iterable<User> getGroupMembers(@CookieValue("userId") String userId, @PathVariable(value = "id") String groupId) {
        return groupRepository.findOptionalByIdAndAdminsContaining(groupId, userId).map(group ->
            userRepository.findAllById(group.getMembers())
        ).orElse(Collections.emptyList());
    }

}
