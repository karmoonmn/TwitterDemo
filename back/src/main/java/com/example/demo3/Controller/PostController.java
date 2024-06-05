package com.example.demo3.Controller;


import com.example.demo3.DTO.CommentDto;
import com.example.demo3.DTO.PostDto;
import com.example.demo3.DTO.UserDto;
import com.example.demo3.exception.PostException;
import com.example.demo3.exception.UserException;
import com.example.demo3.mapper.PostDtoMapper;
import com.example.demo3.mapper.UserDtoMapper;
import com.example.demo3.model.Post;
import com.example.demo3.model.User;
import com.example.demo3.request.PostReplyRequest;
import com.example.demo3.response.ApiResponse;
import com.example.demo3.service.PostService;
import com.example.demo3.service.UserService;
import com.example.demo3.util.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @Autowired
    private UserService userService;

    // TODO: search post using tag, tested already and it worked !!!!!
    @GetMapping("/search/tag/{tag}")
    public ResponseEntity<List<PostDto>> searchPostByTag(
            @PathVariable String tag, @RequestHeader("Authorization") String jwt)
            throws UserException, PostException {
        User user = userService.findUserProfileByJwt(jwt);

        List<Post> posts = postService.findByTag(tag);
        List<PostDto> postDtos = PostDtoMapper.toPostDtos(posts, user);

        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }


//    TODO: search post using text, tested already and it worked !!!!! front end ppl, please do your work !!!!!
    @GetMapping("/search/{text}")
    public ResponseEntity<List<PostDto>> searchPostByText(
            @PathVariable String text, @RequestHeader("Authorization") String jwt)
            throws UserException, PostException {
        User user = userService.findUserProfileByJwt(jwt);

        List<Post> posts = postService.findByText(text);
        List<PostDto> postDtos = PostDtoMapper.toPostDtos(posts, user);

        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    //TODO: linkedlist (post1 -> post2 -> post3)
    //TODO: logic for creating reply post
    @PostMapping("/reply")
    public ResponseEntity<PostDto> replyPost(
            @RequestBody PostReplyRequest req, @RequestHeader("Authorization") String jwt)
            throws UserException, PostException {
        User user = userService.findUserProfileByJwt(jwt);

        Post post = postService.createdReply(req, user);

        PostDto postDto = PostDtoMapper.toPostDto(post, user);
        return new ResponseEntity<>(postDto, HttpStatus.CREATED);
    }

    //TODO: delete post without deleting its replies
    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse> deletePost(
            @PathVariable String postId, @RequestHeader("Authorization") String jwt)
            throws UserException, PostException {
        User user = userService.findUserProfileByJwt(jwt);

        postService.deletePostById(postId, user.getId());
        ApiResponse res = new ApiResponse();
        res.setMessage("Post deleted successfully");
        res.setStatus(true);
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/delete/{postId}")
    public ResponseEntity<PostDto> deletePostBySettingVisible(@PathVariable String postId, @RequestHeader("Authorization") String jwt)
            throws UserException, PostException {
        User user = userService.findUserProfileByJwt(jwt);
        Post post = postService.deletePostBySettingVisible(postId, user.getId());
        PostDto postDto = PostDtoMapper.toPostDto(post, user);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<PostDto> createPost(
            @RequestBody Post req, @RequestHeader("Authorization") String jwt)
            throws UserException, PostException {
        User user = userService.findUserProfileByJwt(jwt);

        Post post = postService.createPost(req, user);

        PostDto postDto = PostDtoMapper.toPostDto(post, user);
        return new ResponseEntity<>(postDto, HttpStatus.CREATED);
    }

    @PutMapping("/{postId}/repost")
    public ResponseEntity<PostDto> repost(
           @PathVariable String postId, @RequestHeader("Authorization") String jwt)
            throws UserException, PostException {
        User user = userService.findUserProfileByJwt(jwt);

        Post post = postService.repost(postId, user);

        PostDto postDto = PostDtoMapper.toPostDto(post, user);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostDto> findPostById(
            @PathVariable String postId, @RequestHeader("Authorization") String jwt)
            throws UserException, PostException {
        User user = userService.findUserProfileByJwt(jwt);

        Post post = postService.findById(postId);
//        User user = userService.findUserById(post.getUser().getId());

        PostDto postDto = PostDtoMapper.toPostDto(post, user);
        return new ResponseEntity<>(postDto, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<List<PostDto>> getAllPosts(
            @RequestHeader("Authorization") String jwt)
            throws UserException, PostException {
        User user = userService.findUserProfileByJwt(jwt);

        List<Post> posts = postService.findAllPost(user);
        List<PostDto> postDtos = PostDtoMapper.toPostDtos(posts, user);

        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<PostDto>> getUsersAllPosts(
            @PathVariable String userId, @RequestHeader("Authorization") String jwt)
            throws UserException, PostException {
        User postUser = userService.findUserById(userId);
        User user = userService.findUserProfileByJwt(jwt);

        List<Post> posts = postService.getUserPost(postUser);
        List<PostDto> postDtos = PostDtoMapper.toPostDtos(posts, user);

        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/likes")
    public ResponseEntity<List<PostDto>> findPostByLikeContainersUser(
            String userId, @RequestHeader("Authorization") String jwt)
            throws UserException, PostException {
        User user = userService.findUserProfileByJwt(jwt);

        List<Post> posts = postService.findByLikesContainsUser(user);
        List<PostDto> postDtos = PostDtoMapper.toPostDtos(posts, user);

        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }


}








