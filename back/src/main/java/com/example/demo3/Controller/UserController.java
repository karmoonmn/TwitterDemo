package com.example.demo3.Controller;

import com.example.demo3.DTO.UserDto;
import com.example.demo3.exception.PostException;
import com.example.demo3.exception.UserException;
import com.example.demo3.mapper.UserDtoMapper;
import com.example.demo3.model.Post;
import com.example.demo3.model.User;
import com.example.demo3.service.PostService;
import com.example.demo3.service.UserService;
import com.example.demo3.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    @GetMapping("/profile")
    public ResponseEntity<UserDto> getUserProfile(
            @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwt(jwt);

        UserDto userDto = UserDtoMapper.toUserDto(user);
        userDto.setReq_user(true);
        return new ResponseEntity<UserDto>(userDto, HttpStatus.ACCEPTED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(
            @PathVariable String userId, @RequestHeader("Authorization") String jwt)
            throws UserException {
        User reqUser = userService.findUserProfileByJwt(jwt);

        User user = userService.findUserById(userId);

        UserDto userDto = UserDtoMapper.toUserDto(user);
        userDto.setReq_user(UserUtil.isReqUser(reqUser, user));
        userDto.setFollowed(UserUtil.isFollowedByReqUser(reqUser, user));

        return new ResponseEntity<UserDto>(userDto, HttpStatus.ACCEPTED);
    }

    @GetMapping("/search")
    public ResponseEntity <List<UserDto>> searchUser(
            @RequestParam String query, @RequestHeader("Authorization") String jwt)
            throws UserException {
        User reqUser = userService.findUserProfileByJwt(jwt);

        List<User> users = userService.searchUser(query);

        List<UserDto> userDto = UserDtoMapper.toUserDtos(users);

        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }

    @PutMapping("/update")
    public ResponseEntity <UserDto> updateUser(
            @RequestBody User req, @RequestHeader("Authorization") String jwt)
            throws UserException {
        User reqUser = userService.findUserProfileByJwt(jwt);

        User user = userService.updateUser(reqUser.getId(), req);

        UserDto userDto = UserDtoMapper.toUserDto(user);

        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{userId}/follow")
    public ResponseEntity <UserDto> followUser(
            @PathVariable String userId, @RequestHeader("Authorization") String jwt)
            throws UserException {
        User reqUser = userService.findUserProfileByJwt(jwt);

        User user = userService.followUser(userId, reqUser);

        UserDto userDto = UserDtoMapper.toUserDto(user);
        userDto.setFollowed(UserUtil.isFollowedByReqUser(reqUser, user));

        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }

    @GetMapping("/post/{postId}")
    public ResponseEntity <UserDto> getUserByPostId(
            @PathVariable String postId, @RequestHeader("Authorization") String jwt)
            throws UserException, PostException {
        User reqUser = userService.findUserProfileByJwt(jwt);

        Post post = postService.findById(postId);
        User user = userService.findUserById(post.getUser().getId());

        UserDto userDto = UserDtoMapper.toUserDto(user);

        return new ResponseEntity<>(userDto, HttpStatus.ACCEPTED);
    }
}
