package edu.gatech.invenio.http;

import edu.gatech.invenio.model.Group;
import edu.gatech.invenio.model.GroupMember;
import edu.gatech.invenio.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class GroupController {
    private final GroupRepository groupRepository;

    @Autowired
    public GroupController(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }

    @GetMapping(value = "/group/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Group> findGroupById(@PathVariable("userId") String userId) {
        if (!userId.isEmpty()) {
            return groupRepository.findByMembersContaining(userId);
        } else {
            return groupRepository.findAll();
        }
    }

    @GetMapping(value = "/group", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Group> findGroups() {
        return groupRepository.findAll();
    }

    @PutMapping(value = "/group", produces = MediaType.APPLICATION_JSON_VALUE)
    public Group saveGroup(@RequestBody Group group) {
        groupRepository.save(group);
        return group;
    }

    // TODO change userId to a cookie (need to deal with CORS)
    @PutMapping(value = "/group/join")
    public void joinGroup(@RequestBody GroupMember groupMember) {
        groupRepository.findById(groupMember.getGroupId()).map(group -> {
            group.getMembers().add(groupMember.getUserId());
            return group;
        }).ifPresent(groupRepository::save);
    }

    @PostMapping(value = "/group/leave")
    public void leaveGroup(@RequestBody GroupMember groupMember) {
        groupRepository.findById(groupMember.getGroupId()).map(group -> {
           group.getMembers().remove(groupMember.getUserId());
           return group;
        }).ifPresent(groupRepository::save);
    }

}
