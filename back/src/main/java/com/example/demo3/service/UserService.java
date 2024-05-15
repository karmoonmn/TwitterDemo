package com.example.demo3.service;

import com.example.demo3.exception.UserException;
import com.example.demo3.model.User;

import java.util.List;

public interface UserService {

    public User findUserById(String userId) throws UserException;

    public User findUserProfileByJwt(String jwt) throws UserException;

    public User updateUser(String userId, User user) throws UserException;

    public User followUser(String userId, User user) throws UserException;

    public List<User> searchUser(String query);

}
