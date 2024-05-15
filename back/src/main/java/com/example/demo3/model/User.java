package com.example.demo3.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    private String id;

    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotEmpty(message = "Password cannot be empty")
    @Length(min = 8, message = "Password must be at least 8 characters long")
    @Pattern.List({
            @Pattern(regexp = "(?=.*[0-9]).+", message = "Password must have at least one digit"),
            @Pattern(regexp = "(?=.*[a-z]).+", message = "Password must have at least one lowercase letter"),
            @Pattern(regexp = "(?=.*[A-Z]).+", message = "Password must have at least one uppercase letter"),
            @Pattern(regexp = "(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).+", message = "Password must have at least one special character")
    })
    private String password;

    private String fullName;
    private String location;
    private String website;
    private String birthDate;
    private String image;
    private String backgroundImage;
    private String bio;
    private boolean req_user;
    private boolean login_with_google;

    //not shown in json file
    @JsonIgnore
    @DBRef
    private List<Post> posts = new ArrayList<>();

    @DBRef
    private List<Like> likes = new ArrayList<>();

    @Field("verification")
    private Verification verfication;

    @JsonIgnore
    @DBRef
    private List<User> followers = new ArrayList<>();

    @JsonIgnore
    @DBRef
    private List<User> followings = new ArrayList<>();

    private Map<String, Integer> tagInterests = new HashMap<>();
}
