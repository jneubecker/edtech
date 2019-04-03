package edu.gatech.invenio.http;

import edu.gatech.invenio.model.Post;
import edu.gatech.invenio.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
public class PostController {
    private final PostRepository postRepository;

    @Autowired
    public PostController(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @GetMapping(value = "/group/post/{groupId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Post> getGroupPosts(@PathVariable("groupId") String groupId) {
        return postRepository.findByGroupId(groupId);
    }

    @GetMapping(value = "/user/post/{userId}")
    public List<Post> getUserPosts(@PathVariable("userId") String userId) {
        return postRepository.findByUserId(userId);
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
}
