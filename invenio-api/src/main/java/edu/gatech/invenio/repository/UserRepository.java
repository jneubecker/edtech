package edu.gatech.invenio.repository;

import edu.gatech.invenio.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    public Optional<User> findByName(String name);
}
