package edu.gatech.invenio.repository;

import edu.gatech.invenio.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PostRepository extends MongoRepository<Post, String> {
    public List<Post> findByGroupId(String groupId);

}
