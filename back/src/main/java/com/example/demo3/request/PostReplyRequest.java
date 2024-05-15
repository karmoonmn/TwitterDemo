package com.example.demo3.request;

import lombok.Data;

import java.time.LocalDateTime;


@Data
public class PostReplyRequest {

    private String content;
    private String postId;
    private LocalDateTime createdAt;
    private String image;

}
