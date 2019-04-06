package edu.gatech.invenio.http;

import edu.gatech.invenio.model.Like;
import edu.gatech.invenio.model.Post;
import edu.gatech.invenio.repository.LikeRepository;
import edu.gatech.invenio.repository.PostRepository;
import edu.gatech.invenio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
public class PostController {
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostController(PostRepository postRepository, LikeRepository likeRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
    }

    @GetMapping(value = "/group/post/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Post> getGroupPosts(@PathVariable("groupId") String groupId) {
        return postRepository.findByGroupId(groupId);
    }

    @GetMapping(value = "/user/post/{userId}")
    public List<Post> getUserPosts(@CookieValue("userId") String requestUserId, @PathVariable("userId") String userId) {
        return userRepository.findById(userId)
                .filter(user -> "public".equals(user.getSettings().getFeedPrivacy()) || userId.equals(requestUserId))
                .map(user -> postRepository.findByUserId(userId)).orElse(Collections.emptyList());
    }

    @PostMapping(value = "/group/post/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Post savePost(@CookieValue("userId") String userId
            , @PathVariable("groupId") String groupId, @RequestBody Post post) {
        return postRepository.save(new Post(post.getContent(), groupId, userId));
    }

    @PutMapping(value = "/post")
    public Post updatePost(@CookieValue("userId") String userId, @RequestBody Post post) {
        return postRepository.findById(post.getId()).filter(foundPost -> foundPost.getUserId().equals(userId)).map(currentPost -> {
            currentPost.setContent(post.getContent());
            postRepository.save(currentPost);
            return currentPost;
        }).orElse(null);
    }

    // TODO only be able to comment on posts you can view (have to be in group post is in)
    @PutMapping(value = "/post/comment/{postId}")
    public Post commentOnPost(@CookieValue("userId") String userId, @PathVariable("postId") String postId, @RequestBody Post post) {
        return postRepository.findById(postId).map(currentPost -> {
            Post newPost = postRepository.save(new Post(post.getContent(), null, userId));
            currentPost.getSubPosts().add(newPost);
            postRepository.save(currentPost);
            return newPost;
        }).orElse(null);
    }

    @DeleteMapping(value = "/post/{id}")
    public Post deletePost(@CookieValue("userId") String userId, @PathVariable("id") String id) {
        return postRepository.findById(id).filter(post -> post.getUserId().equals(userId)).map(post -> {
            postRepository.delete(post);
            return post;
        }).orElse(null);
    }

    // TODO only be able to like posts you can view (have to be in group post is in)
    @PutMapping(value = "post/{id}/like")
    public void like(@CookieValue("userId") String userId, @PathVariable("id") String postId) {
        postRepository.findById(postId).ifPresent(post -> {
            List<Like> likes = likeRepository.findByPostIdAndUserId(postId, userId);

            if (!likes.isEmpty()) {
                post.getLikes().remove(likes.get(0));
                postRepository.save(post);
                likeRepository.delete(likes.get(0));
            } else {
                Like newLike = new Like(postId, userId);
                post.getLikes().add(newLike);
                likeRepository.save(newLike);
                postRepository.save(post);
            }
        });
    }
}
