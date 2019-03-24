package edu.gatech.invenio.repository;

import edu.gatech.invenio.model.Friend;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FriendRepository extends MongoRepository<Friend, String> {
    public Friend findByUserAndFriend(String user, String friend);
    public List<Friend> findByUserOrFriend(String user, String friend);
    public List<Friend> findByFriendAndApproved(String friend, boolean approved);
}
