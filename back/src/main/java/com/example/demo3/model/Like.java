package com.example.demo3.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "likes")
@AllArgsConstructor
@NoArgsConstructor
public class Like {

    @Id
    private String id;

    @DBRef
    private User user;

    @DBRef
    private Post post;

}
