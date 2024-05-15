package com.example.demo3.repo;

import com.example.demo3.model.Post;
import com.example.demo3.model.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TagRepo extends MongoRepository<Tag, String> {

//    List<Post> findAllPostsByTag(String tag);

}
