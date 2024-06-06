package com.example.demo3.DTO;

import com.example.demo3.model.Like;
import com.example.demo3.model.Tag;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class PostDto {

    private String id;
    private String content;
    private String image;
    private String video;
    private UserDto user;
    private LocalDateTime createdAt;


    private int totalLikes;
    private int totalReplies;
    private int totalReposts;
    private boolean isLiked;
    private boolean isRepost;

    private String [] likes;
    private List<String> repostUsersId;
    private List<PostDto> replyPosts;
    private Set<String> tags;
    private List<CommentDto> comments;
    private long commentCount;
    private boolean isVisible;
}
