package com.example.demo3.service;

import com.example.demo3.exception.PostException;
import com.example.demo3.exception.UserException;
import com.example.demo3.model.Post;
import com.example.demo3.model.User;
import com.example.demo3.repo.PostRepo;
import com.example.demo3.request.PostReplyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImp implements PostService{

    @Autowired
    private RecommendationEngine recommendationEngine;

    @Autowired
    private PostRepo postRepo;

    @Override
    public Post createPost(Post req, User user) throws UserException {
        Post post = new Post();
        post.setContent(req.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setImage(req.getImage());
        post.setUser(user);
        post.setReply(false);
        post.setNotAReply(true);
        post.setChat(false);
        post.setVideo(req.getVideo());
        post.setTags(req.getTags());

        return postRepo.save(post);
    }

    @Override
    public Post createChat(Post req, User user) throws UserException {
        Post post = new Post();
        post.setContent(req.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setImage(req.getImage());
        post.setUser(user);
        post.setReply(false);
        post.setNotAReply(false);
        post.setChat(true);
        post.setVideo(req.getVideo());
        post.setTags(req.getTags());

        return postRepo.save(post);
    }

    @Override
    public List<Post> findAllChat(User user) throws UserException {
        return postRepo.findPostByChatIsTrueOrderByCreatedAtDesc(user);
    }

    @Override
    public List<Post> findAllPost(User user) {
        return recommendationEngine.recommendPost(user);
    }

    @Override
    public Post repost(String postId, User user) throws UserException, PostException {
        Post post = findById(postId);
        if (post.getRepost().contains(user)) {
            post.getRepost().remove(user);
        } else {
            post.getRepost().add(user);
        }
        return postRepo.save(post);
    }

    @Override
    public Post findById(String postId) throws PostException {
        Post post = postRepo.findById(postId)
                .orElseThrow(() -> new PostException("Post not found with id : " + postId));
        return post;
    }

    @Override
    public void deletePostById(String postId, String userId) throws PostException, UserException {
        Post post = findById(postId);
        if (!userId.equals(post.getUser().getId())) {
            throw new UserException("You can't delete another user's post");
        }
        postRepo.deleteById(postId);
    }

    @Override
    public Post removeFromRepost(String postId, User user) throws PostException, UserException {
        return null;
    }

    @Override
    public Post createdReply(PostReplyRequest req, User user) throws PostException {

        Post replyFor = findById(req.getTweetId());

        Post post = new Post();
        post.setContent(req.getContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setImage(req.getImage());
        post.setUser(user);
        post.setReply(true);
        post.setNotAReply(false);
        post.setChat(false);
        post.setReplyFor(replyFor);

        Post saveReply = postRepo.save(post);
        replyFor.getReplies().add(saveReply);
        postRepo.save(saveReply);
        postRepo.save(replyFor);

        return replyFor;
    }

    @Override
    public List<Post> getUserPost(User user) {
        return postRepo.findByUser(user);
    }

    @Override
    public List<Post> findByLikesContainsUser(User user) {
        return postRepo.findAllByLikes(user.getId());
    }


    @Override
    public List<Post> findByTag(String tag) {
        return postRepo.findByTags(tag);
    }

    @Override
    public List<Post> findByText(String text) {
        return postRepo.findByContentContaining(text);
    }
}
