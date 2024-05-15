package com.example.demo3.repo;

import com.example.demo3.model.Like;
import com.example.demo3.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface LikeRepo extends MongoRepository<Like, String> {

    @Query("{'user.$id': ?0, 'post.$id': ?1}")
    public Like isLikeExist(String userId, String postId);


    public List<Like> findByPost_Id(String postId);

    //TODO: find the like object using userID and postId
    public Like findLikeByUserAndPostId(User user, String postId);
}
