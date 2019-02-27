package edu.gatech.invenio.repository;

import edu.gatech.invenio.model.Group;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GroupRepository extends MongoRepository<Group, String> {

}
