package com.example.demo3.DTO;

import com.example.demo3.model.Comment;
import com.example.demo3.model.Post;
import com.example.demo3.model.User;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private String id;
    private String commentText;
    private String user;
    private String post;
    private String parentCommentId;
    private LocalDateTime createdAt;
    private boolean isVisible;
}
