package com.example.demo3.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Document(collection = "tags")
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

    @Id
    private String id;

    private String name;

    @DBRef
    private Set<Post> posts = new HashSet<>();
}


