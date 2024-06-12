package com.example.demo3.service;

import com.example.demo3.model.Post;
import com.example.demo3.model.User;
import com.example.demo3.repo.PostRepo;
import com.example.demo3.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RecommendationEngine {
    @Autowired
    private UserRepo userRepository;

    @Autowired
    private PostRepo postRepository;

    //update user's tag interests when they like or reply to a post
    public void updateUserTagInterests(User user, String postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        Map<String, Integer> interests =  user.getTagInterests();

        if (post.getTags().size() != 0) {
            post.getTags().forEach(tag -> {
                interests.merge(tag, 1, Integer::sum);
            });
        }

        userRepository.save(user);
    }

    //TODO: decrease the tag interest when user unlike post
    public void loseInterests(User user, String postId) {
        System.out.println("user lose interest");
        Post post = postRepository.findById(postId).orElseThrow();
        Map<String, Integer> interests =  user.getTagInterests();

        if (!post.getTags().isEmpty()) {
            post.getTags().forEach(tag -> {
                interests.merge(tag, -1, (oldValue, decrement) -> Math.max(0, oldValue + decrement));
            });
        }
        userRepository.save(user);
    }

    //generate a list of recommended posts for a user based on their own interests
    public List<Post> recommendPost(User user) {
        Map<String, Integer> tagInterest = user.getTagInterests();

        PriorityQueue<Post> postsPriority = new PriorityQueue<>((p1, p2) -> {
            int priority1 = calculatePostPriority(tagInterest, p1);
            int priority2 = calculatePostPriority(tagInterest, p2);

            return Integer.compare(priority2, priority1);
        });

        List<Post> allPosts = postRepository.findAll();
        allPosts.forEach(postsPriority::offer);

        List<Post> recommendedPost = new ArrayList<>();
        while (!postsPriority.isEmpty() && recommendedPost.size() < 50) {
            recommendedPost.add(postsPriority.poll());
        }
        return recommendedPost;
    }

    private int calculatePostPriority(Map<String, Integer> tagInterest, Post post) {
        int maxWeight = post.getTags().stream()
                .mapToInt(tag -> tagInterest.getOrDefault(tag, 0))
                .max()
                .orElse(0);
        int priority = maxWeight * 2 * post.getLikes().size();
        if (priority == 0) {
            return post.getLikes().size();
        } else {
            return priority;
        }
    }

}
