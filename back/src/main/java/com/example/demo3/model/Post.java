package com.example.demo3.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Document(collection = "posts")
@AllArgsConstructor
@NoArgsConstructor
public class Post {

    @Id
    private String id;
    @DBRef
    private User user;

    private String content;

    private Set<String> tags = new HashSet<>();

    @JsonIgnore
    @DBRef
    private List<Like> likes = new ArrayList<>();

    @DBRef
    private List<Post> replies = new ArrayList<>();


    private String image;
    private String video;


    @DBRef
    private List<User> repost = new ArrayList<>();

    @DBRef
    private Post replyFor;

    private boolean isReply;
    private boolean isPost;

    private LocalDateTime CreatedAt;
    private boolean isVisible;
    private long commentCount;
}




