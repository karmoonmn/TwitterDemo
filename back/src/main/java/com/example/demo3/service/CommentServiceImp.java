package com.example.demo3.service;

import com.example.demo3.exception.CommentException;
import com.example.demo3.exception.PostException;
import com.example.demo3.exception.UserException;
import com.example.demo3.model.Comment;
import com.example.demo3.model.Post;
import com.example.demo3.model.User;
import com.example.demo3.repo.CommentRepo;
import com.example.demo3.repo.PostRepo;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.error.DefaultErrorViewResolver;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentServiceImp implements CommentService {

    @Autowired
    private PostRepo postRepository;

    @Autowired
    private CommentRepo commentRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private DefaultErrorViewResolver conventionErrorViewResolver;

    @Override
    public Comment createComment(Comment content, User user) throws PostException, UserException, CommentException {
        Comment comment = new Comment();
        comment.setCommentText(content.getCommentText());
        comment.setUser(user);
        comment.setPost(content.getPost());
        comment.setCreatedAt(LocalDateTime.now());
        comment.setVisible(true);
//        comment.setParentCommentId(content.getPost().getId());
        return commentRepository.save(comment);
    }

    @Override
    public Comment createComment(Post postId, Comment content, User user) throws PostException, UserException, CommentException {
        // Check if commenting on a post or replying to a comment
        // Commenting on a post
        // Validate post existence

        Comment comment = new Comment();
        comment.setCommentText(content.getCommentText());
        comment.setUser(user);
        comment.setPost(postId);
        String posts = postId.getId();
        comment.setParentCommentId(posts);
        comment.setVisible(true);
        comment.setCreatedAt(LocalDateTime.now());
        postId.setCommentCount(commentRepository.countCommentByPostId(postId.getId())+1);
//        postId.setCommentCount(postId.getCommentCount() + 1);
        postRepository.save(postId);
        System.out.println("Updated Comment Count: " + postId.getCommentCount());
        return commentRepository.save(comment);
    }

    @Override
    public Comment createComment(String parentComment, Post postId, Comment content, User user) throws PostException, UserException, CommentException {
        // Check if commenting on a post or replying to a comment
        // Commenting on a post
        // Validate post existence
        Comment comment = new Comment();
        comment.setCommentText(content.getCommentText());
        comment.setUser(user);
        comment.setPost(postId);
        comment.setParentCommentId(parentComment);
        comment.setVisible(true);
        comment.setCreatedAt(LocalDateTime.now());
        return commentRepository.save(comment);
    }

    @Override
    public long getCommentCountByPostId(String postId) {
        return commentRepository.countCommentByPostId(postId);
    }

    @Override
    public Comment findById(String id) throws CommentException {
        return commentRepository.findById(id)
                .orElseThrow(() -> new CommentException("Post not found with id : " + id));
    }

    @Override
    public List<Comment> findAllCommentsByPostId(Post post) throws CommentException {
        return commentRepository.findByPostId(new ObjectId(post.getId()));
    }

    @Override
    public List<Comment> findAllCommentsByPostIdDes(String postId) throws CommentException {
        return commentRepository.findCommentByPostIdOrderByCreatedAtDesc(postId);
    }

    @Override
    public List<Comment> findAllCommentsByParentId(String parentId) throws CommentException {
        return commentRepository.findByParentId(parentId);
    }

    @Override
    public List<Comment> findAllComments(User user) {
        return commentRepository.findByUser(new ObjectId(user.getId()));
    }

    @Override
    public Comment deleteComment(String id) throws PostException, UserException, CommentException {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentException("Comment with ID " + id + " not found"));
        Post postId = comment.getPost();
        postId.setCommentCount(commentRepository.countCommentByPostId(postId.getId())-1);
        postRepository.save(postId);
        comment.setCommentText("");
        comment.setVisible(false);
        return commentRepository.save(comment);
    }

}
