package edu.gatech.invenio.repository;

import edu.gatech.invenio.model.Like;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface LikeRepository extends MongoRepository<Like, String> {
    public List<Like> findByPostIdAndUserId(String postId, String userId);
}
