package edu.gatech.invenio.http;

import edu.gatech.invenio.model.Group;
import edu.gatech.invenio.repository.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class GroupController {
    private final GroupRepository repository;

    @Autowired
    public GroupController(GroupRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/group", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Group> findGroup() {
        return repository.findAll();
    }

    @PutMapping(value = "/group", produces = MediaType.APPLICATION_JSON_VALUE)
    public Group saveGroup(@RequestBody Group group) {
        repository.save(group);
        return group;
    }

}
