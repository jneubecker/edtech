package edu.gatech.invenio.http;

import edu.gatech.invenio.model.Group;
import edu.gatech.invenio.model.GroupSettings;
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

    @GetMapping(value = "/group/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Group findGroup(@CookieValue("userId") String userId, @PathVariable("id") String groupId) {
        return groupRepository.findOptionalByIdAndMembersContaining(groupId, userId).orElse(null);
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

    @DeleteMapping(value = "/group/{id}/member")
    public void leaveGroup(@CookieValue("userId") String userId, @PathVariable(value = "id") String groupId) {
        groupRepository.findById(groupId).map(group -> {
            group.getMembers().remove(userId);
            group.getAdmins().remove(userId);
            group.getModerators().remove(userId);
            return group;
        }).ifPresent(groupRepository::save);
    }

    @DeleteMapping(value = "/group/{groupId}/member/{userId}")
    public Group removeUser(@CookieValue("userId") String userId, @PathVariable(value = "groupId") String groupId, @PathVariable(value = "userId") String userToRemove) {
        return groupRepository.findOptionalByIdAndAdminsContaining(groupId, userId).map(group -> {
            group.getMembers().remove(userToRemove);
            group.getAdmins().remove(userToRemove);
            group.getModerators().remove(userToRemove);
            return groupRepository.save(group);
        }).orElse(null);
    }

    @PutMapping(value = "/group/{id}/admin/{userId}")
    public Group addAdmin(@CookieValue("userId") String userId, @PathVariable(value = "id") String groupId, @PathVariable("userId") String adminUserId) {
        return groupRepository.findOptionalByIdAndAdminsContaining(groupId, userId).map(group -> userRepository.findById(adminUserId).map(user -> {
            group.getAdmins().add(user.getId());
            return groupRepository.save(group);
        }).orElse(null)).orElse(null);
    }

    @PutMapping(value = "/group/{id}/moderator/{userId}")
    public Group addModerator(@CookieValue("userId") String userId, @PathVariable(value = "id") String groupId, @PathVariable("userId") String adminUserId) {
        return groupRepository.findOptionalByIdAndAdminsContaining(groupId, userId).map(group -> userRepository.findById(adminUserId).map(user -> {
            group.getModerators().add(user.getId());
            return groupRepository.save(group);
        }).orElse(null)).orElse(null);
    }

    @DeleteMapping(value = "/group/{id}/admin/{userId}")
    public Group removeAdmin(@CookieValue("userId") String userId, @PathVariable(value = "id") String groupId, @PathVariable("userId") String adminUserId) {
        return groupRepository.findOptionalByIdAndAdminsContaining(groupId, userId).map(group -> userRepository.findById(adminUserId).map(user -> {
            group.getAdmins().remove(user.getId());
            return groupRepository.save(group);
        }).orElse(null)).orElse(null);
    }

    @DeleteMapping(value = "/group/{id}/moderator/{userId}")
    public Group removeModerator(@CookieValue("userId") String userId, @PathVariable(value = "id") String groupId, @PathVariable("userId") String adminUserId) {
        return groupRepository.findOptionalByIdAndAdminsContaining(groupId, userId).map(group -> userRepository.findById(adminUserId).map(user -> {
            group.getModerators().remove(user.getId());
            return groupRepository.save(group);
        }).orElse(null)).orElse(null);
    }

    @GetMapping(value = "/group/{id}/members")
    public Iterable<User> getGroupMembers(@CookieValue("userId") String userId, @PathVariable(value = "id") String groupId) {
        return groupRepository.findOptionalByIdAndAdminsContaining(groupId, userId).map(group ->
                userRepository.findAllById(group.getMembers())
        ).orElse(Collections.emptyList());
    }

    @PutMapping("group/{id}/settings")
    public Group updateSettings(@CookieValue("userId") String userId, @PathVariable("id") String groupId, @RequestBody GroupSettings settings) {
        return groupRepository.findOptionalByIdAndAdminsContaining(groupId, userId).map(group -> userRepository.findById(userId).map(user -> {
            group.setGroupSettings(settings);
            return groupRepository.save(group);
        }).orElse(null)).orElse(null);
    }

    @PutMapping("group/{id}/requestaccess")
    public Group requestAccess(@CookieValue("userId") String userId, @PathVariable("id") String groupId) {
        return groupRepository.findById(groupId).map(group -> userRepository.findById(userId).map(user -> {
            group.getPendingRequests().add(user);
            return groupRepository.save(group);
        }).orElse(null)).orElse(null);
    }

    @PutMapping("group/{id}/memberapproval/{memberId}")
    public Group approveRequest(@CookieValue("userId") String userId, @PathVariable("id") String groupId, @PathVariable("memberId") String memberId) {
        return groupRepository.findOptionalByIdAndMembersContaining(groupId, userId).map(group -> userRepository.findById(memberId).map(user -> {
            if (group.getGroupSettings().getMemberApprovalPolicy().equals("anyone") || group.getAdmins().contains(userId) || (group.getGroupSettings().getMemberApprovalPolicy().equals("moderator") && group.getModerators().contains(userId))) {
                group.getPendingRequests().remove(user);
                group.getMembers().add(user.getId());
                return groupRepository.save(group);
            } else {
                return null;
            }
        }).orElse(null)).orElse(null);
    }

    @DeleteMapping("group/{id}/memberapproval/{memberId}")
    public Group denyRequest(@CookieValue("userId") String userId, @PathVariable("id") String groupId, @PathVariable("memberId") String memberId) {
        return groupRepository.findOptionalByIdAndMembersContaining(groupId, userId).map(group -> userRepository.findById(memberId).map(user -> {
            if (group.getGroupSettings().getMemberApprovalPolicy().equals("anyone") || group.getAdmins().contains(userId) || (group.getGroupSettings().getMemberApprovalPolicy().equals("moderator") && group.getModerators().contains(userId))) {
                group.getPendingRequests().remove(user);
                return groupRepository.save(group);
            } else {
                return null;
            }
        }).orElse(null)).orElse(null);
    }

}
