package com.example.demo3.repo;

import com.example.demo3.model.Comment;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface CommentRepo extends MongoRepository<Comment, String> {
//    List<Comment> findByPostIdAndParentCommentId(String id);

//    Find comment
    List<Comment> findCommentById(String id);

//    Find all replies of comment
    @Query("{ 'parentCommentId' : ?0 }")
    List<Comment> findByParentId(String parentCommentId);

//    Find All Top level comments
    @Query("{ 'post' : { $ref: 'posts', $id: ?0 } }")
    List<Comment> findByPostId(ObjectId post);

    @Query("{ 'user' : { $ref: 'users', $id: ?0 } }")
    List<Comment> findByUser(ObjectId user);

    int countCommentByPostId(String postId);

//    List<Comment> findByUser(User user);

//    void deleteByParentCommentId(Comment parentId);

    List<Comment> findCommentByPostIdOrderByCreatedAtDesc(String postId);

    List<Comment> findCommentByParentCommentIdOrderByCreatedAt(String parentId);
}
