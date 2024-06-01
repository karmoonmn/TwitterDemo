package com.example.demo3.service;

import com.example.demo3.exception.PostException;
import com.example.demo3.exception.UserException;
import com.example.demo3.model.Post;
import com.example.demo3.model.User;
import com.example.demo3.request.PostReplyRequest;

import java.util.List;

public interface PostService {

    public Post createPost(Post req, User user) throws UserException;

    public List<Post> findAllPost(User user) throws UserException;

    public Post repost(String postId, User user) throws UserException, PostException;

    public Post findById(String postId) throws PostException;

    public void deletePostById(String postId, String userId) throws PostException, UserException;

    public Post removeFromRepost(String postId, User user) throws PostException, UserException;

    public Post createdReply(PostReplyRequest req, User user) throws PostException;

    public List<Post> getUserPost(User user);

    public List<Post> findByLikesContainsUser(User user);

    public List<Post> findByTag(String tag);

    public List<Post> findByText(String text);

    public Post deletePostBySettingVisible(String postId, String userId) throws PostException, UserException;
}
