package com.example.demo3.Controller;

import com.example.demo3.DTO.CommentDto;
import com.example.demo3.DTO.LikeDto;
import com.example.demo3.DTO.PostDto;
import com.example.demo3.exception.CommentException;
import com.example.demo3.exception.PostException;
import com.example.demo3.exception.UserException;
import com.example.demo3.mapper.CommentDtoMapper;
import com.example.demo3.mapper.LikeDtoMapper;
import com.example.demo3.mapper.PostDtoMapper;
import com.example.demo3.model.Comment;
import com.example.demo3.model.Like;
import com.example.demo3.model.Post;
import com.example.demo3.model.User;
import com.example.demo3.repo.CommentRepo;
import com.example.demo3.service.CommentService;
import com.example.demo3.DTO.CommentDto;
import com.example.demo3.service.CommentService;
import com.example.demo3.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.example.demo3.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @Autowired
    private UserService userService;
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private PostService postService;

    @PostMapping("/create/{postId}")
    public ResponseEntity<CommentDto> addComment(@RequestBody Comment req, @PathVariable String postId, @RequestHeader("Authorization") String jwt)
            throws UserException, PostException, CommentException {
        User user = userService.findUserProfileByJwt(jwt);
        Post post = postService.findById(postId);
        Comment comment = commentService.createComment(post, req, user);

        CommentDto commentDto = CommentDtoMapper.toCommentDto(comment, user);
        PostDto postDto = PostDtoMapper.toPostDto(post, user);
        return new ResponseEntity<>(commentDto, HttpStatus.CREATED);
    }

//    This work QWQQ !
    @PostMapping("/create/reply/{postId}/{commentId}")
    public ResponseEntity<CommentDto> replyComment(
            @RequestBody Comment req, @PathVariable String postId, @PathVariable String commentId,
            @RequestHeader("Authorization") String jwt)
            throws UserException, PostException, CommentException {
        User user = userService.findUserProfileByJwt(jwt);
        Post post = postService.findById(postId);
        Comment comment = commentService.createComment(commentId, post, req, user);

        CommentDto commentDto = CommentDtoMapper.toCommentDto(comment, user);
        return new ResponseEntity<>(commentDto, HttpStatus.CREATED);
    }

    @GetMapping("/one/{commentId}")
    public ResponseEntity<CommentDto> findCommentById(
            @PathVariable String commentId, @RequestHeader("Authorization") String jwt)
            throws UserException, CommentException {
        User user = userService.findUserProfileByJwt(jwt);

        Comment comment = commentService.findById(commentId);

        CommentDto commentDto = CommentDtoMapper.toCommentDto(comment, user);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<List<CommentDto>> getAllCommentsByPostId
            (@PathVariable String postId, @RequestHeader("Authorization") String jwt)
            throws UserException, PostException, CommentException {
        User user = userService.findUserProfileByJwt(jwt);
        Post post = postService.findById(postId);

        List<Comment> comments = commentService.findAllCommentsByPostId(post);
        List<CommentDto> commentDtos = CommentDtoMapper.toCommentDtos(comments, user);

        return new ResponseEntity<>(commentDtos, HttpStatus.OK);
    }

    @GetMapping("/count/{postId}")
    public long getCommentCount(@PathVariable String postId, @RequestHeader("Authorization") String jwt) {
        return commentService.getCommentCountByPostId(postId);
    }
//
//    @GetMapping("/des/{postId}")
//    public ResponseEntity<List<CommentDto>> getAllCommentsByPostIdDes
//            (@PathVariable String postId, @RequestHeader("Authorization") String jwt)
//            throws UserException, PostException, CommentException {
//        User user = userService.findUserProfileByJwt(jwt);
//
//        List<Comment> comments = commentService.findAllCommentsByPostIdDes(postId);
//        List<CommentDto> commentDtos = CommentDtoMapper.toCommentDtos(comments, user);
//
//        return new ResponseEntity<>(commentDtos, HttpStatus.OK);
//    }

    @GetMapping("/comment/{parentId}")
    public ResponseEntity<List<CommentDto>> getAllCommentsByParentId(@PathVariable String parentId, @RequestHeader("Authorization") String jwt)
            throws UserException, CommentException, PostException {
        User user = userService.findUserProfileByJwt(jwt);

        List<Comment> comments = commentService.findAllCommentsByParentId(parentId);
        List<CommentDto> commentDtos = CommentDtoMapper.toCommentDtos(comments, user);

        return new ResponseEntity<>(commentDtos, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CommentDto>> getAllCommentsByUser(
            @PathVariable String userId,
            @RequestHeader("Authorization") String jwt)
            throws UserException {
        User user = userService.findUserById(userId);

        List<Comment> comments = commentService.findAllComments(user);
        List<CommentDto> postDtos = CommentDtoMapper.toCommentDtos(comments, user);

        return new ResponseEntity<>(postDtos, HttpStatus.OK);
    }


    @PutMapping("/delete/{commentId}")
    public ResponseEntity<CommentDto> deleteCommentById(
            @PathVariable String commentId,
            @RequestHeader("Authorization") String jwt)
            throws UserException, PostException, CommentException {
        User user = userService.findUserProfileByJwt(jwt);

        Comment comment = commentService.deleteComment(commentId);
        CommentDto commentDto = CommentDtoMapper.toCommentDto(comment, user);
        return new ResponseEntity<>(commentDto, HttpStatus.OK);
    }


}
