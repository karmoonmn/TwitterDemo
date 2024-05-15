package com.example.demo3.service;


import com.example.demo3.exception.PostException;
import com.example.demo3.exception.UserException;
import com.example.demo3.model.Like;
import com.example.demo3.model.Post;
import com.example.demo3.model.User;
import com.example.demo3.repo.LikeRepo;
import com.example.demo3.repo.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LikeServiceImp implements LikeService{

    @Autowired
    private LikeRepo likeRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private PostRepo postRepository;

    @Autowired
    private RecommendationEngine recommendationEngine;

    @Override
    public Like likePost(String postId, User user) throws UserException, PostException {
        Like isLikeExist = likeRepository.findLikeByUserAndPostId(user, postId);

        Post post = postService.findById(postId);

        if (isLikeExist != null) { //user liked the post

            //TODO: delete the likeId in the post
            post.getLikes().removeIf(like -> like.getId().equals(isLikeExist.getId()));

            //TODO: update user tag interest if the user unlike the post
            recommendationEngine.loseInterests(user, postId);

            likeRepository.deleteById(isLikeExist.getId()); //unlike the post

            postRepository.save(post);

            return isLikeExist;
        }

        Like like = new Like();
        like.setPost(post);
        like.setUser(user);
        Like savedLike = likeRepository.save(like);
        post.getLikes().add(savedLike);
        postRepository.save(post);

        //TODO : update user tag interest
        recommendationEngine.updateUserTagInterests(user, postId);

        return savedLike;
    }

    @Override
    public List<Like> getAllLikes(String postId) throws PostException {
        Optional<Post> post = postRepository.findById(postId);
        List<Like> likes = likeRepository.findByPost_Id(postId);


        return likes;
    }
}
