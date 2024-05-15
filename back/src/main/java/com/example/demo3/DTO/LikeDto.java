package com.example.demo3.DTO;

import com.example.demo3.model.Post;
import lombok.Data;

@Data
public class LikeDto {

    private String id;
    private UserDto user;
    private PostDto post;
}
