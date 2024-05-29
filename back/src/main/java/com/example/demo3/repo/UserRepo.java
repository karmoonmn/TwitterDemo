package com.example.demo3.repo;

import com.example.demo3.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepo extends MongoRepository<User, String> {

    public User findByEmail(String email);

    public List<User> searchUserByEmailOrFullName(String searchText);
}
