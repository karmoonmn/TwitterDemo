package com.example.demo3.service;

import com.example.demo3.exception.PostException;
import com.example.demo3.exception.UserException;
import com.example.demo3.model.Like;
import com.example.demo3.model.User;

import java.util.List;

public interface LikeService {

    public Like likePost(String postId, User user) throws UserException, PostException;

    public List<Like> getAllLikes(String postId) throws PostException;


}
