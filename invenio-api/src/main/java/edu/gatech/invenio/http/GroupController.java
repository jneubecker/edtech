package edu.gatech.invenio.http;

import edu.gatech.invenio.model.Group;
import edu.gatech.invenio.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
public class GroupController {
    private final GroupRepository groupRepository;

    @Autowired
    public GroupController(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @GetMapping(value = "/group", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Group> findGroupById(@CookieValue("userId") String userId) {
        if (!userId.isEmpty()) {
            return groupRepository.findByMembersContaining(userId);
        } else {
            return Collections.emptyList();
        }
    }

    @GetMapping(value = "/group/all", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Group> findGroups() {
        return groupRepository.findAll();
    }

    @PutMapping(value = "/group/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Group saveGroup(@PathVariable(value = "name") String name) {
        return groupRepository.save(new Group(name));
    }

    @PutMapping(value = "/group/member/{id}")
    public void joinGroup(@CookieValue("userId") String userId, @PathVariable(value = "id") String groupId) {
        groupRepository.findById(groupId).map(group -> {
            group.getMembers().add(userId);
            return group;
        }).ifPresent(groupRepository::save);
    }

    @DeleteMapping(value = "/group/member/{id}")
    public void leaveGroup(@CookieValue("userId") String userId, @PathVariable(value = "id") String groupId) {
        groupRepository.findById(groupId).map(group -> {
           group.getMembers().remove(userId);
           return group;
        }).ifPresent(groupRepository::save);
    }

}
