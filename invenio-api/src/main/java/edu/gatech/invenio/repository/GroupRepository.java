package edu.gatech.invenio.repository;

import edu.gatech.invenio.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends MongoRepository<Group, String> {
    List<Group> findByMembersContaining(String userId);
    List<Group> findByMembersNotContaining(String userId);
    Optional<Group> findOptionalByIdAndAdminsContaining(String id, String userId);
}
