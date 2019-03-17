package edu.gatech.invenio.repository;

import edu.gatech.invenio.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface GroupRepository extends MongoRepository<Group, String> {
    public List<Group> findByMembersContaining(String userId);
    public List<Group> findByMembersNotContaining(String userId);
}
