package com.example.demo3.Controller;


import com.example.demo3.DTO.LikeDto;
import com.example.demo3.exception.PostException;
import com.example.demo3.exception.UserException;
import com.example.demo3.mapper.LikeDtoMapper;
import com.example.demo3.model.Like;
import com.example.demo3.model.Post;
import com.example.demo3.model.User;
import com.example.demo3.service.LikeService;
import com.example.demo3.service.RecommendationEngine;
import com.example.demo3.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class LikeController {

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;


    @PostMapping("/{postId}/likes")
    public ResponseEntity<LikeDto> likePost(
            @PathVariable String postId, @RequestHeader("Authorization") String jwt)
            throws UserException, PostException {

        User user = userService.findUserProfileByJwt(jwt);
        Like like = likeService.likePost(postId, user);

        LikeDto likeDto = LikeDtoMapper.toLikeDto(like, user);

        return new ResponseEntity<LikeDto>(likeDto, HttpStatus.CREATED);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity<List<LikeDto>> getAllLikes(
            @PathVariable String postId, @RequestHeader("Authorization") String jwt)
            throws UserException, PostException {

        User user = userService.findUserProfileByJwt(jwt);
        List<Like> likes = likeService.getAllLikes(postId);

        List<LikeDto> likeDtos = LikeDtoMapper.toLikeDtos(likes, user);

        return new ResponseEntity<>(likeDtos, HttpStatus.CREATED);
    }


}
