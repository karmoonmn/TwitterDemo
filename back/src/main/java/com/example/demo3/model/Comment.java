package com.example.demo3.model;

import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Document(collection = "comments")
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    private String id;

    private String commentText;

    @DBRef
    private User user;

    @DBRef
    private Post post;

    private LocalDateTime CreatedAt;

    private String parentCommentId;

    private boolean isVisible;
}

