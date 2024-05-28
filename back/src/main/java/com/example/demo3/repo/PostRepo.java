package com.example.demo3.repo;

import com.example.demo3.model.Comment;
import com.example.demo3.model.Post;
import com.example.demo3.model.User;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Set;

public interface PostRepo extends MongoRepository<Post, String> {

    List<Post> findAllByPostIsTrueOrderByCreatedAtDesc();

    List<Post> findByRepostContainsOrUserIdAndPostIsTrueOrderByCreatedAtDesc(User user, String userId);

    List<Post> findByLikesContainingOrderByCreatedAtDesc(User user);

    List<Post> findAllByLikes(String userId);

    List<Post> findByUser(User user);

    List<Post> findByTags(String tags);

    List<Post> findByContentContaining(String text);
}
