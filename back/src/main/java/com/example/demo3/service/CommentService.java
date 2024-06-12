package com.example.demo3.service;

import com.example.demo3.exception.CommentException;
import com.example.demo3.exception.PostException;
import com.example.demo3.exception.UserException;
import com.example.demo3.model.Comment;
import com.example.demo3.model.Post;
import com.example.demo3.model.User;

import java.util.List;

public interface CommentService {

//    /**
//     * Creates a new comment for a specific post.
//     *
//     * @param postId The ID of the post to comment on.
//     * @param content The content of the comment.
//     * @param user The user creating the comment.
//     * @param parentCommentId (Optional) The ID of the parent comment if replying to an existing comment.
//     * @throws PostException If the post with the provided ID is not found.
//     * @throws UserException If the user creating the comment is not found.
//     * @throws CommentException If there's an issue specific to comment creation (e.g., exceeding character limit).
//     */
//    public Comment createComment(String postId, Comment content, User user, Comment parentCommentId)
//            throws PostException, UserException, CommentException;

    public Comment createComment(Comment content, User user)
            throws PostException, UserException, CommentException;


    //    Create top level comment
    public Comment createComment(String parentComment, Post postId, Comment req, User user)
            throws PostException, UserException, CommentException;


    public Comment createComment(Post postId, Comment req, User user)
            throws PostException, UserException, CommentException;


    /**
     * Finds all comments for a specific post, including nested replies.
     *
     * @param post The ID of the post to retrieve comments for.
     * @return A list of comments for the specified post, including nested replies (ordered by creation date).
     * @throws PostException If the post with the provided ID is not found.
     * @throws CommentException If there's an error retrieving comments.
     */
    public List<Comment> findAllCommentsByPostId(Post post) throws PostException, CommentException;

    public List<Comment> findAllCommentsByPostIdDes(String postId) throws PostException, CommentException;

    public List<Comment> findAllCommentsByParentId(String parentId) throws PostException, CommentException;

    /**
     * Deletes a specific comment.
     *
     * @param id The ID of the comment to delete.
     * @throws PostException If the comment with the provided ID is not found.
     * @throws UserException If the user trying to delete the comment is not authorized to do so.
     * @throws CommentException If there's an issue specific to comment deletion (e.g., trying to delete a non-existent comment).
     */
    public Comment deleteComment(String id) throws PostException, UserException, CommentException;

    public List<Comment> findAllComments(User user);
//    public List<Comment> findAllComments(String user);

    public Comment findById(String id) throws CommentException;

    public long getCommentCountByPostId(String postId);
}


